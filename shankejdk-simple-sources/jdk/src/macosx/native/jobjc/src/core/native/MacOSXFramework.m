/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_MacOSXFramework.h"

#include <dlfcn.h>
#include <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 * Class:     com_apple_jobjc_MacOSXFramework
 * Method:    retainFramework
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_apple_jobjc_MacOSXFramework_retainFramework
(JNIEnv *env, jclass clazz, jstring frameworkName)
{
    if (frameworkName == NULL) return ptr_to_jlong(NULL);
    const char *frameworkNameCStr = (*env)->GetStringUTFChars(env, frameworkName, JNI_FALSE);
    const void *library = dlopen(frameworkNameCStr, RTLD_LOCAL);
    (*env)->ReleaseStringUTFChars(env, frameworkName, frameworkNameCStr);
    return ptr_to_jlong(library);
}

/*
 * Class:     com_apple_jobjc_MacOSXFramework
 * Method:    releaseFramework
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_apple_jobjc_MacOSXFramework_releaseFramework
(JNIEnv *env, jclass clazz, jlong frameworkPtr)
{
    dlclose(jlong_to_ptr(frameworkPtr));
}

JNIEXPORT void JNICALL Java_com_apple_jobjc_MacOSXFramework_getConstant
(JNIEnv *env, jclass clazz, jlong frameworkPtr, jstring constSymbol, jlong retBuffer, jint size)
{
    const char *symbol = (*env)->GetStringUTFChars(env, constSymbol, JNI_FALSE);
    void *handle = frameworkPtr ? jlong_to_ptr(frameworkPtr) : RTLD_DEFAULT;
    void *data = dlsym(handle, symbol);
    (*env)->ReleaseStringUTFChars(env, constSymbol, symbol);

    if(!data)
        (*env)->ThrowNew(env, (*env)->FindClass(env, "java/lang/RuntimeException"), dlerror());
    else
        memcpy(jlong_to_ptr(retBuffer), data, (size_t) size);
}
