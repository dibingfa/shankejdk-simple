/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef OGLFuncs_md_h_Included
#define OGLFuncs_md_h_Included

#include <dlfcn.h>
#include "J2D_GL/cglext.h"
#include "OGLFuncMacros.h"

#define OGL_LIB_HANDLE pLibGL
#define OGL_DECLARE_LIB_HANDLE() \
    static void *OGL_LIB_HANDLE = NULL
#define OGL_LIB_IS_UNINITIALIZED() \
    (OGL_LIB_HANDLE == NULL)
#define OGL_OPEN_LIB() \
    OGL_LIB_HANDLE = dlopen("/System/Library/Frameworks/OpenGL.framework/Versions/Current/Libraries/libGL.dylib", RTLD_LAZY | RTLD_GLOBAL)
#define OGL_CLOSE_LIB() \
    dlclose(OGL_LIB_HANDLE)
#define OGL_GET_PROC_ADDRESS(f) \
    dlsym(OGL_LIB_HANDLE, #f)
#define OGL_GET_EXT_PROC_ADDRESS(f) \
    OGL_GET_PROC_ADDRESS(f)

#define OGL_EXPRESS_PLATFORM_FUNCS(action)
#define OGL_EXPRESS_PLATFORM_EXT_FUNCS(action)

#endif /* OGLFuncs_md_h_Included */
