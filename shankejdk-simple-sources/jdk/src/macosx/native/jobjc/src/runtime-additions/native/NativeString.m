/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_Utils_Strings.h"

#include <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 * Class:     com_apple_jobjc_Utils_Strings
 * Method:    getNativeNSStringForJavaString
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_apple_jobjc_Utils_00024Strings_getNativeNSStringForJavaString
(JNIEnv *env, jclass clazz, jstring javaString)
{
    return ptr_to_jlong(JNFJavaToNSString(env, javaString));
}

/*
 * Class:     com_apple_jobjc_Utils_Strings
 * Method:    getNativeJavaStringForNSString
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_apple_jobjc_Utils_00024Strings_getNativeJavaStringForNSString
(JNIEnv *env, jclass clazz, jlong nativeString)
{
    return JNFNSToJavaString(env, (NSString *)jlong_to_ptr(nativeString));
}
