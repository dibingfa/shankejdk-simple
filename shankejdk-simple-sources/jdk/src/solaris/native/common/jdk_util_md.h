/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef JDK_UTIL_MD_H
#define JDK_UTIL_MD_H

// checking for nanness
#ifdef __solaris__
#include <ieeefp.h>
#define ISNANF(f) isnanf(f)
#define ISNAND(d) isnand(d)
#elif defined(MACOSX)
#include <math.h>
#define ISNANF(f) isnan(f)
#define ISNAND(d) isnan(d)
#elif defined(__linux__) || defined(_ALLBSD_SOURCE)
#include <math.h>
#define ISNANF(f) isnanf(f)
#define ISNAND(d) isnan(d)
#elif defined(_AIX)
#include <math.h>
#define ISNANF(f) _isnanf(f)
#define ISNAND(d) _isnan(d)
#else
#error "missing platform-specific definition here"
#endif

#endif /* JDK_UTIL_MD_H */
