/*
 * Copyright (c) 2020, Red Hat, Inc.
 */

#include "jni.h"
#include "jvm.h"

#include "jdk_internal_platform_cgroupv1_Metrics.h"

JNIEXPORT jboolean JNICALL
Java_jdk_internal_platform_cgroupv1_Metrics_isUseContainerSupport(JNIEnv *env, jclass ignored)
{
    return JVM_IsUseContainerSupport();
}
