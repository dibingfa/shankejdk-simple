/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef JAVA_MD_MACOSX_H
#define JAVA_MD_MACOSX_H

/* CounterGet() is implemented in java_md.c */
int64_t CounterGet(void);
#define Counter2Micros(counts)    (counts)

/* pointer to environment */
#include <crt_externs.h>
#define environ (*_NSGetEnviron())

/*
 *      A collection of useful strings. One should think of these as #define
 *      entries, but actual strings can be more efficient (with many compilers).
 */
static const char *system_dir  = PACKAGE_PATH "/openjdk7";
static const char *user_dir    = "/java";

#include <dlfcn.h>
#include <pthread.h>

#endif /* JAVA_MD_MACOSX_H */
