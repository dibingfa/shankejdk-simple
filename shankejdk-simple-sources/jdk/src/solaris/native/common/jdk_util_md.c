/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
 */

#include <dlfcn.h>
#include "jdk_util.h"

int JDK_InitJvmHandle() {
    /* nop */
    return 1;
}

void* JDK_FindJvmEntry(const char* name) {
    return dlsym(RTLD_DEFAULT, name);
}
