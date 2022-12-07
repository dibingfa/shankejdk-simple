/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef CDragSource_h
#define CDragSource_h

#import <Cocoa/Cocoa.h>
#include <jni.h>

@class CDragSource;

@protocol CDragSourceHolder
- (void) setDragSource:(CDragSource *)source;
@end

@interface CDragSource : NSObject {
@private
    NSView<CDragSourceHolder>* fView;
    jobject            fComponent;
    jobject            fDragSourceContextPeer;

    jobject            fTransferable;
    jobject            fTriggerEvent;
    jlong            fTriggerEventTimeStamp;
    NSPoint            fDragPos;
    jint                fClickCount;
    jint                fModifiers;

    NSImage*        fDragImage;
    NSPoint            fDragImageOffset;

    jint                fSourceActions;
    jlongArray        fFormats;
    jobject            fFormatMap;

    jint                     fDragKeyModifiers;
    jint                     fDragMouseModifiers;
}

// Common methods:
- (id)        init:(jobject)jDragSourceContextPeer
         component:(jobject)jComponent
           control:(id)control
      transferable:(jobject)jTransferable
      triggerEvent:(jobject)jTrigger
          dragPosX:(jint)dragPosX
          dragPosY:(jint)dragPosY
         modifiers:(jint)extModifiers
        clickCount:(jint)clickCount
         timeStamp:(jlong)timeStamp
         dragImage:(jobject)jDragImage
  dragImageOffsetX:(jint)jDragImageOffsetX
  dragImageOffsetY:(jint)jDragImageOffsetY
     sourceActions:(jint)jSourceActions
           formats:(jlongArray)jFormats
         formatMap:(jobject)jFormatMap;

- (void)removeFromView:(JNIEnv *)env;

- (void)drag;

// dnd APIs (see AppKit/NSDragging.h, NSDraggingSource):
- (NSDragOperation)draggingSourceOperationMaskForLocal:(BOOL)flag;
- (void)draggedImage:(NSImage *)image beganAt:(NSPoint)screenPoint;
- (void)draggedImage:(NSImage *)image endedAt:(NSPoint)screenPoint operation:(NSDragOperation)operation;
- (void)draggedImage:(NSImage *)image movedTo:(NSPoint)screenPoint;
- (BOOL)ignoreModifierKeysWhileDragging;

@end

#endif // CDragSource_h
