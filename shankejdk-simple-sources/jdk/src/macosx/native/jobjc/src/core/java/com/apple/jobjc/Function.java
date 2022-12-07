/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


public class Function {
    private static native long getFxnPtrForFunctionName(final String functionName);
    private static native long getFxnPtrForFunctionNameAndLib(final long libPtr, final String functionName);

    final long fxnPtr;

    Function(final long fxnPtr) {
        this.fxnPtr = fxnPtr;
    }

    public Function(final String name) {
        this(getFxnPtr(name));
    }

    public Function(final MacOSXFramework framework, final String name) {
        this(getFxnPtr(name, framework));
    }

    static long getFxnPtr(final String name){
        long fxnPtr = getFxnPtrForFunctionName(name);
        if(fxnPtr == 0) throw new RuntimeException("Function pointer for " + name + " not found in runtime.");
        return fxnPtr;
    }

    static long getFxnPtr(final String name, final MacOSXFramework framework){
        long fxnPtr = 0;
        for(int i = 0; fxnPtr == 0 && i < framework.nativeLibPtrs.length; i++){
            fxnPtr = getFxnPtrForFunctionNameAndLib(framework.nativeLibPtrs[i], name);
            if(fxnPtr != 0) return fxnPtr;
        }
        throw new RuntimeException("Function pointer for " + name + " not found in framework " + framework + ".");
    }
}
