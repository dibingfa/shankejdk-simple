/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <JavaNativeFoundation/JavaNativeFoundation.h>


@interface AppleScriptExecutionContext : NSObject {
    NSString *source;
    BOOL isFile;
    NSDictionary *context;
    NSDictionary *error;
    id returnValue;
}

@property (nonatomic, retain) NSString *source;
@property (nonatomic, retain) NSDictionary *context;
@property (nonatomic, retain) NSDictionary *error;
@property (nonatomic, retain) id returnValue;

- (id) initWithSource:(NSString *)source context:(NSDictionary *)context;
- (id) initWithFile:(NSString *)filename context:(NSDictionary *)context;
- (id) invokeWithEnv:(JNIEnv *)env;

@end
