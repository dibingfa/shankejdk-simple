/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import "PropertiesUtilities.h"

@implementation PropertiesUtilities

+ (NSString *) javaSystemPropertyForKey:(NSString *)key withEnv:(JNIEnv *)env {
    static JNF_CLASS_CACHE(jc_System, "java/lang/System");
    static JNF_STATIC_MEMBER_CACHE(jm_getProperty, jc_System, "getProperty", "(Ljava/lang/String;)Ljava/lang/String;");

    jstring jKey = JNFNSToJavaString(env, key);
    jstring jValue = JNFCallStaticObjectMethod(env, jm_getProperty, jKey);
    (*env)->DeleteLocalRef(env, jKey);

    NSString *value = JNFJavaToNSString(env, jValue);
    (*env)->DeleteLocalRef(env, jValue);
    return value;
}

@end
