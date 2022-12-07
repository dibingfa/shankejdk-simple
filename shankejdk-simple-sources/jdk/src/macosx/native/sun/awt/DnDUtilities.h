/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef DnDUtilities_h
#define DnDUtilities_h

#import <Cocoa/Cocoa.h>
#include <jni.h>

@interface DnDUtilities : NSObject {
}

// Common methods:
+ (NSString *) javaPboardType;

// Dragging action mapping:
+ (jint)mapNSDragOperationToJava:(NSDragOperation)dragOperation;
+ (NSDragOperation)mapJavaDragOperationToNS:(jint)dragOperation;
+ (jint)mapNSDragOperationMaskToJava:(NSDragOperation)dragOperation;
+ (jint)narrowJavaDropActions:(jint)actions;

// Mouse and key modifiers mapping:
+ (NSUInteger)mapJavaExtModifiersToNSMouseDownButtons:(jint)modifiers;
+ (NSUInteger)mapJavaExtModifiersToNSMouseUpButtons:(jint)modifiers;

// Specialized key and mouse modifiers mapping (for operationChanged)
+ (jint)extractJavaExtKeyModifiersFromJavaExtModifiers:(jint)modifiers;
+ (jint)extractJavaExtMouseModifiersFromJavaExtModifiers:(jint)modifiers;

// Getting the state of the current Drag
+ (NSDragOperation)nsDragOperationForModifiers:(NSUInteger)modifiers;
+ (jint) javaKeyModifiersForNSDragOperation:(NSDragOperation)dragOp;
@end


// Global debugging flag (for drag-and-drop) - this can be overriden locally per file:
#ifndef DND_DEBUG
//    #define DND_DEBUG TRUE
#endif

#if DND_DEBUG
    // Turn DLog (debug log) on for debugging:
    #define    DLog(arg1)                        NSLog(arg1)
    #define    DLog2(arg1, arg2)                NSLog(arg1, arg2)
    #define    DLog3(arg1, arg2, arg3)            NSLog(arg1, arg2, arg3)
    #define    DLog4(arg1, arg2, arg3, arg4)    NSLog(arg1, arg2, arg3, arg4)
    #define    DLog5(arg1, arg2, arg3, arg4, arg5)            NSLog(arg1, arg2, arg3, arg4, arg5)
    #define    DLog6(arg1, arg2, arg3, arg4, arg5, arg6)    NSLog(arg1, arg2, arg3, arg4, arg5, arg6)
#else
    #define    DLog(arg1);
    #define    DLog2(arg1, arg2);
    #define    DLog3(arg1, arg2, arg3);
    #define    DLog4(arg1, arg2, arg3, arg4);
    #define    DLog5(arg1, arg2, arg3, arg4, arg5);
    #define    DLog6(arg1, arg2, arg3, arg4, arg5, arg6);
#endif

#endif // DnDUtilities_h
