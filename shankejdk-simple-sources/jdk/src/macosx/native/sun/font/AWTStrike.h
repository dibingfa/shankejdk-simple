/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <JavaRuntimeSupport/JavaRuntimeSupport.h>

#import "AWTFont.h"

@interface AWTStrike : NSObject {
@public
    AWTFont *                fAWTFont;
    CGFloat                  fSize;
    JRSFontRenderingStyle    fStyle;
    jint                     fAAStyle;

    CGAffineTransform        fTx;
    CGAffineTransform        fDevTx;
    CGAffineTransform        fAltTx; // alternate strike tx used for Sun2D
    CGAffineTransform        fFontTx;
}

+ (AWTStrike *) awtStrikeForFont:(AWTFont *)awtFont tx:(CGAffineTransform)tx invDevTx:(CGAffineTransform)invDevTx style:(JRSFontRenderingStyle)style aaStyle:(jint)aaStyle;

@end
