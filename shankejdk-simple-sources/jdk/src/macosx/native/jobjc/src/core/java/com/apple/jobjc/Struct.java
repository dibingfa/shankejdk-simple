/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


/**
 * A struct is malloced on the C heap and accessed in Java through a ByteBuffer.
 */
public abstract class Struct{
    protected final NativeBuffer raw;
    private final JObjCRuntime runtime;
    protected final JObjCRuntime getRuntime(){ return runtime; }

    /**
     * Create a brand new struct from nothing.
     */
    protected Struct(final JObjCRuntime runtime, final int SIZEOF){
        this(runtime, new NativeBuffer(SIZEOF), SIZEOF);
    }

    /**
     * Create a struct by taking ownership of an existing buffer.
     * Used for struct fields of type struct. For example, the origin and size fields
     * in NSRect would be initialized with this constructor.
     */
    protected Struct(final JObjCRuntime runtime, final NativeBuffer buffer, final int SIZEOF){
        if(runtime == null) throw new NullPointerException("runtime");
        this.runtime = runtime;
        this.raw = buffer;
        this.raw.limit(SIZEOF);
    }

    abstract public Coder getCoder();
}
