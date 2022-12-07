/*
 * Copyright (c) 1998, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#include "java_awt_Insets.h"
#include "jni_util.h"

#include "awt_Insets.h"

struct InsetsIDs insetsIDs;


JNIEXPORT void JNICALL
Java_java_awt_Insets_initIDs(JNIEnv *env, jclass cls)
{
    CHECK_NULL(insetsIDs.top = (*env)->GetFieldID(env, cls, "top", "I"));
    CHECK_NULL(insetsIDs.bottom = (*env)->GetFieldID(env, cls, "bottom", "I"));
    CHECK_NULL(insetsIDs.left = (*env)->GetFieldID(env, cls, "left", "I"));
    CHECK_NULL(insetsIDs.right = (*env)->GetFieldID(env, cls, "right", "I"));
}
