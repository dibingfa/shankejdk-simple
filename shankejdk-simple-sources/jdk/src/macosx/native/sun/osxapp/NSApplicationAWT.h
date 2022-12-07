/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

@interface NSApplicationAWT : NSApplication {
    NSString *fApplicationName;
    NSWindow *eventTransparentWindow;
    NSTimeInterval dummyEventTimestamp;
    NSConditionLock* seenDummyEventLock;
}

- (void) finishLaunching;
- (void) registerWithProcessManager;
- (void) setDockIconWithEnv:(JNIEnv *)env;
- (void) postDummyEvent:(bool) useCocoa;
- (void) postRunnableEvent:(void (^)())block;
- (void) waitForDummyEvent:(double) timeout;

+ (void) runAWTLoopWithApp:(NSApplication*)app;

@end

@interface NSApplication (CustomNIBAdditions)

// Returns whether or not application is using its default NIB
- (BOOL)usingDefaultNib;

@end

void OSXAPP_SetApplicationDelegate(id <NSApplicationDelegate> delegate);

