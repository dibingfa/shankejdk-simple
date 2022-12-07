/*
 * Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved.
 */

#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "AWTFont.h"
#import "CoreTextSupport.h"

#import "sun_font_CCharToGlyphMapper.h"

/*
 * Class:     sun_font_CCharToGlyphMapper
 * Method:    countGlyphs
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL
Java_sun_font_CCharToGlyphMapper_countGlyphs
    (JNIEnv *env, jclass clazz, jlong awtFontPtr)
{
    jint numGlyphs = 0;

JNF_COCOA_ENTER(env);

    AWTFont *awtFont = (AWTFont *)jlong_to_ptr(awtFontPtr);
    numGlyphs = [awtFont->fFont numberOfGlyphs];

JNF_COCOA_EXIT(env);

    return numGlyphs;
}

static inline void
GetGlyphsFromUnicodes(JNIEnv *env, AWTFont *awtFont,
                      jint count, UniChar *unicodes,
                      CGGlyph *cgGlyphs, jintArray glyphs)
{
    jint *glyphCodeInts = (*env)->GetPrimitiveArrayCritical(env, glyphs, 0);

    CTS_GetGlyphsAsIntsForCharacters(awtFont, unicodes,
                                     cgGlyphs, glyphCodeInts, count);

    // Do not use JNI_COMMIT, as that will not free the buffer copy
    // when +ProtectJavaHeap is on.
    (*env)->ReleasePrimitiveArrayCritical(env, glyphs, glyphCodeInts, 0);
}

static inline void
AllocateGlyphBuffer(JNIEnv *env, AWTFont *awtFont,
                    jint count, UniChar *unicodes, jintArray glyphs)
{
    if (count < MAX_STACK_ALLOC_GLYPH_BUFFER_SIZE) {
        CGGlyph cgGlyphs[count];
        GetGlyphsFromUnicodes(env, awtFont, count, unicodes, cgGlyphs, glyphs);
    } else {
        CGGlyph *cgGlyphs = (CGGlyph *)malloc(count * sizeof(CGGlyph));
        GetGlyphsFromUnicodes(env, awtFont, count, unicodes, cgGlyphs, glyphs);
        free(cgGlyphs);
    }
}

/*
 * Class:     sun_font_CCharToGlyphMapper
 * Method:    nativeCharsToGlyphs
 * Signature: (JI[C[I)V
 */
JNIEXPORT void JNICALL
Java_sun_font_CCharToGlyphMapper_nativeCharsToGlyphs
    (JNIEnv *env, jclass clazz,
     jlong awtFontPtr, jint count, jcharArray unicodes, jintArray glyphs)
{
JNF_COCOA_ENTER(env);

    AWTFont *awtFont = (AWTFont *)jlong_to_ptr(awtFontPtr);

    // check the array size
    jint len = (*env)->GetArrayLength(env, glyphs);
    if (len < count) {
        count = len;
    }

    jchar *unicodesAsChars =
        (*env)->GetPrimitiveArrayCritical(env, unicodes, NULL);

    if (unicodesAsChars != NULL) {
        AllocateGlyphBuffer(env, awtFont, count,
                           (UniChar *)unicodesAsChars, glyphs);

    (*env)->ReleasePrimitiveArrayCritical(env, unicodes,
                                          unicodesAsChars, JNI_ABORT);
    }

JNF_COCOA_EXIT(env);
}
