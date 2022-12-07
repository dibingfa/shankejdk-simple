/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.concurrent;

import java.util.concurrent.Executor;

abstract class LibDispatchMainQueue extends LibDispatchQueue implements Executor {
        public LibDispatchMainQueue() {
                super(LibDispatchNative.nativeGetMainQueue());
        }

        @Override
        protected synchronized void dispose() {
                // should not dispose the main queue
        }

        static class Sync extends LibDispatchMainQueue {
                @Override
                public void execute(final Runnable task) {
                        LibDispatchNative.nativeExecuteSync(ptr, task);
                }
        }

        static class ASync extends LibDispatchMainQueue {
                @Override
                public void execute(final Runnable task) {
                        LibDispatchNative.nativeExecuteAsync(ptr, task);
                }
        }
}
