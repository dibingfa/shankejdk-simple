/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import "QuartzSurfaceData.h"
#import <pthread.h>

typedef UInt8 Pixel8bit;
typedef UInt16 Pixel16bit;
typedef UInt32 Pixel32bit;

typedef struct _ImageSDOps ImageSDOps;

ImageSDOps*    LockImage(JNIEnv* env, jobject imageSurfaceData);
void        UnlockImage(JNIEnv* env, ImageSDOps* isdo);
ImageSDOps*    LockImagePixels(JNIEnv* env, jobject imageSurfaceData);
void        UnlockImagePixels(JNIEnv* env, ImageSDOps* isdo);

// if there is no image created for isdo.imgRef, it creates and image using the isdo.dataProvider
// If there is an image present, this is a no-op
void makeSureImageIsCreated(ImageSDOps* isdo);

typedef struct _ContextInfo
{
    BOOL                useWindowContextReference;
    BOOL                canUseJavaPixelsAsContext;
    size_t                bitsPerComponent;
    size_t                bytesPerPixel;
    size_t                bytesPerRow;
    CGImageAlphaInfo    alphaInfo;
    CGColorSpaceRef        colorSpace;
} ContextInfo;

typedef struct _ImageInfo
{
    size_t                bitsPerComponent;
    size_t                bitsPerPixel;
    size_t                bytesPerPixel;
    size_t                bytesPerRow;
    CGImageAlphaInfo    alphaInfo;
    CGColorSpaceRef        colorSpace;
} ImageInfo;

struct _ImageSDOps
{
    QuartzSDOps                qsdo; // must be the first entry!

    ContextInfo                contextInfo;
    ImageInfo                imageInfo;
    BOOL                    isSubImage;

    jint*                    javaImageInfo;

    // parameters specifying this BufferedImage given to us from Java
    jobject                    array;
    jint                    offset;
    jint                    width;
    jint                    height;
    jint                    javaPixelBytes;
    jint                    javaPixelsBytesPerRow;
    jobject                    icm;
    jint                    type;

    Pixel8bit*                pixels;
    Pixel8bit*                pixelsLocked;

    // needed by TYPE_BYTE_INDEXED
    UInt16*                    indexedColorTable;
    UInt32*                    lutData;
    UInt32                    lutDataSize;

    // Used as a cached image ref created from the isdo.dataprovider. This is only a chached image, and it might become invalid
    // if somebody draws on the bitmap context, or the pixels are changed in java. In that case, we need to NULL out
    // this image and recreate it from the data provider.
    CGImageRef                imgRef;

    // Cached instance of CGDataProvider. dataProvider is alloced the first time a bitmap context is created, providing the
    // native pixels as a source of the data. The dataProviders life cycle is the same as ISDO. The reference gets
    // released when we are done with the ISDO.
    CGDataProviderRef        dataProvider;

    // Pointer in memory that is used for create the CGBitmapContext and the CGDataProvider (used for imgRef). This is a native
    // copy of the pixels for the Image. There is a spearate copy of the pixels that lives in Java heap. There are two main
    // reasons why we keep those pixels spearate: 1) CG doesn't support all the Java pixel formats 2) The Garbage collector can
    // move the java pixels at any time. There are possible workarounds for both problems. Number 2) seems to be a more serious issue, since
    // we can solve 1) by only supporting certain image types.
    void *                    nativePixels;
    NSGraphicsContext*        nsRef;

    pthread_mutex_t            lock;
    jint                    nrOfPixelsOwners;
};

