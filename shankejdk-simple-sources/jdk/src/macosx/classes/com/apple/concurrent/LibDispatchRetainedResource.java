/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.concurrent;

class LibDispatchRetainedResource {
        protected long ptr;

        protected LibDispatchRetainedResource(final long ptr) {
                this.ptr = ptr;
        }

        protected synchronized void dispose() {
                if (ptr != 0) LibDispatchNative.nativeReleaseQueue(ptr);
                ptr = 0;
        }

        protected void finalize() throws Throwable {
                dispose();
        }
}
