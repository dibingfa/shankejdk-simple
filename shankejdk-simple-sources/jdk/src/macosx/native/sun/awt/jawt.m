/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#import <jawt.h>

#import <jawt_md.h>

#import "awt_DrawingSurface.h"

/*
 * Get the AWT native structure.
 * This function returns JNI_FALSE if an error occurs.
 */
JNIEXPORT jboolean JNICALL JAWT_GetAWT
(JNIEnv* env, JAWT* awt)
{
    if (awt == NULL) {
        return JNI_FALSE;
    }

    if (awt->version != (JAWT_VERSION_1_4 | JAWT_MACOSX_USE_CALAYER) &&
        awt->version != JAWT_VERSION_1_7)
    {
        return JNI_FALSE;
    }

    awt->GetDrawingSurface = awt_GetDrawingSurface;
    awt->FreeDrawingSurface = awt_FreeDrawingSurface;
    if (awt->version >= JAWT_VERSION_1_4) {
        awt->Lock = awt_Lock;
        awt->Unlock = awt_Unlock;
        awt->GetComponent = awt_GetComponent;
    }

    return JNI_TRUE;
}
