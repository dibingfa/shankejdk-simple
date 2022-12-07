/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <AppKit/AppKit.h>
#import "jni.h"

// This is a class that only has class methods. This is so that the +initialize method is
//  called when the class is first accessed so that sColors is created and filled out
//  lazily.
__attribute__((visibility("default")))
@interface CSystemColors : NSObject {

}

+ (void)reloadColors;

+ (NSColor*)getColor:(NSUInteger)colorIndex useAppleColor:(BOOL)useAppleColor;

@end
