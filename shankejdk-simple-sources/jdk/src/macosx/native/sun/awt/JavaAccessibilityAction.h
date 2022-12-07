/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <AppKit/AppKit.h>
#import <jni.h>


@protocol JavaAccessibilityAction

- (NSString *)getDescription;
- (void)perform;

@end


@interface JavaAxAction : NSObject <JavaAccessibilityAction> {
    jobject fAccessibleAction;
    jint fIndex;
    jobject fComponent;
}

- (id)initWithEnv:(JNIEnv *)env withAccessibleAction:(jobject)accessibleAction withIndex:(jint)index withComponent:(jobject)component;

- (NSString *)getDescription;
- (void)perform;

@end


@interface TabGroupAction : NSObject <JavaAccessibilityAction> {
    jobject fTabGroup;
    jint fIndex;
    jobject fComponent;
}

- (id)initWithEnv:(JNIEnv *)env withTabGroup:(jobject)tabGroup withIndex:(jint)index withComponent:(jobject)component;

- (NSString *)getDescription;
- (void)perform;

@end
