/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_ID.h"

#include <JavaNativeFoundation/JavaNativeFoundation.h>

#include <objc/runtime.h>
#include <objc/message.h>

/*
 * Class:     com_apple_jobjc_ID
 * Method:    getNativeDescription
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_apple_jobjc_ID_getNativeDescription
(JNIEnv *env, jclass clazz, jlong objPtr)
{
    jstring ret = NULL;

JNF_COCOA_ENTER(env);

    NSString *desc = [((id)jlong_to_ptr(objPtr)) description];
    ret = JNFNSToJavaString(env, desc);

JNF_COCOA_EXIT(env);

    return ret;
}
