/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>

@interface QueuingApplicationDelegate : NSObject<NSApplicationDelegate>
{
    BOOL fHandlesDocumentTypes;
    BOOL fHandlesURLTypes;

    id <NSApplicationDelegate> realDelegate;

    NSMutableArray* queue;
}

+ (QueuingApplicationDelegate*) sharedDelegate;

- (id) init;
- (void) dealloc;

- (void)processQueuedEventsWithTargetDelegate:(id <NSApplicationDelegate>)delegate;

@property(retain) id <NSApplicationDelegate> realDelegate;

@property(retain) NSMutableArray* queue;

@end

