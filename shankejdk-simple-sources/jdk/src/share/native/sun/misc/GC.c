/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni.h>
#include <jvm.h>
#include "sun_misc_GC.h"


JNIEXPORT jlong JNICALL
Java_sun_misc_GC_maxObjectInspectionAge(JNIEnv *env, jclass cls)
{
    return JVM_MaxObjectInspectionAge();
}
