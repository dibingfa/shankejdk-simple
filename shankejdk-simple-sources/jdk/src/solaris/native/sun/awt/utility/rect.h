/*
 * Copyright (c) 2007, 2014, Oracle and/or its affiliates. All rights reserved.
 */

/* platform-dependent definitions */

#ifndef _AWT_RECT_H
#define _AWT_RECT_H

#ifndef MACOSX
#include <X11/Xlib.h>
typedef XRectangle RECT_T;
#else
// OSX still needs this for BitmapToYXBandedRectangles
typedef struct {
    int x;
    int y;
    int width;
    int height;
} RECT_T;
#endif /* !MACOSX */

#define RECT_EQ_X(r1,r2)        ((r1).x==(r2).x && (r1).width==(r2).width)

#define RECT_SET(r,xx,yy,ww,hh)  \
    do {                         \
        (r).x=(xx);              \
        (r).y=(yy);              \
        (r).width=(ww);          \
        (r).height=(hh);         \
    } while (0)

#define RECT_INC_HEIGHT(r)      (r).height++

#if defined(__cplusplus)
extern "C" {
#endif

int BitmapToYXBandedRectangles(int bitsPerPixel, int width, int height,
        unsigned char * buf, RECT_T * outBuf);

#if defined(__cplusplus)
}
#endif

#endif // _AWT_RECT_H
