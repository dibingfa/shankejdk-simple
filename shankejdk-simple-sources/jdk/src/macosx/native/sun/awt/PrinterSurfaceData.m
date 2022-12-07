/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */


#import "PrinterSurfaceData.h"
#import <JavaNativeFoundation/JavaNativeFoundation.h>


//#define DEBUG 1
#if defined DEBUG
    #define PRINT(msg) {fprintf(stderr, "%s\n", msg);}
#else
    #define PRINT(msg) {}
#endif

static LockFunc PrintSD_Lock;
static UnlockFunc PrintSD_Unlock;
static GetRasInfoFunc PrintSD_GetRasInfo;
static ReleaseFunc PrintSD_ReleaseRasInfo;
static void flush(JNIEnv *env, QuartzSDOps *qsdo);

static void PrintSD_startCGContext(JNIEnv *env, QuartzSDOps *qsdo, SDRenderType renderType)
{
PRINT(" PrintSD_startCGContext")

    if (qsdo->cgRef != NULL)
    {
        flush(env, qsdo);

        SetUpCGContext(env, qsdo, renderType);
    }
}

static void PrintSD_finishCGContext(JNIEnv *env, QuartzSDOps *qsdo)
{
PRINT("    PrintSD_finishCGContext")

    if (qsdo->cgRef != NULL)
    {
        CompleteCGContext(env, qsdo);
    }
}

static void PrintSD_dispose(JNIEnv *env, SurfaceDataOps *sdo)
{
PRINT(" PrintSD_dispose")
    QuartzSDOps *qsdo = (QuartzSDOps *)sdo;

    (*env)->DeleteGlobalRef(env, qsdo->javaGraphicsStatesObjects);

    if (qsdo->graphicsStateInfo.batchedLines != NULL)
    {
        free(qsdo->graphicsStateInfo.batchedLines);
        qsdo->graphicsStateInfo.batchedLines = NULL;
    }

    qsdo->BeginSurface            = NULL;
    qsdo->FinishSurface            = NULL;
}

JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CPrinterSurfaceData_initOps(JNIEnv *env, jobject jthis, jlong nsRef, jobject jGraphicsState, jobjectArray jGraphicsStateObject, jint width, jint height)
{
JNF_COCOA_ENTER(env);

PRINT("Java_sun_lwawt_macosx_CPrinterSurfaceData_initOps")

    PrintSDOps *psdo = (PrintSDOps*)SurfaceData_InitOps(env, jthis, sizeof(PrintSDOps));
    psdo->nsRef            = (NSGraphicsContext*)jlong_to_ptr(nsRef);
    psdo->width            = width;
    psdo->height        = height;

    QuartzSDOps *qsdo = (QuartzSDOps*)psdo;
    qsdo->BeginSurface            = PrintSD_startCGContext;
    qsdo->FinishSurface            = PrintSD_finishCGContext;
    qsdo->cgRef                    = [psdo->nsRef graphicsPort];

    qsdo->javaGraphicsStates            = (jint*)((*env)->GetDirectBufferAddress(env, jGraphicsState));
    qsdo->javaGraphicsStatesObjects        = (*env)->NewGlobalRef(env, jGraphicsStateObject);

    qsdo->graphicsStateInfo.batchedLines        = NULL;
    qsdo->graphicsStateInfo.batchedLinesCount    = 0;

    SurfaceDataOps *sdo = (SurfaceDataOps*)qsdo;
    sdo->Lock        = PrintSD_Lock;
    sdo->Unlock        = PrintSD_Unlock;
    sdo->GetRasInfo    = PrintSD_GetRasInfo;
    sdo->Release    = PrintSD_ReleaseRasInfo;
    sdo->Setup        = NULL;
    sdo->Dispose    = PrintSD_dispose;

JNF_COCOA_EXIT(env);
}

static jint PrintSD_Lock(JNIEnv *env, SurfaceDataOps *sdo, SurfaceDataRasInfo *pRasInfo, jint lockflags)
{
PRINT(" PrintSD_Lock")
    jint status = SD_FAILURE;

    //QuartzSDOps *qsdo = (QuartzSDOps*)sdo;
    //PrintSD_startCGContext(env, qsdo, SD_Image);

    status = SD_SUCCESS;

    return status;
}
static void PrintSD_Unlock(JNIEnv *env, SurfaceDataOps *sdo, SurfaceDataRasInfo *pRasInfo)
{
PRINT(" PrintSD_Unlock")

    //QuartzSDOps *qsdo = (QuartzSDOps*)sdo;
    //PrintSD_finishCGContext(env, qsdo);
}
static void PrintSD_GetRasInfo(JNIEnv *env, SurfaceDataOps *sdo, SurfaceDataRasInfo *pRasInfo)
{
PRINT(" PrintSD_GetRasInfo")
    PrintSDOps *psdo = (PrintSDOps*)sdo;

    pRasInfo->pixelStride = 4; // ARGB
    pRasInfo->scanStride = psdo->width * pRasInfo->pixelStride;

    pRasInfo->rasBase = NULL; //psdo->dataForSun2D;
}
static void PrintSD_ReleaseRasInfo(JNIEnv *env, SurfaceDataOps *sdo, SurfaceDataRasInfo *pRasInfo)
{
PRINT(" PrintSD_ReleaseRasInfo")

    pRasInfo->pixelStride = 0;
    pRasInfo->scanStride = 0;
    pRasInfo->rasBase = NULL;
}

static void dataProvider_FreeSun2DPixels(void *info, const void *data, size_t size)
{
PRINT("dataProvider_FreeSun2DPixels")
   // CGBitmapFreeData(info);
    free(info);
}
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CPrinterSurfaceData__1flush
  (JNIEnv *env, jobject jsurfacedata)
{
    flush(env, (QuartzSDOps*)SurfaceData_GetOps(env, jsurfacedata));
}
static void flush(JNIEnv *env, QuartzSDOps *qsdo)
{
}
