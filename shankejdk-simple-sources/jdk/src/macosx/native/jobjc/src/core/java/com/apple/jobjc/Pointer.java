/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


public class Pointer <T> implements Comparable<Pointer<T>>{
    long ptr;

    protected Pointer(final long ptr) {
        this.ptr = ptr;
        getNativeObjectLifecycleManager().begin(ptr);
    }

    @Override protected final synchronized void finalize() throws Throwable {
        long pptr = ptr;
        ptr = 0;
        if (pptr != 0) getNativeObjectLifecycleManager().end(pptr);
    }

    protected NativeObjectLifecycleManager getNativeObjectLifecycleManager() {
        return NativeObjectLifecycleManager.Nothing.INST;
    }

    @Override public boolean equals(Object o) {
        return o instanceof Pointer && ptr == ((Pointer) o).ptr;
    }

    @Override public int hashCode() { return (int)(ptr^(ptr>>>32)); }

    public int compareTo(Pointer<T> o) {
        if(this==o || ptr==o.ptr) return 0;
        if(ptr < o.ptr) return -1;
        return 1;
    }
}
