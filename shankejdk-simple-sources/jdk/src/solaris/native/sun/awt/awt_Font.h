/*
 * Copyright (c) 1998, 2011, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni_util.h>

/* fieldIDs for Font fields that may be accessed from C */
struct FontIDs {
    jfieldID pData;
    jfieldID style;
    jfieldID size;
    jmethodID getPeer;
    jmethodID getFamily;
};

/* fieldIDs for XFontPeer fields that may be accessed from C */
struct XFontPeerIDs {
    jfieldID xfsname;
};

/* fieldIDs for PlatformFont fields that may be accessed from C */
struct PlatformFontIDs {
    jfieldID componentFonts;
    jfieldID fontConfig;
    jmethodID makeConvertedMultiFontString;
    jmethodID makeConvertedMultiFontChars;
};
