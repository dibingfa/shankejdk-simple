/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Coder.StructCoder;
import com.apple.jobjc.Invoke.FunCall;
import com.apple.jobjc.foundation.FoundationFramework;
import com.apple.jobjc.foundation.NSRect;

public class BenchStructCoding extends BaseBench {
    final static FoundationFramework FND = JOBJC.Foundation();
    final static int ITERS = 1000;
    final static PrimitiveCoder CODER = com.apple.jobjc.MixedPrimitiveCoder.FloatDoubleCoder;
    final static StructCoder RCODER = NSRect.getStructCoder();
    final static FunCall nsMakePoint =
        new com.apple.jobjc.Invoke.FunCall(FND, "NSMakeRect", com.apple.jobjc.foundation.NSRect.getStructCoder(),
                com.apple.jobjc.MixedPrimitiveCoder.FloatDoubleCoder,
                com.apple.jobjc.MixedPrimitiveCoder.FloatDoubleCoder,
                com.apple.jobjc.MixedPrimitiveCoder.FloatDoubleCoder,
                com.apple.jobjc.MixedPrimitiveCoder.FloatDoubleCoder);

    public void testFoo(){
        bench("NSMakeRect", 3, 3, 10,
                new Task("FND.NSMakeRect"){
            @Override public void run() {
                for(int i = 0; i < ITERS; ++i){
                    NSRect s = FND.NSMakeRect(0, 1, 2, 3);
//                    assertEquals(1.0D, s.origin().y());
                }
            }},

            new Task("nsMakeRect.invoke(..., s)"){
                @Override public void run() {
                    for(int i = 0; i < ITERS; ++i){
                        nsMakePoint.init(ARGS);
                        CODER.push(ARGS, 0.0D);
                        CODER.push(ARGS, 1.0D);
                        CODER.push(ARGS, 2.0D);
                        CODER.push(ARGS, 3.0D);
                        NSRect s = FND.makeNSRect();
                        nsMakePoint.invoke(ARGS, s);
//                        assertEquals(1.0D, s.origin().y());
                    }
                }},

                new Task("nsMakeRect.invoke(..); NSRect r = pop(..);"){
                    @Override public void run() {
                        for(int i = 0; i < ITERS; ++i){
                            nsMakePoint.init(ARGS);
                            CODER.push(ARGS, 0.0D);
                            CODER.push(ARGS, 1.0D);
                            CODER.push(ARGS, 2.0D);
                            CODER.push(ARGS, 3.0D);
                            nsMakePoint.invoke(ARGS);
                            NSRect s = (NSRect) RCODER.pop(ARGS);
//                            assertEquals(1.0D, s.origin().y());
                        }
                    }}
        );
    }
}
