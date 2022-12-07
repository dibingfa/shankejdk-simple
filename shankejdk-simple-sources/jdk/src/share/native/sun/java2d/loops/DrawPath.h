/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef DrawPath_h_Included
#define DrawPath_h_Included

typedef struct {
    SurfaceDataRasInfo* pRasInfo;
    jint pixel;
    NativePrimitive* pPrim;
    CompositeInfo* pCompInfo;
} DrawHandlerData;

#define DHND(HND) ((DrawHandlerData*)((HND)->pData))

#endif
