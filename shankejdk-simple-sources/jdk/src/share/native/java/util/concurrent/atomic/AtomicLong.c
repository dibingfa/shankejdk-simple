/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni.h>
#include <jvm.h>
#include "java_util_concurrent_atomic_AtomicLong.h"

JNIEXPORT jboolean JNICALL
Java_java_util_concurrent_atomic_AtomicLong_VMSupportsCS8(JNIEnv *env, jclass cls)
{
    return JVM_SupportsCX8();
}
