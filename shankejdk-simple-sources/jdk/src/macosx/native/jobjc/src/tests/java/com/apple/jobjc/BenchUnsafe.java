/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import java.nio.ByteBuffer;

public class BenchUnsafe extends BaseBench{
    final static int ITERS = 1000000;

    final static NativeBuffer NBUF = new NativeBuffer(2 * ITERS);
    final static ByteBuffer BBUF = NBUF.buffer;
    final static long ADDR = NBUF.bufferPtr;
    final static long ADDR_MAX = NBUF.bufferPtr + ITERS;

    final static NativeBuffer NBUF2 = new NativeBuffer(2 * ITERS);
    final static ByteBuffer BBUF2 = NBUF2.buffer;
    final static long ADDR2 = NBUF2.bufferPtr;
    final static long ADDR2_MAX = NBUF2.bufferPtr + ITERS;

    final static long ARG = 345;

    final static long BUFSIZE = BBUF.limit();

    public void testIt(){
        this.bench("Memory writes", 5, 3, 100L,

                new Task("buffer.putLong"){
            @Override public void run() {
                for(long i = 0; i < ITERS; i++)
                    BBUF.putLong((int) i, ARG);
            }},

            new Task("unsafe.putLong"){
                @Override public void run() {
                    for(long i = ADDR; i < ADDR_MAX; i++)
                        UNSAFE.putLong(i, ARG);
                }});
    }
}
