/*
 * Copyright (c) 2001, 2005, Oracle and/or its affiliates. All rights reserved.
 */

#include "SurfaceData.h"
#include "colordata.h"


typedef struct _BufImgSDOps {
    SurfaceDataOps      sdOps;
    jobject             array;
    jint                offset;
    jint                bitoffset;
    jint                pixStr;
    jint                scanStr;
    jobject             icm;
    jobject             lutarray;
    jint                lutsize;
    SurfaceDataBounds   rasbounds;
} BufImgSDOps;

typedef struct _BufImgRIPrivate {
    jint                lockFlags;
    void                *base;
    void                *lutbase;
    ColorData           *cData;
} BufImgRIPrivate;
