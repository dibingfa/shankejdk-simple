/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <jni.h>

@interface PrinterView : NSView {
    jobject fPrinterJob; // CPrinterJob
    jobject fCurPageFormat;
    jobject fCurPainter;
    jobject fCurPeekGraphics;

    jint fFirstPage, fLastPage;
}

- (id)initWithFrame:(NSRect)aRect withEnv:(JNIEnv*)env withPrinterJob:(jobject)printerJob;

- (void)setFirstPage:(jint)firstPage lastPage:(jint)lastPage;

- (void)releaseReferences:(JNIEnv*)env;

- (void)drawRect:(NSRect)aRect;

- (NSString*)printJobTitle;
- (BOOL)knowsPageRange:(NSRangePointer)aRange;
- (NSRect)rectForPage:(NSInteger)pageNumber;

- (BOOL)cancelCheck:(JNIEnv*)env;

- (void)complete:(JNIEnv*)env;

@end
