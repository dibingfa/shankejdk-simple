/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import "com_apple_laf_ScreenPopupFactory.h"
#import <JavaNativeFoundation/JavaNativeFoundation.h>

static JNF_CLASS_CACHE(sjc_PopupFactory, "javax/swing/PopupFactory");
static JNF_MEMBER_CACHE(jm_getPopup, sjc_PopupFactory, "getPopup", "(Ljava/awt/Component;Ljava/awt/Component;III)Ljavax/swing/Popup;");

/*
 * Class:     com_apple_laf_ScreenPopupFactory
 * Method:    _getHeavyWeightPopup
 * Signature: (Ljava/awt/Component;Ljava/awt/Component;II)Ljavax/swing/Popup;
 */
JNIEXPORT jobject /* javax.swing.Popup */ JNICALL Java_com_apple_laf_ScreenPopupFactory__1getHeavyWeightPopup
(JNIEnv *env, jobject screenPopupFactory, jobject comp, jobject invoker, jint x, jint y) {
    jobject popup;
JNF_COCOA_ENTER(env);
    popup = JNFCallObjectMethod(env, screenPopupFactory, jm_getPopup, comp, invoker, x, y, 2);
JNF_COCOA_EXIT(env);
    return popup;
}
