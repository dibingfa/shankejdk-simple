/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_NativeObjectLifecycleManager.h"

#include <JavaNativeFoundation/JavaNativeFoundation.h>


/*
 * Class:     com_apple_jobjc_NativeObjectLifecycleManager
 * Method:    retainNativeObject
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_apple_jobjc_NativeObjectLifecycleManager_retainNativeObject
(JNIEnv *env, jclass clazz, jlong ptr)
{
    if (ptr == 0L) return;
    CFRetain(jlong_to_ptr(ptr));
}

/*
 * Class:     com_apple_jobjc_NativeObjectLifecycleManager
 * Method:    releaseNativeObject
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_apple_jobjc_NativeObjectLifecycleManager_releaseNativeObject
(JNIEnv *env, jclass clazz, jlong ptr)
{
    if (ptr == 0L) return;
    CFRelease(jlong_to_ptr(ptr));
}

/*
 * Class:     com_apple_jobjc_NativeObjectLifecycleManager
 * Method:    freeNativeObject
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_apple_jobjc_NativeObjectLifecycleManager_freeNativeObject
(JNIEnv *env, jclass clazz, jlong ptr)
{
    if (ptr == 0L) return;
    free(jlong_to_ptr(ptr));
}
