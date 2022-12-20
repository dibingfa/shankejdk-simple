/*
 * Copyright (c) 2014, 2018, Oracle and/or its affiliates. All rights reserved.
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
#include "jfr/recorder/jfrRecorder.hpp"
#include "jfr/utilities/jfrAllocation.hpp"
#include "jfr/utilities/jfrTypes.hpp"
#include "memory/allocation.inline.hpp"
#include "runtime/atomic.inline.hpp"
#include "runtime/orderAccess.inline.hpp"
#include "runtime/vm_version.hpp"
#include "runtime/mutexLocker.hpp"
#include "utilities/debug.hpp"
#include "utilities/macros.hpp"
#include "utilities/nativeCallStack.hpp"

jlong atomic_add_jlong(jlong value, jlong volatile* const dest) {
  jlong compare_value;
  jlong exchange_value;
#ifndef SUPPORTS_NATIVE_CX8
  if (!VM_Version::supports_cx8()) {
    MutexLockerEx mu(JfrCounters_lock, Mutex::_no_safepoint_check_flag);
    return *dest += value;
  }
#endif
  do {
    compare_value = OrderAccess::load_acquire(dest);
    exchange_value = compare_value + value;
  } while (Atomic::cmpxchg(exchange_value, dest, compare_value) != compare_value);
  return exchange_value;
}


static void hook_memory_allocation(const char* allocation, size_t alloc_size) {
  if (NULL == allocation) {
    if (!JfrRecorder::is_created()) {
      if (LogJFR) tty->print_cr("Memory allocation failed for size [" SIZE_FORMAT "] bytes", alloc_size);
      return;
    } else {
      // after critical startup, fail as by default
      vm_exit_out_of_memory(alloc_size, OOM_MALLOC_ERROR, "AllocateHeap");
    }
  }
  debug_only(add(alloc_size));
}

void JfrCHeapObj::on_memory_allocation(const void* allocation, size_t size) {
  hook_memory_allocation((const char*)allocation, size);
}

void* JfrCHeapObj::operator new(size_t size) throw() {
  return operator new(size, std::nothrow);
}

void* JfrCHeapObj::operator new (size_t size, const std::nothrow_t&  nothrow_constant) throw() {
  void* const memory = CHeapObj<mtTracing>::operator new(size, nothrow_constant, CALLER_PC);
  hook_memory_allocation((const char*)memory, size);
  return memory;
}

void* JfrCHeapObj::operator new [](size_t size) throw() {
  return operator new[](size, std::nothrow);
}

void* JfrCHeapObj::operator new [](size_t size, const std::nothrow_t&  nothrow_constant) throw() {
  void* const memory = CHeapObj<mtTracing>::operator new[](size, nothrow_constant, CALLER_PC);
  hook_memory_allocation((const char*)memory, size);
  return memory;
}

void JfrCHeapObj::operator delete(void* p, size_t size) {
  debug_only(hook_memory_deallocation(size);)
  CHeapObj<mtTracing>::operator delete(p);
}

void JfrCHeapObj::operator delete[](void* p, size_t size) {
  debug_only(hook_memory_deallocation(size);)
  CHeapObj<mtTracing>::operator delete[](p);
}

char* JfrCHeapObj::realloc_array(char* old, size_t size) {
  char* const memory = ReallocateHeap(old, size, mtTracing, AllocFailStrategy::RETURN_NULL);
  hook_memory_allocation(memory, size);
  return memory;
}

void JfrCHeapObj::free(void* p, size_t size) {
  debug_only(hook_memory_deallocation(size);)
  FreeHeap(p);
}

char* JfrCHeapObj::allocate_array_noinline(size_t elements, size_t element_size) {
  return AllocateHeap(elements * element_size, mtTracing, CALLER_PC, AllocFailStrategy::RETURN_NULL);
}
