/*
 * Copyright (c) 2005, 2018, Oracle and/or its affiliates. All rights reserved.
 */

#include <stdlib.h>
#include <stdio.h>
#include "gtk_interface.h"
#include "com_sun_java_swing_plaf_gtk_GTKStyle.h"

const char *getStrFor(JNIEnv *env, jstring val);

/*
 * Class:     com_sun_java_swing_plaf_gtk_GTKStyle
 * Method:    nativeGetXThickness
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL
Java_com_sun_java_swing_plaf_gtk_GTKStyle_nativeGetXThickness(
    JNIEnv *env, jclass klass, jint widget_type)
{
    jint ret;
    gtk->gdk_threads_enter();
    ret = gtk->get_xthickness(env, widget_type);
    gtk->gdk_threads_leave();
    return ret;
}

/*
 * Class:     com_sun_java_swing_plaf_gtk_GTKStyle
 * Method:    nativeGetYThickness
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL
Java_com_sun_java_swing_plaf_gtk_GTKStyle_nativeGetYThickness(
    JNIEnv *env, jclass klass, jint widget_type)
{
    jint ret;
    gtk->gdk_threads_enter();
    ret = gtk->get_ythickness(env, widget_type);
    gtk->gdk_threads_leave();
    return ret;
}

/*
 * Class:     com_sun_java_swing_plaf_gtk_GTKStyle
 * Method:    nativeGetColorForState
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL
Java_com_sun_java_swing_plaf_gtk_GTKStyle_nativeGetColorForState(
    JNIEnv *env, jclass klass, jint widget_type,
    jint state_type, jint type_id)
{
    jint ret;
    gtk->gdk_threads_enter();
    ret = gtk->get_color_for_state(env, widget_type, state_type, type_id);
    gtk->gdk_threads_leave();
    return ret;
}

/*
 * Class:     com_sun_java_swing_plaf_gtk_GTKStyle
 * Method:    nativeGetClassValue
 * Signature: (ILjava/lang/String;)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL
Java_com_sun_java_swing_plaf_gtk_GTKStyle_nativeGetClassValue(
    JNIEnv *env, jclass klass, jint widget_type, jstring key)
{
    jobject ret;
    gtk->gdk_threads_enter();
    ret = gtk->get_class_value(env, widget_type, getStrFor(env, key));
    gtk->gdk_threads_leave();
    return ret;
}

/*
 * Class:     com_sun_java_swing_plaf_gtk_GTKStyle
 * Method:    nativeGetPangoFontName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL
Java_com_sun_java_swing_plaf_gtk_GTKStyle_nativeGetPangoFontName(
    JNIEnv *env, jclass klass, jint widget_type)
{
    jstring ret;
    gtk->gdk_threads_enter();
    ret = gtk->get_pango_font_name(env, widget_type);
    gtk->gdk_threads_leave();
    return ret;
}
