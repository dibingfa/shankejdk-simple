/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_Utils_Numbers.h"

#include <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 * Class:     com_apple_jobjc_Utils_Numbers
 * Method:    getNativeNSNumberForJavaNumber
 * Signature: (Ljava/lang/Number;)J
 */
JNIEXPORT jlong JNICALL Java_com_apple_jobjc_Utils_00024Numbers_getNativeNSNumberForJavaNumber
(JNIEnv *env, jclass clazz, jobject javaNumber)
{
    return ptr_to_jlong(JNFJavaToNSNumber(env, javaNumber));
}

/*
 * Class:     com_apple_jobjc_Utils_Numbers
 * Method:    getNativeJavaNumberForNSNumber
 * Signature: (J)Ljava/lang/Number;
 */
JNIEXPORT jobject JNICALL Java_com_apple_jobjc_Utils_00024Numbers_getNativeJavaNumberForNSNumber
(JNIEnv *env, jclass clazz, jlong nativeNumber)
{
    return JNFNSToJavaNumber(env, (NSNumber *)jlong_to_ptr(nativeNumber));
}
