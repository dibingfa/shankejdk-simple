/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#include "com_apple_jobjc_CIF.h"

#define MACOSX
#include <ffi/ffi.h>
#include <JavaNativeFoundation/JavaNativeFoundation.h>

#include "NativeBuffer.h"

JNIEXPORT jint JNICALL Java_com_apple_jobjc_CIF_getSizeofCIF
(JNIEnv *env, jclass clazz)
{
    return (jint) sizeof(ffi_cif);
}

JNIEXPORT jboolean JNICALL Java_com_apple_jobjc_CIF_prepCIF
(JNIEnv *env, jclass clazz, jlong jCIFPtr, jint jNargs, jlong jRetTypePtr, jlong jArgsPtr)
{
    ffi_cif *cif = jlong_to_ptr(jCIFPtr);
    unsigned int nargs = jNargs;
    ffi_type *rtype = jlong_to_ptr(jRetTypePtr);
    ffi_type **atypes = jlong_to_ptr(jArgsPtr);

//    NSLog(@"rtype->(size: %d, alignment: %d, type: %d)", rtype->size, rtype->alignment, rtype->type);
    return (jboolean) (FFI_OK == ffi_prep_cif(cif, FFI_DEFAULT_ABI, nargs, rtype, atypes));
}
