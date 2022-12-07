/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "LWCToolkit.h"
#include "GeomUtilities.h"

#include "sun_awt_CGraphicsConfig.h"


/*
 * Class:     sun_awt_CGraphicsConfig
 * Method:    nativeGetBounds
 * Signature: (I)Ljava/awt/Rectangle;
 */
JNIEXPORT jobject JNICALL Java_sun_awt_CGraphicsConfig_nativeGetBounds
(JNIEnv *env, jclass class, jint displayID)
{
    jobject jrect = NULL;

JNF_COCOA_ENTER(env);

    CGRect rect = CGDisplayBounds((CGDirectDisplayID)displayID);
    jrect = CGToJavaRect(env, rect);

JNF_COCOA_EXIT(env);

    return jrect;
}
