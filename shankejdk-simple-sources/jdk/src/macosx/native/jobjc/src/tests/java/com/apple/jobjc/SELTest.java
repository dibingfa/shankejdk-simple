/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Coder.PointerCoder;
import com.apple.jobjc.Coder.PrimitivePointerCoder;
import com.apple.jobjc.Invoke.MsgSend;

public class SELTest extends PooledTestCase {

    NativeArgumentBuffer nativeBuffer;
    JObjCRuntime runtime;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        nativeBuffer = UnsafeRuntimeAccess.getNativeBuffer();
        runtime = nativeBuffer.runtime;
    }

    public void testGetBlackColor() throws Throwable {
        final MacOSXFramework appKit = TestUtils.getAppKit();
        final NSClass<? extends ID> clazz = UnsafeRuntimeAccess.getNSClass(appKit, "NSColor");
        final MsgSend sel = UnsafeRuntimeAccess.createMsgSend(clazz, "redColor", PointerCoder.INST);

        sel.init(nativeBuffer, clazz);
        sel.invoke(nativeBuffer);

        final long blackColorPtr = PrimitivePointerCoder.INST.pop(nativeBuffer);
        String dscr = UnsafeRuntimeAccess.getDescriptionForPtr(blackColorPtr);

        System.out.println("0x" + Long.toHexString(blackColorPtr) + ": " + dscr);
        assertEquals("NSCalibratedRGBColorSpace 1 0 0 1", dscr);
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(SELTest.class);
    }
}
