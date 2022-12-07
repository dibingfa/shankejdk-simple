/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import "JavaAccessibilityAction.h"
#import "JavaAccessibilityUtilities.h"

#import "ThreadUtilities.h"


@implementation JavaAxAction

- (id)initWithEnv:(JNIEnv *)env withAccessibleAction:(jobject)accessibleAction withIndex:(jint)index withComponent:(jobject)component
{
    self = [super init];
    if (self) {
        fAccessibleAction = JNFNewWeakGlobalRef(env, accessibleAction);
        fIndex = index;
        fComponent = JNFNewWeakGlobalRef(env, component);
    }
    return self;
}

- (void)dealloc
{
    JNIEnv *env = [ThreadUtilities getJNIEnvUncached];

    JNFDeleteWeakGlobalRef(env, fAccessibleAction);
    fAccessibleAction = NULL;

    JNFDeleteWeakGlobalRef(env, fComponent);
    fComponent = NULL;

    [super dealloc];
}


- (NSString *)getDescription
{
    static JNF_STATIC_MEMBER_CACHE(jm_getAccessibleActionDescription, sjc_CAccessibility, "getAccessibleActionDescription", "(Ljavax/accessibility/AccessibleAction;ILjava/awt/Component;)Ljava/lang/String;");

    JNIEnv* env = [ThreadUtilities getJNIEnv];

    jobject fCompLocal = (*env)->NewLocalRef(env, fComponent);
    if ((*env)->IsSameObject(env, fCompLocal, NULL)) {
        return nil;
    }
    NSString *str = nil;
    jstring jstr = JNFCallStaticObjectMethod( env,
                                              jm_getAccessibleActionDescription,
                                              fAccessibleAction,
                                              fIndex,
                                              fCompLocal );
    if (jstr != NULL) {
        str = JNFJavaToNSString(env, jstr); // AWT_THREADING Safe (AWTRunLoopMode)
        (*env)->DeleteLocalRef(env, jstr);
    }
    (*env)->DeleteLocalRef(env, fCompLocal);
    return str;
}

- (void)perform
{
    static JNF_STATIC_MEMBER_CACHE(jm_doAccessibleAction, sjc_CAccessibility, "doAccessibleAction", "(Ljavax/accessibility/AccessibleAction;ILjava/awt/Component;)V");

    JNIEnv* env = [ThreadUtilities getJNIEnv];

    JNFCallStaticVoidMethod(env, jm_doAccessibleAction, fAccessibleAction, fIndex, fComponent); // AWT_THREADING Safe (AWTRunLoopMode)
}

@end


@implementation TabGroupAction

- (id)initWithEnv:(JNIEnv *)env withTabGroup:(jobject)tabGroup withIndex:(jint)index withComponent:(jobject)component
{
    self = [super init];
    if (self) {
        fTabGroup = JNFNewWeakGlobalRef(env, tabGroup);
        fIndex = index;
        fComponent = JNFNewWeakGlobalRef(env, component);
    }
    return self;
}

- (void)dealloc
{
    JNIEnv *env = [ThreadUtilities getJNIEnvUncached];

    JNFDeleteWeakGlobalRef(env, fTabGroup);
    fTabGroup = NULL;

    JNFDeleteWeakGlobalRef(env, fComponent);
    fComponent = NULL;

    [super dealloc];
}

- (NSString *)getDescription
{
    return @"click";
}

- (void)perform
{
    JNIEnv* env = [ThreadUtilities getJNIEnv];

    setAxContextSelection(env, fTabGroup, fIndex, fComponent);
}

@end
