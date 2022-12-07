/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import <JavaNativeFoundation/JavaNativeFoundation.h>

extern NSString *const JavaAccessibilityIgnore;

extern NSMutableDictionary *sRoles;
extern void initializeRoles();

extern JNFClassInfo sjc_CAccessibility;
extern JNFClassInfo sjc_AccessibleComponent;
extern JNFClassInfo sjc_AccessibleContext;
extern JNFClassInfo sjc_Accessible;
extern JNFClassInfo sjc_AccessibleRole;
extern JNFClassInfo sjc_Point;
extern JNFClassInfo sjc_AccessibleText;

extern JNFMemberInfo *sjm_getAccessibleRole;
extern JNFMemberInfo *sjf_key;
extern JNFMemberInfo *sjf_X;
extern JNFMemberInfo *sjf_Y;

NSSize getAxComponentSize(JNIEnv *env, jobject axComponent, jobject component);
NSString *getJavaRole(JNIEnv *env, jobject axComponent, jobject component);
jobject getAxSelection(JNIEnv *env, jobject axContext, jobject component);
jobject getAxContextSelection(JNIEnv *env, jobject axContext, jint index, jobject component);
void setAxContextSelection(JNIEnv *env, jobject axContext, jint index, jobject component);
jobject getAxContext(JNIEnv *env, jobject accessible, jobject component);
BOOL isChildSelected(JNIEnv *env, jobject accessible, jint index, jobject component);
jobject getAxStateSet(JNIEnv *env, jobject axContext, jobject component);
BOOL containsAxState(JNIEnv *env, jobject axContext, jobject axState, jobject component);
BOOL isVertical(JNIEnv *env, jobject axContext, jobject component);
BOOL isHorizontal(JNIEnv *env, jobject axContext, jobject component);
BOOL isShowing(JNIEnv *env, jobject axContext, jobject component);
BOOL isSelectable(JNIEnv *env, jobject axContext, jobject component);
NSPoint getAxComponentLocationOnScreen(JNIEnv *env, jobject axComponent, jobject component);
jint getAxTextCharCount(JNIEnv *env, jobject axText, jobject component);

// these methods are copied from the corresponding NSAccessibility methods
id JavaAccessibilityAttributeValue(id element, NSString *attribute);
BOOL JavaAccessibilityIsAttributeSettable(id element, NSString *attribute);
void JavaAccessibilitySetAttributeValue(id element, NSString *attribute, id value);

// these methods are copied from the corresponding NSAccessibilityErrors methods
void JavaAccessibilityRaiseSetAttributeToIllegalTypeException(const char *functionName, id element, NSString *attribute, id value);
void JavaAccessibilityRaiseUnimplementedAttributeException(const char *functionName, id element, NSString *attribute);
void JavaAccessibilityRaiseIllegalParameterTypeException(const char *functionName, id element, NSString *attribute, id parameter);
