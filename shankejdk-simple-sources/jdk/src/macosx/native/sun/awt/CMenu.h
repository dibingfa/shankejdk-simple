/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import "CMenuItem.h"

extern NSString *JAVA_MENU_NAME;

@interface CMenu : CMenuItem {
    NSMenu *fMenu;
}

// Initializers
- (id)initWithPeer:(jobject)peer;

- (void)setNativeMenuTitle_OnAppKitThread:(NSString *)title;
- (void)addNativeItem_OnAppKitThread:(CMenuItem *)itemModified;
- (void)deleteNativeJavaItem_OnAppKitThread:(NSNumber *)number;
- (void)setNativeEnabled_OnAppKitThread:(NSNumber *)boolNumber;

// Actions
- (void)addJavaSubmenu:(CMenu *)submenu;
- (void)addJavaMenuItem:(CMenuItem *)newItem;
- (void)setJavaMenuTitle:(NSString *)title;
- (void)deleteJavaItem:(jint)index;
- (void)addNSMenuItemToMenu:(NSMenu *)inMenu;
- (void)addSeparator;

// Accessors
- (NSMenu *)menu;
@end
