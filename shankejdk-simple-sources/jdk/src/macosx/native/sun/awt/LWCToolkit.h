/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
 */

#import <pthread.h>
#import <assert.h>

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

#define DEBUG 1

// number of mouse buttons supported
extern int gNumberOfButtons;

// InputEvent mask array
extern jint* gButtonDownMasks;

@interface AWTToolkit : NSObject { }
+ (long) getEventCount;
+ (void) eventCountPlusPlus;
+ (jint) scrollStateWithEvent: (NSEvent*) event;
+ (BOOL) hasPreciseScrollingDeltas: (NSEvent*) event;
@end

/*
 * Utility Macros
 */

/** Macro to cast a jlong to an Objective-C object (id). Casts to long on 32-bit systems to quiesce the compiler. */
#define OBJC(jl) ((id)jlong_to_ptr(jl))
