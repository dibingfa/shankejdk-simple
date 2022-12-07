/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "CDragSource.h"
#import "CDropTarget.h"

@interface AWTView : NSView<NSTextInputClient, CDragSourceHolder, CDropTargetHolder> {
@private
    jobject m_cPlatformView;

    // Handler for the tracking area needed for Enter/Exit events management.
    NSTrackingArea* rolloverTrackingArea;

    // TODO: NSMenu *contextualMenu;

    // dnd support (see AppKit/NSDragging.h, NSDraggingSource/Destination):
    CDragSource *_dragSource;
    CDropTarget *_dropTarget;

    // Input method data
    jobject fInputMethodLOCKABLE;
    BOOL fKeyEventsNeeded;
    BOOL fProcessingKeystroke;

    BOOL fEnablePressAndHold;
    BOOL fInPressAndHold;
    BOOL fPAHNeedsToSelect;

    id cglLayer; // is a sublayer of view.layer

    BOOL mouseIsOver;
}

@property (nonatomic, retain) id cglLayer;
@property (nonatomic) BOOL mouseIsOver;

- (id) initWithRect:(NSRect) rect platformView:(jobject)cPlatformView windowLayer:(CALayer*)windowLayer;
- (void) deliverJavaMouseEvent: (NSEvent *) event;
- (jobject) awtComponent:(JNIEnv *)env;

+ (AWTView *) awtView:(JNIEnv *)env ofAccessible:(jobject)jaccessible;

// Input method-related events
- (void)setInputMethod:(jobject)inputMethod;
- (void)abandonInput;

@end
