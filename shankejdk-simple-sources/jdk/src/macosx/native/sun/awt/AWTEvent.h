/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef __AWTEVENT_H
#define __AWTEVENT_H

#import "LWCToolkit.h"

jlong UTC(NSEvent *event);
void DeliverJavaKeyEvent(JNIEnv *env, NSEvent *event, jobject peer);
void DeliverJavaMouseEvent(JNIEnv *env, NSEvent *event, jobject peer);
void SendAdditionalJavaEvents(JNIEnv *env, NSEvent *nsEvent, jobject peer);
jint GetJavaMouseModifiers(NSInteger button, NSUInteger modifierFlags);
jint NsKeyModifiersToJavaModifiers(NSUInteger nsFlags, BOOL isExtMods);
NSUInteger JavaModifiersToNsKeyModifiers(jint javaModifiers, BOOL isExtMods);
unichar NsCharToJavaChar(unichar nsChar, NSUInteger modifiers);

#endif /* __AWTEVENT_H */
