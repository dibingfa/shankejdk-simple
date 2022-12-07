/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "sun_lwawt_macosx_CFRetainedResource.h"


/*
 * Class:     sun_lwawt_macosx_CFRetainedResource
 * Method:    nativeCFRelease
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CFRetainedResource_nativeCFRelease
(JNIEnv *env, jclass clazz, jlong ptr, jboolean releaseOnAppKitThread)
{
    if (releaseOnAppKitThread) {
        // Releasing resources on the main AppKit message loop only
        // Releasing resources on the nested loops may cause dangling 
        // pointers after the nested loop is exited
        if ([NSApp respondsToSelector:@selector(postRunnableEvent:)]) {
            [NSApp postRunnableEvent:^() {
                CFRelease(jlong_to_ptr(ptr));
            }];
        } else {
            // could happen if we are embedded inside SWT/FX application,
            [JNFRunLoop performOnMainThreadWaiting:NO withBlock:^() {
                CFRelease(jlong_to_ptr(ptr));
            }];
        }
    } else {

JNF_COCOA_ENTER(env);

        CFRelease(jlong_to_ptr(ptr));

JNF_COCOA_EXIT(env);

    }
}
