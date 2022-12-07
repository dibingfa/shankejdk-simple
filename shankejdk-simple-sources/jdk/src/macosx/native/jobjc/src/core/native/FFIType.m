/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
#include <JavaNativeFoundation/JavaNativeFoundation.h>
#include <ffi/ffi.h>

#include "com_apple_jobjc_FFIType.h"

JNIEXPORT void JNICALL Java_com_apple_jobjc_FFIType_makeFFIType
(JNIEnv *env, jclass clazz, jlong ffi_type_jlong, jlong ffi_type_elements_jlong)
{
    ffi_type *type = jlong_to_ptr(ffi_type_jlong);
    type->elements = jlong_to_ptr(ffi_type_elements_jlong);
    type->type = FFI_TYPE_STRUCT;
    type->size = type->alignment = 0;
}

JNIEXPORT jint JNICALL Java_com_apple_jobjc_FFIType_getFFITypeSizeof
(JNIEnv *env, jclass clazz)
{
    return (jint) sizeof(ffi_type);
}
