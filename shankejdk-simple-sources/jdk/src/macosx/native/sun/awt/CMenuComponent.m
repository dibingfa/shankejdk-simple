/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import "CMenuComponent.h"
#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "ThreadUtilities.h"

@class CMenuItem;

@implementation CMenuComponent

-(id) initWithPeer:(jobject)peer {
    self = [super init];
    if (self) {
        // the peer has been made clobal ref before
        fPeer = peer;
    }
    return self;
}

-(void) dealloc {
    JNIEnv *env = [ThreadUtilities getJNIEnvUncached];
    JNFDeleteGlobalRef(env, fPeer);
    fPeer = NULL;

    [super dealloc];
}
@end
