/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#import <AppKit/AppKit.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>
#import <objc/message.h>

#import "ThreadUtilities.h"


// The following must be named "jvm", as there are extern references to it in AWT
JavaVM *jvm = NULL;
static JNIEnv *appKitEnv = NULL;
static jobject appkitThreadGroup = NULL;
static BOOL awtEmbedded = NO;

static inline void attachCurrentThread(void** env) {
    if ([NSThread isMainThread]) {
        JavaVMAttachArgs args;
        args.version = JNI_VERSION_1_4;
        args.name = "AppKit Thread";
        args.group = appkitThreadGroup;
        (*jvm)->AttachCurrentThreadAsDaemon(jvm, env, &args);
    } else {
        (*jvm)->AttachCurrentThreadAsDaemon(jvm, env, NULL);
    }
}

@implementation ThreadUtilities

+ (JNIEnv*)getJNIEnv {
AWT_ASSERT_APPKIT_THREAD;
    if (appKitEnv == NULL) {
        attachCurrentThread((void **)&appKitEnv);
    }
    return appKitEnv;
}

+ (JNIEnv*)getJNIEnvUncached {
    JNIEnv *env = NULL;
    attachCurrentThread((void **)&env);
    return env;
}

+ (void)detachCurrentThread {
    (*jvm)->DetachCurrentThread(jvm);
}

+ (void)setAppkitThreadGroup:(jobject)group {
    appkitThreadGroup = group;
}

+ (void)performOnMainThreadWaiting:(BOOL)wait block:(void (^)())block {
    if ([NSThread isMainThread] && wait == YES) {
        block(); 
    } else { 
        [JNFRunLoop performOnMainThreadWaiting:wait withBlock:block]; 
    }
}

+ (void)performOnMainThread:(SEL)aSelector on:(id)target withObject:(id)arg waitUntilDone:(BOOL)wait {
    if ([NSThread isMainThread] && wait == YES) {
        [target performSelector:aSelector withObject:arg];
    } else {
        [JNFRunLoop performOnMainThread:aSelector on:target withObject:arg waitUntilDone:wait];
    }
}

+ (void)setAWTEmbedded:(BOOL)embedded {
    awtEmbedded = embedded;
}

+ (BOOL)isAWTEmbedded {
    return awtEmbedded;
}

@end


void OSXAPP_SetJavaVM(JavaVM *vm)
{
    jvm = vm;
}

