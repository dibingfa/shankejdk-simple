/*
 * Copyright (c) 2014, 2021, Oracle and/or its affiliates. All rights reserved.
 */

#include <string.h>
#include <stdlib.h>

#include "jni.h"
#include "jni_util.h"
#include "jlong.h"
#include "jvm.h"
#include "jdk_util.h"

/* defined in libverify.so/verify.dll (src file common/check_format.c) */
extern jboolean VerifyClassname(char *utf_name, jboolean arrayAllowed);
extern jboolean VerifyFixClassname(char *utf_name);

#include "sun_misc_URLClassPath.h"

extern char*
getUTF(JNIEnv *env, jstring str, char* localBuf, int bufSize);


JNIEXPORT jboolean JNICALL
Java_sun_misc_URLClassPath_knownToNotExist0(JNIEnv *env, jclass cls, jobject loader,
                                            jstring classname)
{
    char *clname;
    jboolean result = JNI_FALSE;
    char buf[128];

    if (classname == NULL) {
        JNU_ThrowNullPointerException(env, NULL);
        return result;
    }

    clname = getUTF(env, classname, buf, sizeof(buf));
    if (clname == NULL) {
        // getUTF() throws OOME before returning NULL, no need to throw OOME here
        return result;
    }
    (void)VerifyFixClassname(clname);

    if (!VerifyClassname(clname, JNI_TRUE)) {  /* expects slashed name */
        goto done;
    }

    result = JVM_KnownToNotExist(env, loader, clname);

 done:
    if (clname != buf) {
        free(clname);
    }

    return result;
}

JNIEXPORT jobjectArray JNICALL
Java_sun_misc_URLClassPath_getLookupCacheURLs(JNIEnv *env, jclass cls, jobject loader)
{
    return JVM_GetResourceLookupCacheURLs(env, loader);
}


JNIEXPORT jintArray JNICALL
Java_sun_misc_URLClassPath_getLookupCacheForClassLoader(JNIEnv *env, jclass cls,
                                                        jobject loader,
                                                        jstring resource_name)
{
    char *resname;
    jintArray result = NULL;
    char buf[128];

    if (resource_name == NULL) {
        JNU_ThrowNullPointerException(env, NULL);
        return result;
    }

    resname = getUTF(env, resource_name, buf, sizeof(buf));
    if (resname == NULL) {
        // getUTF() throws OOME before returning NULL, no need to throw OOME here
        return result;
    }
    result = JVM_GetResourceLookupCache(env, loader, resname);

 done:
    if (resname != buf) {
        free(resname);
    }

    return result;
}

