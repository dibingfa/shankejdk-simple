/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 */

#include "jni.h"
#include "jni_util.h"
#include "jlong.h"
#include "jvm.h"
#include "jdk_util.h"

#include "sun_misc_VMSupport.h"

typedef jobject (JNICALL *INIT_AGENT_PROPERTIES_FN)(JNIEnv *, jobject);

static INIT_AGENT_PROPERTIES_FN InitAgentProperties_fp = NULL;

JNIEXPORT jobject JNICALL
Java_sun_misc_VMSupport_initAgentProperties(JNIEnv *env, jclass cls, jobject props)
{
    if (InitAgentProperties_fp == NULL) {
        if (!JDK_InitJvmHandle()) {
            JNU_ThrowInternalError(env,
                 "Handle for JVM not found for symbol lookup");
            return NULL;
        }
        InitAgentProperties_fp = (INIT_AGENT_PROPERTIES_FN)
            JDK_FindJvmEntry("JVM_InitAgentProperties");
        if (InitAgentProperties_fp == NULL) {
            JNU_ThrowInternalError(env,
                 "Mismatched VM version: JVM_InitAgentProperties not found");
            return NULL;
        }
    }
    return (*InitAgentProperties_fp)(env, props);
}

JNIEXPORT jstring JNICALL
Java_sun_misc_VMSupport_getVMTemporaryDirectory(JNIEnv *env, jclass cls)
{
    return JVM_GetTemporaryDirectory(env);
}
