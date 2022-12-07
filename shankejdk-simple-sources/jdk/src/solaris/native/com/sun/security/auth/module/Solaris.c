/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni.h>
#include "com_sun_security_auth_module_SolarisSystem.h"
#include <stdio.h>
#include <pwd.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <pwd.h>

static void throwIllegalArgumentException(JNIEnv *env, const char *msg) {
    jclass clazz = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
    if (clazz != NULL)
        (*env)->ThrowNew(env, clazz, msg);
}

JNIEXPORT void JNICALL
Java_com_sun_security_auth_module_SolarisSystem_getSolarisInfo
                                                (JNIEnv *env, jobject obj) {

    int i;
    char pwd_buf[1024];
    struct passwd pwd;
    jsize numSuppGroups = getgroups(0, NULL);
    jfieldID fid;
    jstring jstr;
    jlongArray jgroups;
    jlong *jgroupsAsArray;
    gid_t *groups;
    jclass cls;

    groups = (gid_t *)calloc(numSuppGroups, sizeof(gid_t));

    if (groups == NULL) {
        jclass cls = (*env)->FindClass(env,"java/lang/OutOfMemoryError");
        if (cls != NULL)
            (*env)->ThrowNew(env, cls, NULL);
        return;
    }

    cls = (*env)->GetObjectClass(env, obj);

    memset(pwd_buf, 0, sizeof(pwd_buf));
    if (getpwuid_r(getuid(), &pwd, pwd_buf, sizeof(pwd_buf)) != NULL &&
        getgroups(numSuppGroups, groups) != -1) {

        /*
         * set username
         */
        fid = (*env)->GetFieldID(env, cls, "username", "Ljava/lang/String;");
        if (fid == 0) {
            (*env)->ExceptionClear(env);
            throwIllegalArgumentException(env, "invalid field: username");
            return;
        }
        jstr = (*env)->NewStringUTF(env, pwd.pw_name);
        if (jstr == NULL)
            return;
        (*env)->SetObjectField(env, obj, fid, jstr);

        /*
         * set uid
         */
        fid = (*env)->GetFieldID(env, cls, "uid", "J");
        if (fid == 0) {
            (*env)->ExceptionClear(env);
            throwIllegalArgumentException(env, "invalid field: uid");
            return;
        }
        (*env)->SetLongField(env, obj, fid, pwd.pw_uid);

        /*
         * set gid
         */
        fid = (*env)->GetFieldID(env, cls, "gid", "J");
        if (fid == 0) {
            (*env)->ExceptionClear(env);
            throwIllegalArgumentException(env, "invalid field: gid");
            return;
        }
        (*env)->SetLongField(env, obj, fid, pwd.pw_gid);

        /*
         * set supplementary groups
         */
        fid = (*env)->GetFieldID(env, cls, "groups", "[J");
        if (fid == 0) {
            (*env)->ExceptionClear(env);
            throwIllegalArgumentException(env, "invalid field: groups");
            return;
        }

        jgroups = (*env)->NewLongArray(env, numSuppGroups);
        if (jgroups == NULL)
            return;
        jgroupsAsArray = (*env)->GetLongArrayElements(env, jgroups, 0);
        if (jgroupsAsArray == NULL)
            return;
        for (i = 0; i < numSuppGroups; i++)
            jgroupsAsArray[i] = groups[i];
        (*env)->ReleaseLongArrayElements(env, jgroups, jgroupsAsArray, 0);
        (*env)->SetObjectField(env, obj, fid, jgroups);
    }
    return;
}
