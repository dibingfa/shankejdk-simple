/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef __CGGLYPHIMAGES_H
#define __CGGLYPHIMAGES_H

#import "jni.h"
#import "AWTStrike.h"

void
CGGlyphImages_GetGlyphImagePtrs(jlong glyphInfos[],
                                const AWTStrike *strike,
                                jint rawGlyphCodes[], const CFIndex len);

#endif /* __CGGLYPHIMAGES_H */
