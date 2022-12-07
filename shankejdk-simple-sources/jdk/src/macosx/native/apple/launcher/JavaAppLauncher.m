/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import "apple_launcher_JavaAppLauncher.h"

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>


/*
 * Class:     apple_launcher_JavaAppLauncher
 * Method:    nativeConvertAndRelease
 * Signature: (J)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_apple_launcher_JavaAppLauncher_nativeConvertAndRelease
(JNIEnv *env, jclass clazz, jlong nsObjectPtr) {

    jobject value = NULL;

JNF_COCOA_ENTER(env);

    id obj = jlong_to_ptr(nsObjectPtr);
    value = [[JNFDefaultCoercions defaultCoercer] coerceNSObject:obj withEnv:env];
    CFRelease(obj);

JNF_COCOA_EXIT(env);

    return value;
}

/*
 * Class:     apple_launcher_JavaAppLauncher
 * Method:    nativeInvokeNonPublic
 * Signature: (Ljava/lang/Class;Ljava/lang/reflect/Method;[Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_apple_launcher_JavaAppLauncher_nativeInvokeNonPublic
(JNIEnv *env, jclass clazz, jclass targetClass, jobject targetMethod, jobjectArray args) {
    jmethodID mainMethodID = (*env)->FromReflectedMethod(env, targetMethod);
    if ((*env)->ExceptionOccurred(env)) return;
    (*env)->CallStaticVoidMethod(env, targetClass, mainMethodID, args);
}
