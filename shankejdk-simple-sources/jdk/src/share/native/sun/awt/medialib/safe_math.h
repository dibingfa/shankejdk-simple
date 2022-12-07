/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef __SAFE_MATH_H__
#define __SAFE_MATH_H__

#include "mlib_types.h"

#define SAFE_TO_MULT(a, b) \
    (((a) > 0) && ((b) >= 0) && ((0x7fffffff / (a)) > (b)))

#define SAFE_TO_ADD(a, b) \
    (((a) >= 0) && ((b) >= 0) && ((0x7fffffff - (a)) > (b)))

#define IS_FINITE(a) \
    (((a) >= MLIB_D64_MIN) && ((a) <= MLIB_D64_MAX))

#endif // __SAFE_MATH_H__
