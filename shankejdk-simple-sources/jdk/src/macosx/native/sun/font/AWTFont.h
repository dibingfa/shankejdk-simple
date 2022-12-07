/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <JavaRuntimeSupport/JavaRuntimeSupport.h>

#import "fontscalerdefs.h"

#define MAX_STACK_ALLOC_GLYPH_BUFFER_SIZE 256

@interface AWTFont : NSObject {
@public
    NSFont    *fFont;
    CGFontRef  fNativeCGFont;
    BOOL       fIsFakeItalic;
    TTLayoutTableCache* layoutTableCache;
}

+ (AWTFont *) awtFontForName:(NSString *)name
    style:(int)style isFakeItalic:(BOOL)isFakeItalic;

+ (NSFont *) nsFontForJavaFont:(jobject)javaFont env:(JNIEnv *)env;

@end
