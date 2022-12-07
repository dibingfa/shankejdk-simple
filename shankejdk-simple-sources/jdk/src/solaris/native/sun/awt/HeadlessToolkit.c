/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni.h>
#include <jni_util.h>

#ifdef HEADLESS

extern JavaVM *jvm;

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved)
{
    jvm = vm;
    return JNI_VERSION_1_2;
}

JNIEXPORT jboolean JNICALL AWTIsHeadless() {
    return JNI_TRUE;
}

#endif
