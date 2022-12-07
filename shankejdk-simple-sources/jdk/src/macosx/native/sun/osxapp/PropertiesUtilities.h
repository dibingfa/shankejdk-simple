/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>


@interface PropertiesUtilities : NSObject

+ (NSString *) javaSystemPropertyForKey:(NSString *)key withEnv:(JNIEnv *)env;

@end
