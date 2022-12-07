/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#import <AppKit/AppKit.h>
#import <jni.h>

@interface CMenuComponent : NSObject {

@public
    jobject fPeer;
}

// Setup
- (id) initWithPeer:(jobject)peer;
- (void) disposer;
@end
