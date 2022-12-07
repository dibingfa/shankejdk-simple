/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.concurrent;

import java.util.List;
import java.util.concurrent.*;

class LibDispatchSerialQueue extends AbstractExecutorService {
        static final int RUNNING    = 0;
    static final int SHUTDOWN   = 1;
//  static final int STOP       = 2; // not supported by GCD
    static final int TERMINATED = 3;

    final Object lock = new Object();
        LibDispatchQueue nativeQueueWrapper;
    volatile int runState;

        LibDispatchSerialQueue(final long queuePtr) {
                nativeQueueWrapper = new LibDispatchQueue(queuePtr);
        }

        @Override
        public void execute(final Runnable task) {
                if (nativeQueueWrapper == null) return;
                LibDispatchNative.nativeExecuteAsync(nativeQueueWrapper.ptr, task);
        }

        @Override
        public boolean isShutdown() {
                return runState != RUNNING;
        }

        @Override
        public boolean isTerminated() {
                return runState == TERMINATED;
        }

        @Override
        public void shutdown() {
                synchronized (lock) {
                        if (runState != RUNNING) return;

                        runState = SHUTDOWN;
                        execute(new Runnable() {
                                public void run() {
                                        synchronized (lock) {
                                                runState = TERMINATED;
                                                lock.notifyAll(); // for the benefit of awaitTermination()
                                        }
                                }
                        });
                        nativeQueueWrapper = null;
                }
        }

        @Override
        public List<Runnable> shutdownNow() {
                shutdown();
                return null;
        }

        @Override
        public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
                if (runState == TERMINATED) return true;

                final long millis = unit.toMillis(timeout);
                if (millis <= 0) return false;

                synchronized (lock) {
                        if (runState == TERMINATED) return true;
                        lock.wait(timeout);
                        if (runState == TERMINATED) return true;
                }

                return false;
        }
}
