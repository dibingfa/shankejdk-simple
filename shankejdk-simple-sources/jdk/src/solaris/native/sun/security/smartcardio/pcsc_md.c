/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include <dlfcn.h>

#include <winscard.h>

#include "sun_security_smartcardio_PlatformPCSC.h"

#include "pcsc_md.h"

void *hModule;
FPTR_SCardEstablishContext scardEstablishContext;
FPTR_SCardConnect scardConnect;
FPTR_SCardDisconnect scardDisconnect;
FPTR_SCardStatus scardStatus;
FPTR_SCardGetStatusChange scardGetStatusChange;
FPTR_SCardTransmit scardTransmit;
FPTR_SCardListReaders scardListReaders;
FPTR_SCardBeginTransaction scardBeginTransaction;
FPTR_SCardEndTransaction scardEndTransaction;
FPTR_SCardControl scardControl;

/*
 * Throws a Java Exception by name
 */
void throwByName(JNIEnv *env, const char *name, const char *msg)
{
    jclass cls = (*env)->FindClass(env, name);

    if (cls != 0) /* Otherwise an exception has already been thrown */
        (*env)->ThrowNew(env, cls, msg);
}

/*
 * Throws java.lang.NullPointerException
 */
void throwNullPointerException(JNIEnv *env, const char *msg)
{
    throwByName(env, "java/lang/NullPointerException", msg);
}

/*
 * Throws java.io.IOException
 */
void throwIOException(JNIEnv *env, const char *msg)
{
    throwByName(env, "java/io/IOException", msg);
}

void *findFunction(JNIEnv *env, void *hModule, char *functionName) {
    void *fAddress = dlsym(hModule, functionName);
    if (fAddress == NULL) {
        char errorMessage[256];
        snprintf(errorMessage, sizeof(errorMessage), "Symbol not found: %s", functionName);
        throwNullPointerException(env, errorMessage);
        return NULL;
    }
    return fAddress;
}

JNIEXPORT void JNICALL Java_sun_security_smartcardio_PlatformPCSC_initialize
        (JNIEnv *env, jclass thisClass, jstring jLibName) {
    const char *libName = (*env)->GetStringUTFChars(env, jLibName, NULL);
    if (libName == NULL) {
        throwNullPointerException(env, "PCSC library name is null");
        return;
    }
    hModule = dlopen(libName, RTLD_LAZY);
    (*env)->ReleaseStringUTFChars(env, jLibName, libName);

    if (hModule == NULL) {
        throwIOException(env, dlerror());
        return;
    }
    scardEstablishContext = (FPTR_SCardEstablishContext)findFunction(env, hModule, "SCardEstablishContext");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardConnect          = (FPTR_SCardConnect)         findFunction(env, hModule, "SCardConnect");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardDisconnect       = (FPTR_SCardDisconnect)      findFunction(env, hModule, "SCardDisconnect");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardStatus           = (FPTR_SCardStatus)          findFunction(env, hModule, "SCardStatus");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardGetStatusChange  = (FPTR_SCardGetStatusChange) findFunction(env, hModule, "SCardGetStatusChange");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardTransmit         = (FPTR_SCardTransmit)        findFunction(env, hModule, "SCardTransmit");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardListReaders      = (FPTR_SCardListReaders)     findFunction(env, hModule, "SCardListReaders");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardBeginTransaction = (FPTR_SCardBeginTransaction)findFunction(env, hModule, "SCardBeginTransaction");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
    scardEndTransaction   = (FPTR_SCardEndTransaction)  findFunction(env, hModule, "SCardEndTransaction");
    if ((*env)->ExceptionCheck(env)) {
         return;
    }
#ifndef __APPLE__
    scardControl          = (FPTR_SCardControl)         findFunction(env, hModule, "SCardControl");
#else
    scardControl          = (FPTR_SCardControl)         findFunction(env, hModule, "SCardControl132");
#endif // __APPLE__
}
