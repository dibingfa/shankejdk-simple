/*
 * Copyright (c) 2002, 2018, Oracle and/or its affiliates. All rights reserved.
 */

#include "jvm.h"

JNIEXPORT void JNICALL
Java_java_nio_Bits_copySwapMemory0(JNIEnv *env, jclass cls, jobject srcObj,
                                   jlong srcOffset, jobject dstObj,
                                   jlong dstOffset, jlong size,
                                   jlong elemSize) {
  JVM_CopySwapMemory(env, srcObj, srcOffset, dstObj, dstOffset,
                     size, elemSize);
}
