/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#import <AppKit/AppKit.h>
#import "jni.h"

@interface CClipboard : NSObject {
    jobject fClipboardOwner;

    // Track pasteboard changes. Initialized once at the start, and then updated
    // on an application resume event.  If it's different than the last time we claimed
    // the clipboard that means we lost the clipboard to someone else.
    NSInteger fChangeCount;
}

+ (CClipboard *) sharedClipboard;

- (void) javaDeclareTypes:(NSArray *)inTypes withOwner:(jobject)inClipboard jniEnv:(JNIEnv *)inEnv;
- (void) javaSetData:(NSData *)inData forType:(NSString *) inFormat;

- (NSArray *) javaGetTypes;
- (NSData *) javaGetDataForType:(NSString *)inFormat;

@end
