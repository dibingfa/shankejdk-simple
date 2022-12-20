/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

#include "precompiled.hpp"
#include "memory/gcLocker.inline.hpp"
#include "memory/resourceArea.hpp"
#include "memory/sharedHeap.hpp"
#include "runtime/thread.inline.hpp"

volatile jint GC_locker::_jni_lock_count = 0;
volatile bool GC_locker::_needs_gc       = false;
volatile bool GC_locker::_doing_gc       = false;
unsigned int  GC_locker::_total_collections = 0;




bool GC_locker::check_active_before_gc() {
  assert(SafepointSynchronize::is_at_safepoint(), "only read at safepoint");
  if (is_active() && !_needs_gc) {
    verify_critical_count();
    _needs_gc = true;
    if (PrintJNIGCStalls && PrintGCDetails) {
      ResourceMark rm; // JavaThread::name() allocates to convert to UTF8
      gclog_or_tty->print_cr("%.3f: Setting _needs_gc. Thread \"%s\" %d locked.",
                             gclog_or_tty->time_stamp().seconds(), Thread::current()->name(), _jni_lock_count);
    }

  }
  return is_active();
}

void GC_locker::stall_until_clear() {
  assert(!JavaThread::current()->in_critical(), "Would deadlock");
  MutexLocker   ml(JNICritical_lock);

  if (needs_gc()) {
    if (PrintJNIGCStalls && PrintGCDetails) {
      ResourceMark rm; // JavaThread::name() allocates to convert to UTF8
      gclog_or_tty->print_cr("%.3f: Allocation failed. Thread \"%s\" is stalled by JNI critical section, %d locked.",
                             gclog_or_tty->time_stamp().seconds(), Thread::current()->name(), _jni_lock_count);
    }
  }

  // Wait for _needs_gc  to be cleared
  while (needs_gc()) {
    JNICritical_lock->wait();
  }
}

bool GC_locker::should_discard(GCCause::Cause cause, uint total_collections) {
  return (cause == GCCause::_gc_locker) &&
         (_total_collections != total_collections);
}

void GC_locker::jni_lock(JavaThread* thread) {
  assert(!thread->in_critical(), "shouldn't currently be in a critical region");
  MutexLocker mu(JNICritical_lock);
  // Block entering threads if we know at least one thread is in a
  // JNI critical region and we need a GC.
  // We check that at least one thread is in a critical region before
  // blocking because blocked threads are woken up by a thread exiting
  // a JNI critical region.
  while (is_active_and_needs_gc() || _doing_gc) {
    JNICritical_lock->wait();
  }
  thread->enter_critical();
  _jni_lock_count++;
  increment_debug_jni_lock_count();
}

void GC_locker::jni_unlock(JavaThread* thread) {
  assert(thread->in_last_critical(), "should be exiting critical region");
  MutexLocker mu(JNICritical_lock);
  _jni_lock_count--;
  decrement_debug_jni_lock_count();
  thread->exit_critical();
  if (needs_gc() && !is_active_internal()) {
    // We're the last thread out. Request a GC.
    // Capture the current total collections, to allow detection of
    // other collections that make this one unnecessary.  The value of
    // total_collections() is only changed at a safepoint, so there
    // must not be a safepoint between the lock becoming inactive and
    // getting the count, else there may be unnecessary GCLocker GCs.
    _total_collections = Universe::heap()->total_collections();
    _doing_gc = true;
    {
      // Must give up the lock while at a safepoint
      MutexUnlocker munlock(JNICritical_lock);
      if (PrintJNIGCStalls && PrintGCDetails) {
        ResourceMark rm; // JavaThread::name() allocates to convert to UTF8
        gclog_or_tty->print_cr("%.3f: Thread \"%s\" is performing GC after exiting critical section, %d locked",
            gclog_or_tty->time_stamp().seconds(), Thread::current()->name(), _jni_lock_count);
      }
      Universe::heap()->collect(GCCause::_gc_locker);
    }
    _doing_gc = false;
    _needs_gc = false;
    JNICritical_lock->notify_all();
  }
}

// Implementation of No_GC_Verifier

