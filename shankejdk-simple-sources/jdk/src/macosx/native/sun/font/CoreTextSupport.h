/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#import <Cocoa/Cocoa.h>
#import <jni.h>
#import <JavaRuntimeSupport/JavaRuntimeSupport.h>

#include "AWTFont.h"

#pragma mark --- CoreText Support ---

#define HI_SURROGATE_START 0xD800
#define LO_SURROGATE_START 0xDC00

/*
 *    Transform Unicode characters into glyphs.
 *
 *    Fills the "glyphsAsInts" array with the glyph codes for the current font,
 *    or the negative unicode value if we know the character can be hot-substituted.
 *
 *    This is the heart of "Universal Font Substitution" in Java.
 */
void CTS_GetGlyphsAsIntsForCharacters(const AWTFont *font, const UniChar unicodes[], CGGlyph glyphs[], jint glyphsAsInts[], const size_t count);

// Translates a Java glyph code int (might be a negative unicode value) into a CGGlyph/CTFontRef pair
// Returns the substituted font, and places the appropriate glyph into "glyph"
CTFontRef CTS_CopyCTFallbackFontAndGlyphForJavaGlyphCode(const AWTFont *font, const jint glyphCode, CGGlyph *glyphRef);

// Translates a Unicode into a CGGlyph/CTFontRef pair
// Returns the substituted font, and places the appropriate glyph into "glyphRef"
CTFontRef CTS_CopyCTFallbackFontAndGlyphForUnicode(const AWTFont *font, const UTF16Char *charRef, CGGlyph *glyphRef, int count);

// Breakup a 32 bit unicode value into the component surrogate pairs
void CTS_BreakupUnicodeIntoSurrogatePairs(int uniChar, UTF16Char charRef[]);


// Basic struct that holds everything CoreText is interested in
typedef struct CTS_ProviderStruct {
    const UniChar         *unicodes;
    CFIndex                length;
    CFMutableDictionaryRef attributes;
} CTS_ProviderStruct;
