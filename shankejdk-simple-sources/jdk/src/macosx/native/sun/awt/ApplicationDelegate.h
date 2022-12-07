/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>

@class CMenuBar;

//
// This class supplies the native implementation for the com.apple.eawt.Application class.  We
// implement this as a delegate rather than extend NSApplication because we can not rely on AWT always
// being the creator of the NSApplication NSApp instance.
//
@interface ApplicationDelegate : NSObject<NSApplicationDelegate>
{
    NSMenuItem *fPreferencesMenu;
    NSMenuItem *fAboutMenu;

    NSMenu *fDockMenu;
    CMenuBar *fDefaultMenuBar;

    BOOL fHandlesDocumentTypes;
    BOOL fHandlesURLTypes;
}

@property (nonatomic, retain) NSMenuItem *fPreferencesMenu;
@property (nonatomic, retain) NSMenuItem *fAboutMenu;

@property (nonatomic, retain) NSMenu *fDockMenu;
@property (nonatomic, retain) CMenuBar *fDefaultMenuBar;

// Returns the shared delegate, creating if necessary
+ (ApplicationDelegate *)sharedDelegate;

// called by the window machinery to setup a default menu bar
- (CMenuBar *)defaultMenuBar;

@end
