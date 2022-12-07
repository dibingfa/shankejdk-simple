/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef CGLSurfaceData_h_Included
#define CGLSurfaceData_h_Included

#import "OGLSurfaceData.h"
#import "CGLGraphicsConfig.h"
#import "AWTWindow.h"
#import "CGLLayer.h"

/**
 * The CGLSDOps structure contains the CGL-specific information for a given
 * OGLSurfaceData.  It is referenced by the native OGLSDOps structure.
 */
typedef struct _CGLSDOps {
    AWTView               *peerData;
    CGLLayer              *layer;
    GLclampf              argb[4]; // background clear color
    CGLGraphicsConfigInfo *configInfo;
} CGLSDOps;

#endif /* CGLSurfaceData_h_Included */
