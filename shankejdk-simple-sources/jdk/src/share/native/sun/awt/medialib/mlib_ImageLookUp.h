/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */


#ifndef __MLIB_IMAGE_LOOKUP_FUNC_INTENAL_H
#define __MLIB_IMAGE_LOOKUP_FUNC_INTENAL_H

#include "mlib_ImageCopy.h"

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

#define TABLE_SHIFT_S32         536870911u


/* mlib_ImageLookUp_64.c */

void mlib_ImageLookUp_U8_D64(const mlib_u8  *src,
                             mlib_s32       slb,
                             mlib_d64       *dst,
                             mlib_s32       dlb,
                             mlib_s32       xsize,
                             mlib_s32       ysize,
                             mlib_s32       csize,
                             const mlib_d64 **table);

void mlib_ImageLookUp_S16_D64(const mlib_s16 *src,
                              mlib_s32       slb,
                              mlib_d64       *dst,
                              mlib_s32       dlb,
                              mlib_s32       xsize,
                              mlib_s32       ysize,
                              mlib_s32       csize,
                              const mlib_d64 **table);

void mlib_ImageLookUp_U16_D64(const mlib_u16 *src,
                              mlib_s32       slb,
                              mlib_d64       *dst,
                              mlib_s32       dlb,
                              mlib_s32       xsize,
                              mlib_s32       ysize,
                              mlib_s32       csize,
                              const mlib_d64 **table);

void mlib_ImageLookUp_S32_D64(const mlib_s32 *src,
                              mlib_s32       slb,
                              mlib_d64       *dst,
                              mlib_s32       dlb,
                              mlib_s32       xsize,
                              mlib_s32       ysize,
                              mlib_s32       csize,
                              const mlib_d64 **table);

void mlib_ImageLookUpSI_U8_D64(const mlib_u8  *src,
                               mlib_s32       slb,
                               mlib_d64       *dst,
                               mlib_s32       dlb,
                               mlib_s32       xsize,
                               mlib_s32       ysize,
                               mlib_s32       csize,
                               const mlib_d64 **table);

void mlib_ImageLookUpSI_S16_D64(const mlib_s16 *src,
                                mlib_s32       slb,
                                mlib_d64       *dst,
                                mlib_s32       dlb,
                                mlib_s32       xsize,
                                mlib_s32       ysize,
                                mlib_s32       csize,
                                const mlib_d64 **table);

void mlib_ImageLookUpSI_U16_D64(const mlib_u16 *src,
                                mlib_s32       slb,
                                mlib_d64       *dst,
                                mlib_s32       dlb,
                                mlib_s32       xsize,
                                mlib_s32       ysize,
                                mlib_s32       csize,
                                const mlib_d64 **table);

void mlib_ImageLookUpSI_S32_D64(const mlib_s32 *src,
                                mlib_s32       slb,
                                mlib_d64       *dst,
                                mlib_s32       dlb,
                                mlib_s32       xsize,
                                mlib_s32       ysize,
                                mlib_s32       csize,
                                const mlib_d64 **table);

/* mlib_ImageLookUp_Bit.c */

mlib_status mlib_ImageLookUp_Bit_U8_1(const mlib_u8 *src,
                                      mlib_s32      slb,
                                      mlib_u8       *dst,
                                      mlib_s32      dlb,
                                      mlib_s32      xsize,
                                      mlib_s32      ysize,
                                      mlib_s32      nchan,
                                      mlib_s32      bitoff,
                                      const mlib_u8 **table);

mlib_status mlib_ImageLookUp_Bit_U8_2(const mlib_u8 *src,
                                      mlib_s32      slb,
                                      mlib_u8       *dst,
                                      mlib_s32      dlb,
                                      mlib_s32      xsize,
                                      mlib_s32      ysize,
                                      mlib_s32      nchan,
                                      mlib_s32      bitoff,
                                      const mlib_u8 **table);

mlib_status mlib_ImageLookUp_Bit_U8_3(const mlib_u8 *src,
                                      mlib_s32      slb,
                                      mlib_u8       *dst,
                                      mlib_s32      dlb,
                                      mlib_s32      xsize,
                                      mlib_s32      ysize,
                                      mlib_s32      nchan,
                                      mlib_s32      bitoff,
                                      const mlib_u8 **table);

mlib_status mlib_ImageLookUp_Bit_U8_4(const mlib_u8 *src,
                                      mlib_s32      slb,
                                      mlib_u8       *dst,
                                      mlib_s32      dlb,
                                      mlib_s32      xsize,
                                      mlib_s32      ysize,
                                      mlib_s32      nchan,
                                      mlib_s32      bitoff,
                                      const mlib_u8 **table);

#ifdef __cplusplus
}
#endif /* __cplusplus */
#endif /* __MLIB_IMAGE_LOOKUP_FUNC_INTENAL_H */
