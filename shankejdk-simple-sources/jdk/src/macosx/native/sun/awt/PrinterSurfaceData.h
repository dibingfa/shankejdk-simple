/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import "QuartzSurfaceData.h"

struct _PrintSDOps
{
    QuartzSDOps                qsdo; // must be the first entry!

    NSGraphicsContext        *nsRef;

    jint                    width;
    jint                    height;
};
typedef struct _PrintSDOps PrintSDOps;
