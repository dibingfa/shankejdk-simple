/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Coder.PointerCoder;
import com.apple.jobjc.Coder.PrimitivePointerCoder;
import com.apple.jobjc.Coder.VoidCoder;
import com.apple.jobjc.Invoke.FunCall;

public class FunctionTest extends PooledTestCase {
    NativeArgumentBuffer nativeBuffer;
    JObjCRuntime runtime;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        nativeBuffer = UnsafeRuntimeAccess.getNativeBuffer();
        runtime = nativeBuffer.runtime;
    }

    public void testInvokeNoParamNoReturn() throws Throwable {
        final FunCall fxn = UnsafeRuntimeAccess.createFunCall(TestUtils.getAppKit(), "NSBeep", VoidCoder.INST);

        fxn.init(nativeBuffer);
        fxn.invoke(nativeBuffer);
    }

    public void testInvokeNoParams() throws Throwable {
        final FunCall fxn = UnsafeRuntimeAccess.createFunCall(TestUtils.getFoundation(), "NSFullUserName", PointerCoder.INST);

        fxn.init(nativeBuffer);
        fxn.invoke(nativeBuffer);

        final long ptr = PrimitivePointerCoder.INST.pop(nativeBuffer);
        System.out.println("0x" + Long.toHexString(ptr) + ": " + UnsafeRuntimeAccess.getDescriptionForPtr(ptr));
    }

    public void testInvokeOneParam() throws Throwable {
        final FunCall getHomeDirFxn = UnsafeRuntimeAccess.createFunCall(TestUtils.getAppKit(), "NSHomeDirectory", PointerCoder.INST);

        getHomeDirFxn.init(nativeBuffer);
        getHomeDirFxn.invoke(nativeBuffer);

        final long homeDirPtr = PrimitivePointerCoder.INST.pop(nativeBuffer);
        System.out.println("0x" + Long.toHexString(homeDirPtr) + ": " + UnsafeRuntimeAccess.getDescriptionForPtr(homeDirPtr));

        final FunCall getTypeOfFxn = UnsafeRuntimeAccess.createFunCall(TestUtils.getFoundation(), "NSLog", PointerCoder.INST, PointerCoder.INST);
        getTypeOfFxn.init(nativeBuffer);
        PrimitivePointerCoder.INST.push(runtime, nativeBuffer, homeDirPtr);
        getTypeOfFxn.invoke(nativeBuffer);

    //    long typePtr = PointerCoder.pointer_coder.popPtr(nativeBuffer);
    //    System.out.println("0x" + Long.toHexString(typePtr) + ": " + TestUtils.getDescriptionForPtr(typePtr));
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(FunctionTest.class);
    }
}
