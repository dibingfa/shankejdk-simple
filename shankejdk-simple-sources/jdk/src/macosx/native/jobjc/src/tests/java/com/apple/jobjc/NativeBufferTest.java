/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import junit.framework.TestCase;

public class NativeBufferTest extends TestCase{
    public void testSlicePtrs(){
        JObjC.getInstance();

        NativeBuffer b = new NativeBuffer(123);
        b.position(1);
        NativeBuffer c = b.slice();
        assertEquals(b.bufferPtr + 1, c.bufferPtr);
    }
}
