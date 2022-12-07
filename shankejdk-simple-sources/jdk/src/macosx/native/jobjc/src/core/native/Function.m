/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_Function.h"

#define MACOSX
#include <dlfcn.h>
#include <JavaNativeFoundation/JavaNativeFoundation.h>

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_Function_getFxnPtrForFunctionName
(JNIEnv *env, jclass clazz, jstring fxnName)
{
    const char *functionName = (*env)->GetStringUTFChars(env, fxnName, NULL);
    void *fxnPtr = dlsym(RTLD_SELF, functionName);
    (*env)->ReleaseStringUTFChars(env, fxnName, functionName);
    return ptr_to_jlong(fxnPtr);
}

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_Function_getFxnPtrForFunctionNameAndLib
(JNIEnv *env, jclass clazz, jlong frameworkPtr, jstring fxnName)
{
    void *frameworkHandle = jlong_to_ptr(frameworkPtr);

    const char *functionName = (*env)->GetStringUTFChars(env, fxnName, NULL);
    void *fxnPtr = dlsym(frameworkHandle, functionName);
    (*env)->ReleaseStringUTFChars(env, fxnName, functionName);

    return ptr_to_jlong(fxnPtr);
}
