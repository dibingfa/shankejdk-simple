/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
 */

#include "jvm.h"
#include "java_lang_String.h"

JNIEXPORT jobject JNICALL
Java_java_lang_String_intern(JNIEnv *env, jobject this)
{
    return JVM_InternString(env, this);
}
