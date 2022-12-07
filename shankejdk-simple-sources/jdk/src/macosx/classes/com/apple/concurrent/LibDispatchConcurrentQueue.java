/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.concurrent;

import java.util.concurrent.Executor;

class LibDispatchConcurrentQueue extends LibDispatchQueue implements Executor {
        LibDispatchConcurrentQueue(final long queuePtr) {
                super(queuePtr);
        }

        @Override
        public void execute(final Runnable task) {
                LibDispatchNative.nativeExecuteAsync(ptr, task);
        }

        @Override
        protected synchronized void dispose() {
                // should not dispose the default concurrent queues
        }
}
