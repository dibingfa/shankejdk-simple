/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_Invoke_FunCall.h"
#include <ffi/ffi.h>
#include <objc/message.h>
#include <JavaNativeFoundation/JavaNativeFoundation.h>

JNIEXPORT void JNICALL Java_com_apple_jobjc_Invoke_00024FunCall_invoke
(JNIEnv *env, jclass clazz, jlong cifPtr, jlong fxnPtr, jlong retValPtr, jlong argsPtr)
{
    ffi_cif *cif = jlong_to_ptr(cifPtr);
    void *fxn    = jlong_to_ptr(fxnPtr);
    void *retVal = jlong_to_ptr(retValPtr);
    void **args  = jlong_to_ptr(argsPtr);

    ffi_call(cif, fxn, retVal, args);
}
