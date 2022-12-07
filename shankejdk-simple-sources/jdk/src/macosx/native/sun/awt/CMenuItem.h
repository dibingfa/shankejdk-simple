/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import "CMenuComponent.h"

@interface CMenuItem : CMenuComponent {
    NSMenuItem *fMenuItem;
    BOOL fIsCheckbox;
    BOOL fIsEnabled;
}

// Setup
- (id) initWithPeer:(jobject)peer asSeparator: (BOOL) asSeparator;
- (void) setIsCheckbox;

// Events
- (void) handleAction:(NSMenuItem *) sender;

- (void) setJavaLabel:(NSString *)theLabel
             shortcut:(NSString *)theKeyEquivalent
         modifierMask:(jint)modifiers;

- (void) setJavaImage:(NSImage *)theImage;
- (void) setJavaToolTipText:(NSString *)theText;
- (void) setJavaEnabled:(BOOL) enabled;
- (void) setJavaState:(BOOL)newState;
- (void) addNSMenuItemToMenu:(NSMenu *)inMenu;
- (NSMenuItem *)menuItem;
- (BOOL) isEnabled;
@end
