/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef _AWTWINDOW_H
#define _AWTWINDOW_H

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "CMenuBar.h"
#import "LWCToolkit.h"


@class AWTView;

@interface AWTWindow : NSObject <NSWindowDelegate> {
@private
    JNFWeakJObjectWrapper *javaPlatformWindow;
    CMenuBar *javaMenuBar;
    NSSize javaMinSize;
    NSSize javaMaxSize;
    jint styleBits;
    BOOL isEnabled;
    NSWindow *nsWindow;
    AWTWindow *ownerWindow;
    jint preFullScreenLevel;
    BOOL isMinimizing;
}

// An instance of either AWTWindow_Normal or AWTWindow_Panel
@property (nonatomic, retain) NSWindow *nsWindow;

@property (nonatomic, retain) JNFWeakJObjectWrapper *javaPlatformWindow;
@property (nonatomic, retain) CMenuBar *javaMenuBar;
@property (nonatomic, retain) AWTWindow *ownerWindow;
@property (nonatomic) NSSize javaMinSize;
@property (nonatomic) NSSize javaMaxSize;
@property (nonatomic) jint styleBits;
@property (nonatomic) BOOL isEnabled;
@property (nonatomic) jint preFullScreenLevel;
@property (nonatomic) BOOL isMinimizing;


- (id) initWithPlatformWindow:(JNFWeakJObjectWrapper *)javaPlatformWindow
                  ownerWindow:owner
                    styleBits:(jint)styleBits
                    frameRect:(NSRect)frameRect
                  contentView:(NSView *)contentView;

- (BOOL) isTopmostWindowUnderMouse;

// NSWindow overrides delegate methods
- (BOOL) canBecomeKeyWindow;
- (BOOL) canBecomeMainWindow;
- (BOOL) worksWhenModal;
- (void)sendEvent:(NSEvent *)event;

+ (void) setLastKeyWindow:(AWTWindow *)window;
+ (AWTWindow *) lastKeyWindow;

@end

@interface AWTWindow_Normal : NSWindow
- (id) initWithDelegate:(AWTWindow *)delegate
              frameRect:(NSRect)rect
              styleMask:(NSUInteger)styleMask
            contentView:(NSView *)view;
@end

@interface AWTWindow_Panel : NSPanel
- (id) initWithDelegate:(AWTWindow *)delegate
              frameRect:(NSRect)rect
              styleMask:(NSUInteger)styleMask
            contentView:(NSView *)view;
@end

#endif _AWTWINDOW_H
