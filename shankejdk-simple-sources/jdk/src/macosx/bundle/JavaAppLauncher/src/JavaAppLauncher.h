/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>

#import "jni.h"

#import "JVMArgs.h"


@interface JavaAppLauncher : NSObject {
    JVMArgs *args;
    JavaVM *jvm;
}

@property (retain) JVMArgs *args;

- (void) findAndLoadJVM;
- (void) invokeBundledAppJavaLauncherWithEnv:(JNIEnv *)env;

@end
