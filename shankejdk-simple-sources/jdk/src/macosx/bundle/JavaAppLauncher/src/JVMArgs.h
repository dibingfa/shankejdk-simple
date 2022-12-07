/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>

#import "jni.h"


@interface JVMArgs : NSObject {
@public
    NSBundle *jreBundle;
    char *preferredJVMLib;
    JavaVMInitArgs vm_args;
    BOOL startOnFirstThread;
    BOOL debug;

    NSDictionary *appInfo;
    NSMutableDictionary *jvmInfo;

    NSString *userHome;
    NSString *appPackage;
    NSString *javaRoot;
}

@property (retain, nonatomic) NSBundle *jreBundle;
@property (nonatomic) char *preferredJVMLib;
@property (nonatomic) JavaVMInitArgs vm_args;
@property (nonatomic) BOOL startOnFirstThread;
@property (nonatomic) BOOL debug;

@property (retain, nonatomic) NSDictionary *appInfo;
@property (retain, nonatomic) NSMutableDictionary *jvmInfo;

@property (retain, nonatomic) NSString *userHome;
@property (retain, nonatomic) NSString *appPackage;
@property (retain, nonatomic) NSString *javaRoot;

+ (JVMArgs *)jvmArgsForBundle:(NSBundle *)appBundle argc:(int)argc argv:(char *[])argv;

@end
