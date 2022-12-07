/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.foundation.FoundationFramework;
import com.apple.jobjc.foundation.NSString;

public class BenchIDPop extends BaseBench{
    static native long jniNSStringAlloc();
    static native long jniNSStringAllocAndRetain();
    static native long jniNSStringCached();
    static native void jniCFRetain(long x);
    static native void jniCFRelease(long x);

    final static int ITERS = 1000;
    final static FoundationFramework FND = JOBJC.Foundation();

    private static class LongWrap{
        long l;
        public LongWrap(long l){ this.l = l; }
    }

    public void testIt(){
        bench("Alloc, retain, pop a new NSString", 2, 3, 2000,
                new Task("jniNSStringAllocAndRetain()"){
            @Override public void run() {
                for(int i = 0; i < ITERS; i++)
                    jniNSStringAllocAndRetain();
            }},

            new Task("new LongWrap(jniNSStringAllocAndRetain())"){
                @Override public void run() {
                    for(int i = 0; i < ITERS; i++)
                        new LongWrap(jniNSStringAllocAndRetain());
                }},

                new Task("FND.NSString().alloc()"){
                    @Override public void run() {
                        for(int i = 0; i < ITERS; i++)
                            FND.NSString().alloc();
                    }},

                    new Task("new NSString(jniNSStringAlloc(), RUNTIME)"){
                        @Override public void run() {
                            for(int i = 0; i < ITERS; i++)
                                new NSString(jniNSStringAlloc(), RUNTIME);
                        }}
        );

        final long nsstringPtr = jniNSStringAlloc();

        bench("Get and hold an existing object", 2, 3, 2000,
                new Task("jniCFRetain(nsstringPtr)"){
            @Override public void run() {
                for(int i = 0; i < ITERS; i++)
                    jniCFRetain(nsstringPtr);
            }},

            new Task("jniCFRetain(new LongWrap(nsstringPtr).l"){
                @Override public void run() {
                    for(int i = 0; i < ITERS; i++)
                        jniCFRetain(new LongWrap(nsstringPtr).l);
                }},

                new Task("ID.getInstance(nsstringPtr, RUNTIME)"){
                    @Override public void run() {
                        for(int i = 0; i < ITERS; i++)
                            ID.getInstance(nsstringPtr, RUNTIME);
                    }}
        );
    }

}
