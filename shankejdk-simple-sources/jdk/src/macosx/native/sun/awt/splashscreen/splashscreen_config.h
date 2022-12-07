/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/* platform-dependent definitions */

#ifndef SPLASHSCREEN_CONFIG_H
#define SPLASHSCREEN_CONFIG_H

#include <sys/types.h>
#include <sys/unistd.h>
#include <signal.h>
#include <inttypes.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <pthread.h>
#include <Cocoa/Cocoa.h>

typedef uint32_t rgbquad_t;
typedef uint16_t word_t;
typedef uint8_t byte_t;


// Actually the following Rect machinery is unused since we don't use shapes
typedef int RECT_T;

#define RECT_EQ_X(r1,r2)        ((r1) == (r2))
#define RECT_SET(r,xx,yy,ww,hh) ;
#define RECT_INC_HEIGHT(r)      ;

#define SPLASHCTL_QUIT          'Q'
#define SPLASHCTL_UPDATE        'U'
#define SPLASHCTL_RECONFIGURE   'R'

#define INLINE static

#define SPLASHEXPORT

#endif
