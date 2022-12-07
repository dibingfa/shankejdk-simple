/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni.h>

#include "jni_util.h"
#include "jmm.h"

#ifndef _MANAGEMENT_H_
#define _MANAGEMENT_H_

extern const JmmInterface* jmm_interface;
extern jint jmm_version;
extern void throw_internal_error(JNIEnv* env, const char* msg);

#endif
