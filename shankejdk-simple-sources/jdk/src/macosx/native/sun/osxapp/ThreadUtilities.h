/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef __THREADUTILITIES_H
#define __THREADUTILITIES_H

#include <pthread.h>

#import "AWT_debug.h"


// --------------------------------------------------------------------------
#ifndef PRODUCT_BUILD

// Turn on the AWT thread assert mechanism. See below for different variants.
// TODO: don't enable this for production builds...
#define AWT_THREAD_ASSERTS

#endif /* PRODUCT_BUILD */
// --------------------------------------------------------------------------

// --------------------------------------------------------------------------
#ifdef AWT_THREAD_ASSERTS

// Turn on to have awt thread asserts display a message on the console.
#define AWT_THREAD_ASSERTS_MESSAGES

// Turn on to have awt thread asserts use an environment variable switch to
// determine if assert should really be called.
//#define AWT_THREAD_ASSERTS_ENV_ASSERT

// Define AWT_THREAD_ASSERTS_WAIT to make asserts halt the asserting thread
// for debugging purposes.
//#define AWT_THREAD_ASSERTS_WAIT

#ifdef AWT_THREAD_ASSERTS_MESSAGES

#define AWT_THREAD_ASSERTS_NOT_APPKIT_MESSAGE \
    AWT_DEBUG_LOG(@"Not running on AppKit thread 0 when expected.")

#define AWT_THREAD_ASSERTS_ON_APPKIT_MESSAGE \
    AWT_DEBUG_LOG(@"Running on AppKit thread 0 when not expected.")

#ifdef AWT_THREAD_ASSERTS_ENV_ASSERT

extern int sAWTThreadAsserts;
#define AWT_THREAD_ASSERTS_ENV_ASSERT_CHECK    \
do {                                           \
    if (sAWTThreadAsserts) {                   \
        NSLog(@"\tPlease run this java program again with setenv COCOA_AWT_DISABLE_THREAD_ASSERTS to proceed with a warning."); \
        assert(NO);                            \
    }                                          \
} while (0)

#else

#define AWT_THREAD_ASSERTS_ENV_ASSERT_CHECK do {} while (0)

#endif /* AWT_THREAD_ASSERTS_ENV_ASSERT */

#define AWT_ASSERT_APPKIT_THREAD               \
do {                                           \
    if (pthread_main_np() == 0) {              \
        AWT_THREAD_ASSERTS_NOT_APPKIT_MESSAGE; \
        AWT_DEBUG_BUG_REPORT_MESSAGE;          \
        AWT_THREAD_ASSERTS_ENV_ASSERT_CHECK;   \
    }                                          \
} while (0)

#define AWT_ASSERT_NOT_APPKIT_THREAD           \
do {                                           \
    if (pthread_main_np() != 0) {              \
        AWT_THREAD_ASSERTS_ON_APPKIT_MESSAGE;  \
        AWT_DEBUG_BUG_REPORT_MESSAGE;          \
        AWT_THREAD_ASSERTS_ENV_ASSERT_CHECK;   \
    }                                          \
} while (0)

#endif /* AWT_THREAD_ASSERTS_MESSAGES */

#ifdef AWT_THREAD_ASSERTS_WAIT

#define AWT_ASSERT_APPKIT_THREAD      \
do {                                  \
    while (pthread_main_np() == 0) {} \
} while (0)

#define AWT_ASSERT_NOT_APPKIT_THREAD  \
do {                                  \
    while (pthread_main_np() != 0) {} \
} while (0)

#endif /* AWT_THREAD_ASSERTS_WAIT */

#else /* AWT_THREAD_ASSERTS */

#define AWT_ASSERT_APPKIT_THREAD     do {} while (0)
#define AWT_ASSERT_NOT_APPKIT_THREAD do {} while (0)

#endif /* AWT_THREAD_ASSERTS */
// --------------------------------------------------------------------------

__attribute__((visibility("default")))
@interface ThreadUtilities { }

+ (JNIEnv*)getJNIEnv;
+ (JNIEnv*)getJNIEnvUncached;
+ (void)detachCurrentThread;
+ (void)setAppkitThreadGroup:(jobject)group;
+ (void)setAWTEmbedded:(BOOL)embedded;
+ (BOOL)isAWTEmbedded;

//Wrappers for the corresponding JNFRunLoop methods with a check for main thread
+ (void)performOnMainThreadWaiting:(BOOL)wait block:(void (^)())block;
+ (void)performOnMainThread:(SEL)aSelector on:(id)target withObject:(id)arg waitUntilDone:(BOOL)wait;
@end

void OSXAPP_SetJavaVM(JavaVM *vm);

#endif /* __THREADUTILITIES_H */
