/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
 */

/***
 *** THIS IMPLEMENTS ONLY THE OBSOLETE java.awt.Event CLASS! SEE
 *** awt_AWTEvent.[ch] FOR THE NEWER EVENT CLASSES.
 ***
 ***/
#ifdef HEADLESS
    #error This file should not be included in headless library
#endif

#include "java_awt_Event.h"
#include "jni_util.h"

#include "awt_Event.h"

struct EventIDs eventIDs;

JNIEXPORT void JNICALL
Java_java_awt_Event_initIDs(JNIEnv *env, jclass cls)
{
    CHECK_NULL(eventIDs.data = (*env)->GetFieldID(env, cls, "data", "J"));
    CHECK_NULL(eventIDs.consumed = (*env)->GetFieldID(env, cls, "consumed", "Z"));
    CHECK_NULL(eventIDs.id = (*env)->GetFieldID(env, cls, "id", "I"));
}
