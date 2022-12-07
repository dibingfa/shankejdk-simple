/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "apple_laf_JRSUIFocus.h"
#import "apple_laf_JRSUIControl.h"

#include <Carbon/Carbon.h>


/*
 * Class:     apple_laf_JRSUIFocus
 * Method:    beginNativeFocus
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_apple_laf_JRSUIFocus_beginNativeFocus
(JNIEnv *env, jclass clazz, jlong cgContext, jint ringStyle)
{
    if (cgContext == 0L) return apple_laf_JRSUIFocus_NULL_CG_REF;
    CGContextRef cgRef = (CGContextRef)jlong_to_ptr(cgContext);

    OSStatus status = HIThemeBeginFocus(cgRef, ringStyle, NULL);
    return status == noErr ? apple_laf_JRSUIFocus_SUCCESS : status;
}

/*
 * Class:     apple_laf_JRSUIFocus
 * Method:    endNativeFocus
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_apple_laf_JRSUIFocus_endNativeFocus
(JNIEnv *env, jclass clazz, jlong cgContext)
{
    if (cgContext == 0L) return apple_laf_JRSUIFocus_NULL_CG_REF;
    CGContextRef cgRef = (CGContextRef)jlong_to_ptr(cgContext);

    OSStatus status = HIThemeEndFocus(cgRef);
    return status == noErr ? apple_laf_JRSUIFocus_SUCCESS : status;
}
