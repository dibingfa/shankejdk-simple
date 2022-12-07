/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import java.nio.ByteOrder;

import com.apple.jobjc.Coder.PrimitivePointerCoder;


public final class NativeArgumentBuffer{
    private static final ThreadLocal<NativeArgumentBuffer> threadLocal = new ThreadLocal<NativeArgumentBuffer>();

    static NativeArgumentBuffer getThreadLocalBuffer(final JObjCRuntime runtime) {
        runtime.assertOK();
        final NativeArgumentBuffer alreadyCreated = threadLocal.get();
        if (alreadyCreated != null) return alreadyCreated;

        final NativeArgumentBuffer newThreadLocalState = new NativeArgumentBuffer(runtime);
        threadLocal.set(newThreadLocalState);
        return newThreadLocalState;
    }

    final JObjCRuntime runtime;

    final NativeBuffer buffer;

    long argPtrsPtr;
    long argValuesPtr;
    final long retValPtr;

    private static final int MAX_ARGS = 512;
    private static final int BUF_SIZE = MAX_ARGS * 8 * 2;
    private static final int ARG_VALS_OFFSET = BUF_SIZE/2;

    private NativeArgumentBuffer(final JObjCRuntime runtime) {
        runtime.assertOK();
        this.runtime = runtime;
        this.buffer = new NativeBuffer(BUF_SIZE);
        this.buffer.buffer.order(ByteOrder.nativeOrder());

        reset();
        this.retValPtr = buffer.bufferPtr;
    }


    // Call before each new call
    public void reset() {
        argPtrsPtr = buffer.bufferPtr;
        argValuesPtr = buffer.bufferPtr + ARG_VALS_OFFSET;
        assert buffer.ptrInBounds(argValuesPtr);
    }

    // Push a pointer to a block of memory
    public void doPutArgPtr(long ptr) {
        assert buffer.ptrInBounds(argPtrsPtr);
        PrimitivePointerCoder.INST.push(runtime, argPtrsPtr, ptr);
        argPtrsPtr += JObjCRuntime.PTR_LEN;
    }

    // Call this after having written a value of size `sizeof` to `argValuesPtr`.
    public void didPutArgValue(int sizeof) {
        assert buffer.ptrInBounds(argValuesPtr);
        doPutArgPtr(argValuesPtr);
        argValuesPtr += sizeof;
    }


    @Override public String toString() {
        final StringBuilder builder = new StringBuilder();
        final long bptr = buffer.bufferPtr;

        for(long i = bptr; i < bptr + ARG_VALS_OFFSET; i += JObjCRuntime.PTR_LEN){
            if(argPtrsPtr == i)
                builder.append("*");
            builder.append(PrimitivePointerCoder.INST.popPtr(JObjCRuntime.inst(), i));
            builder.append(" ");
        }

        builder.append("\n");

        for(long i = bptr + ARG_VALS_OFFSET; i < bptr + BUF_SIZE; i += JObjCRuntime.PTR_LEN){
            if(argValuesPtr == i)
                builder.append("*");
            builder.append(PrimitivePointerCoder.INST.popPtr(JObjCRuntime.inst(), i));
            builder.append(" ");
        }

        return builder.toString();
    }
}
