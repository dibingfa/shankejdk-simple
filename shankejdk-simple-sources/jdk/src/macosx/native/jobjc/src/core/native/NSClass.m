/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_NSClass.h"
#include <objc/runtime.h>

#include <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 * Class:     com_apple_jobjc_NSClass
 * Method:    getNativeClassByName
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_apple_jobjc_NSClass_getNativeClassByName
(JNIEnv *env, jclass clazz, jstring className)
{
    if (className == NULL) return ptr_to_jlong(NULL);
    const char *classNameCStr = (*env)->GetStringUTFChars(env, className, JNI_FALSE);
    const id obj = objc_getClass(classNameCStr);
    (*env)->ReleaseStringUTFChars(env, className, classNameCStr);

    if (obj == nil) return ptr_to_jlong(NULL);
    return ptr_to_jlong(obj);
}

/*
 * Class:     com_apple_jobjc_NSClass
 * Method:    getSuperClass
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_apple_jobjc_NSClass_getSuperClassOfClass
(JNIEnv *env, jclass clazz, jlong clazzPtr)
{
    if (clazzPtr == 0L) return ptr_to_jlong(NULL);
    const Class objClazz = (Class)jlong_to_ptr(clazzPtr);
    return ptr_to_jlong(class_getSuperclass(objClazz));
}

/*
 * Class:     com_apple_jobjc_NSClass
 * Method:    getClassName
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_apple_jobjc_NSClass_getClassNameOfClass
(JNIEnv *env, jclass clazz, jlong clazzPtr)
{
    const char *clazzName = (char *)class_getName((Class)jlong_to_ptr(clazzPtr));
    return (*env)->NewStringUTF(env, clazzName);
}

JNIEXPORT jlong JNICALL Java_com_apple_jobjc_NSClass_getClass
(JNIEnv *env, jclass clazz, jlong objPtr)
{
    id obj = (id)jlong_to_ptr(objPtr);
    return ptr_to_jlong(object_getClass(obj));
}
