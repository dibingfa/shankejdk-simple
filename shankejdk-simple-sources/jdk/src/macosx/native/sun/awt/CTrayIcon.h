/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni.h>
#import <Foundation/Foundation.h>
#import <AppKit/AppKit.h>

#import "CPopupMenu.h"

#ifndef _Included_sun_awt_lwmacosx_CTrayIcon
#define _Included_sun_awt_lwmacosx_CTrayIcon

#ifdef __cplusplus
extern "C" {
#endif

@class AWTTrayIconView;

/*
 * AWTTrayIcon
 */
@interface AWTTrayIcon : NSObject {
    jobject peer;
    AWTTrayIconView *view;
    NSStatusItem *theItem;
}

- (id) initWithPeer:(jobject)thePeer;
- (void) setTooltip:(NSString *)tooltip;
- (NSStatusItem *)theItem;
- (jobject) peer;
- (void) setImage:(NSImage *) imagePtr sizing:(BOOL)autosize;
- (NSPoint) getLocationOnScreen;
- (void) deliverJavaMouseEvent:(NSEvent*) event;

@end //AWTTrayIcon

//==================================================================================
/*
 * AWTTrayIconView */
@interface AWTTrayIconView : NSView <NSMenuDelegate> {
@public
    AWTTrayIcon *trayIcon;
    NSImage* image;
    BOOL isHighlighted;
}
-(id)initWithTrayIcon:(AWTTrayIcon *)theTrayIcon;
-(void)setHighlighted:(BOOL)aFlag;
-(void)setImage:(NSImage*)anImage;
-(void)setTrayIcon:(AWTTrayIcon*)theTrayIcon;

@end //AWTTrayIconView

#ifdef __cplusplus
}
#endif
#endif
