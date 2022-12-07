/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.concurrent;

final class LibDispatchNative {
    static {
        java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    System.loadLibrary("osx");
                    return null;
                }
            });
    }

    static native boolean nativeIsDispatchSupported();
    static native long nativeGetMainQueue();
    static native long nativeCreateConcurrentQueue(int priority);
    static native long nativeCreateSerialQueue(String name);
    static native void nativeReleaseQueue(long nativeQueue);
    static native void nativeExecuteAsync(long nativeQueue, Runnable task);
    static native void nativeExecuteSync(long nativeQueue, Runnable task);

    private LibDispatchNative() { }
}
