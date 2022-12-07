/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Invoke.FunCall;
import com.apple.jobjc.Invoke.MsgSend;

public class UnsafeRuntimeAccess {
    public static NativeArgumentBuffer getNativeBuffer() {
        return NativeArgumentBuffer.getThreadLocalBuffer(JObjCRuntime.getInstance());
    }

    public static String getClassNameFor(final long obj) {
        return NSClass.getClassNameOfClass(obj);
    }

    public static String getClassNameFor(final NSClass cls) {
        return NSClass.getClassNameOfClass(cls.ptr);
    }

    public static NSClass<?> getSuperClass(final NSClass<? extends ID> clazz) {
        return clazz.getSuperClass();
    }

    public static String getDescriptionForPtr(final long objPtr) {
        return ID.getNativeDescription(objPtr);
    }

    public static MacOSXFramework getFramework(final String[] frameworkLibs) {
        return new MacOSXFramework(JObjCRuntime.getInstance(), frameworkLibs);
    }

    public static FunCall createFunCall(final MacOSXFramework framework, final String fxnName, final Coder returnCoder, final Coder ... argCoders) {
        return new FunCall(framework, fxnName, returnCoder, argCoders);
    }

    public static MsgSend createMsgSend(final NSClass<?> clazz, final String selName, final Coder returnCoder, final Coder ... argCoders) {
        return new MsgSend(clazz.getRuntime(), selName, returnCoder, argCoders);
    }

    public static NSClass<ID> getNSClass(final MacOSXFramework framework, final String name) {
        return new NSClass<ID>(name, framework.getRuntime());
    }

    public static long getObjPtr(final ID obj) {
        return obj.ptr;
    }
}
