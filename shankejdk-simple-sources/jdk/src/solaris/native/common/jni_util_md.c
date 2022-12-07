/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#include <string.h>

#include "jni.h"
#include "jni_util.h"
#include "dlfcn.h"

jstring nativeNewStringPlatform(JNIEnv *env, const char *str) {
    return NULL;
}

char* nativeGetStringPlatformChars(JNIEnv *env, jstring jstr, jboolean *isCopy) {
    return NULL;
}

#if defined(LINUX) && (defined(_GNU_SOURCE) || \
         (defined(_POSIX_C_SOURCE) && _POSIX_C_SOURCE < 200112L \
             && defined(_XOPEN_SOURCE) && _XOPEN_SOURCE < 600))
extern int __xpg_strerror_r(int, char *, size_t);
#define strerror_r(a, b, c) __xpg_strerror_r((a), (b), (c))
#endif

void* getProcessHandle() {
    static void *procHandle = NULL;
    if (procHandle != NULL) {
        return procHandle;
    }
#ifdef __APPLE__
    procHandle = (void*)dlopen(NULL, RTLD_FIRST);
#else
    procHandle = (void*)dlopen(NULL, RTLD_LAZY);
#endif
    return procHandle;
}

void buildJniFunctionName(const char *sym, const char *cname,
                          char *jniEntryName) {
    strcpy(jniEntryName, sym);
    if (cname != NULL) {
        strcat(jniEntryName, "_");
        strcat(jniEntryName, cname);
    }
}

int
getErrorString(int err, char *buf, size_t len)
{
    if (err == 0 || len < 1) return 0;
    return strerror_r(err, buf, len);
}
