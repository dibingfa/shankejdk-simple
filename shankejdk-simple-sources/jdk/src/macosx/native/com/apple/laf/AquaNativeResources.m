/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import "com_apple_laf_AquaNativeResources.h"

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 * Class:     com_apple_laf_AquaNativeResources
 * Method:    getWindowBackgroundColor
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_apple_laf_AquaNativeResources_getWindowBackgroundColor
    (JNIEnv *env, jclass clz)
{
    // TODO(cpc): this code is currently disabled at the Java level
#if 0
    NSColor* color = nil;
JNF_COCOA_ENTER(env);
    color = [NSColor lightGrayColor];//[AWTColor getMagicBackgroundColor];
    if (color) CFRetain(color); // GC
JNF_COCOA_EXIT(env);
    return ptr_to_jlong(color);
#else
    return 0L;
#endif
}
