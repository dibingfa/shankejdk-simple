/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_NativeBuffer.h"

#define MACOSX
#include <ffi/ffi.h>
#include <JavaNativeFoundation/JavaNativeFoundation.h>

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_NativeBuffer_getPtrOfBuffer
(JNIEnv *env, jclass clazz, jobject buffer)
{
    if (buffer == NULL) return ptr_to_jlong(0);
    return ptr_to_jlong((*env)->GetDirectBufferAddress(env, buffer));
}
