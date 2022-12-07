/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
 */
#ifndef _COLOR_H_
#define _COLOR_H_

#include "awt.h"
#include "colordata.h"

#if !defined(HEADLESS) && !defined(MACOSX)
typedef struct {
    unsigned int Depth;
    XPixmapFormatValues wsImageFormat;
    ImgColorData clrdata;
    ImgConvertFcn *convert[NUM_IMGCV];
} awtImageData;
#endif /* !HEADLESS && !MACOSX */

#endif           /* _COLOR_H_ */
