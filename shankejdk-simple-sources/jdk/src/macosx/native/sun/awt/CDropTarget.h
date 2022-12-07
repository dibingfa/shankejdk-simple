/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef CDropTarget_h
#define CDropTarget_h

#import <AppKit/AppKit.h>
#import <jni.h>

@class ControlModel;

@class CDropTarget;

@protocol CDropTargetHolder
- (void) setDropTarget:(CDropTarget *)target;
@end

@interface CDropTarget : NSObject {
@private
    NSView<CDropTargetHolder>* fView;
    jobject            fComponent;
    jobject            fDropTarget;
    jobject            fDropTargetContextPeer;
}

+ (CDropTarget *) currentDropTarget;

// Common methods:
- (id)init:(jobject)dropTarget component:(jobject)jcomponent peer:(jobject)jpeer control:(id)control;
- (void)controlModelControlValid;
- (void)removeFromView:(JNIEnv *)env;

- (NSInteger)getDraggingSequenceNumber;
- (jobject)copyDraggingDataForFormat:(jlong)format;
- (void)javaDraggingEnded:(jlong)draggingSequenceNumber success:(BOOL)jsuccess action:(jint)jdropaction;

// dnd APIs (see AppKit/NSDragging.h, NSDraggingDestination):
- (NSDragOperation)draggingEntered:(id <NSDraggingInfo>)sender;
- (NSDragOperation)draggingUpdated:(id <NSDraggingInfo>)sender;
- (void)draggingExited:(id <NSDraggingInfo>)sender;
- (BOOL)prepareForDragOperation:(id <NSDraggingInfo>)sender;
- (BOOL)performDragOperation:(id <NSDraggingInfo>)sender;
- (void)concludeDragOperation:(id <NSDraggingInfo>)sender;
- (void)draggingEnded:(id <NSDraggingInfo>)sender;

- (jint)currentJavaActions;

@end

#endif // CDropTarget_h
