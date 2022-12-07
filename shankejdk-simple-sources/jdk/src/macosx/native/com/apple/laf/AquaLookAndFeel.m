/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 *    Empty JNI_OnLoad - needed to prevent:
 *    <rdar://4984599> AWT's JNI_OnLoad called multiple times
 *    Please remove when <rdar://5121166> has been resolved.
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    return JNI_VERSION_1_4;
}
