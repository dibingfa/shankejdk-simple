/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
#include <JavaNativeFoundation/JavaNativeFoundation.h>
#include <Cocoa/Cocoa.h>

#include "com_apple_jobjc_BenchFunCall.h"
#include <math.h>

JNIEXPORT jdouble JNICALL Java_com_apple_jobjc_BenchFunCall_jniSin
(JNIEnv *env, jclass clazz, jdouble x)
{
    return (jdouble) sin((double) x);
}


#include "com_apple_jobjc_BenchIDPop.h"

JNIEXPORT void JNICALL Java_com_apple_jobjc_BenchIDPop_jniCFRetain
(JNIEnv *env, jclass clazz, jlong x)
{
    CFRetain(jlong_to_ptr(x));
}

JNIEXPORT void JNICALL Java_com_apple_jobjc_BenchIDPop_jniCFRelease
(JNIEnv *env, jclass clazz, jlong x)
{
    CFRelease(jlong_to_ptr(x));
}

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_BenchIDPop_jniNSStringAlloc
(JNIEnv *env, jclass clazz)
{
    return ptr_to_jlong([NSString alloc]);
}

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_BenchIDPop_jniNSStringAllocAndRetain
(JNIEnv *env, jclass clazz)
{
    return ptr_to_jlong(CFRetain([NSString alloc]));
}

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_BenchIDPop_jniNSStringCached
(JNIEnv *env, jclass clazz)
{
    static jlong str = 0;
    if(!str) str = ptr_to_jlong([NSString alloc]);
    return str;
}
