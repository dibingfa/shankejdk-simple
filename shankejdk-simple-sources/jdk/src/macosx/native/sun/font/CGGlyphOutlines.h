/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Foundation/Foundation.h>
#import <ApplicationServices/ApplicationServices.h>
#import <AppKit/NSFont.h>
#include <jni.h>

#define kStorageSizeChangeOnGetMoreFactor 2
#define kInitialAllocatedPathSegments 2048

typedef enum  {
    eMoveTo    = 0,
    eLineTo    = 1,
    eQuadTo    = 2,
    eCubicTo   = 3,
    eClosePath = 4
} AWTPathSegmentType;

typedef struct AWTPath {
    CGSize  fTranslate;
    UInt32  fNumberOfSegments;
    jfloat* fSegmentData;
    jbyte*  fSegmentType;
    UInt32  fNumberOfDataElements;
    UInt32  fAllocatedSegmentTypeSpace;
    UInt32  fAllocatedSegmentDataSpace;
} AWTPath, *AWTPathRef;

AWTPathRef AWTPathCreate(CGSize translate);
void AWTPathFree(AWTPathRef pathRef);
OSStatus AWTGetGlyphOutline(CGGlyph *glyphs, NSFont *font,
                            CGSize *advances,
                            CGAffineTransform *inAffineTransform,
                            UInt32 inStartIndex, size_t length,
                            AWTPathRef* outPath);
