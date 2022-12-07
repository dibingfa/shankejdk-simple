/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


public abstract class NativeObjectLifecycleManager {
    private static native void retainNativeObject(final long ptr);
    private static native void releaseNativeObject(final long ptr);
    private static native void freeNativeObject(final long ptr);

    abstract void begin(final long ptr);
    abstract void end(final long ptr);
    boolean shouldPreRetain() { return false; }

    public static class CFRetainRelease extends NativeObjectLifecycleManager {
        public static final NativeObjectLifecycleManager INST = new CFRetainRelease();
        @Override void begin(final long ptr) { retainNativeObject(ptr); }
        @Override void end(final long ptr) { releaseNativeObject(ptr); }
        @Override boolean shouldPreRetain() { return true; }
    }

    public static class Free extends NativeObjectLifecycleManager {
        public static final NativeObjectLifecycleManager INST = new Free();
        @Override void begin(final long ptr) { }
        @Override void end(final long ptr) { freeNativeObject(ptr); }
    }

    public static class Nothing extends NativeObjectLifecycleManager {
        public static final NativeObjectLifecycleManager INST = new Nothing();
        @Override void begin(final long ptr) { }
        @Override void end(final long ptr) { }
    }
}
