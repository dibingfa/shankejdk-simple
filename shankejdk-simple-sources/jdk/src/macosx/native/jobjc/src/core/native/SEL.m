/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_SEL.h"

#include <JavaNativeFoundation/JavaNativeFoundation.h>
#include <objc/message.h>

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_SEL_getSelectorPtr
(JNIEnv *env, jclass jclazz, jstring selName)
{
    const char *selNameAsChars = (*env)->GetStringUTFChars(env, selName, JNI_FALSE);
    const SEL sel = sel_registerName(selNameAsChars);
    (*env)->ReleaseStringUTFChars(env, selName, selNameAsChars);
    return ptr_to_jlong((void*)sel);
}

JNIEXPORT jstring JNICALL Java_com_apple_jobjc_SEL_getSelectorName
(JNIEnv *env, jclass jclazz, jlong selPtr)
{
    return (*env)->NewStringUTF(env, sel_getName(jlong_to_ptr(selPtr)));
}
