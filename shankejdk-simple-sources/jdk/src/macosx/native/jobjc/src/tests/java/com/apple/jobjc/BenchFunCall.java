/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Invoke.FunCall;
import com.apple.jobjc.PrimitiveCoder.DoubleCoder;

public final class BenchFunCall extends BaseBench{
    final static int ITERS  = 1000;
    final static FunCall fc = new FunCall(JObjCRuntime.getInstance(), "sin", DoubleCoder.INST, DoubleCoder.INST);
    final static double ARG = 3.14159265 / 2.0;
    final static double RET = 1.0;

    private static native double jniSin(double arg);

    public void testBench(){
        this.bench("Calling functions", 5, 3, 10000L,

                new Task("JNI Invoke"){
            @Override public void run() {
                for(int i = 0; i < ITERS; ++i)
                    jniSin(ARG);
            }},

            new Task("JObjC FunCall"){
                @Override public void run() {
                    for(int i = 0; i < ITERS; ++i){
                        fc.init(ARGS);
                        DoubleCoder.INST.push(ARGS, ARG);
                        fc.invoke(ARGS);
                        DoubleCoder.INST.pop(ARGS);
                    }
                }},

                new Task("JObjC FunCall (inlined)"){
                    @Override public void run() {
                        for(int i = 0; i < ITERS; ++i){
                            // init
                            ARGS.argPtrsPtr = ARGS.buffer.bufferPtr;
                            ARGS.argValuesPtr = ARGS.buffer.bufferPtr + 256;
                            // push double
                            //// push arg ptr
                            if(JObjCRuntime.IS64)
                                UNSAFE.putLong(ARGS.argPtrsPtr, ARGS.argValuesPtr);
                            else
                                UNSAFE.putInt(ARGS.argPtrsPtr, (int) ARGS.argValuesPtr);
                            ARGS.argPtrsPtr += JObjCRuntime.PTR_LEN;
                            //// push arg value
                            UNSAFE.putDouble(ARGS.argValuesPtr, ARG);
                            ARGS.argValuesPtr += 8;
                            // invoke
                            FunCall.invoke(fc.cif.cif.bufferPtr, fc.fxnPtr, ARGS.retValPtr, ARGS.buffer.bufferPtr);
                            // pop
                            UNSAFE.getDouble(ARGS.retValPtr);
                        }
                    }}
        );
    }
}
