/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "AWTSurfaceLayers.h"

JNIEXPORT JAWT_DrawingSurfaceInfo* JNICALL awt_DrawingSurface_GetDrawingSurfaceInfo
(JAWT_DrawingSurface* ds)
{
    JAWT_DrawingSurfaceInfo* dsi = (JAWT_DrawingSurfaceInfo*)malloc(sizeof(JAWT_DrawingSurfaceInfo));

    JNIEnv *env = ds->env;
    jobject target = ds->target;

    static JNF_CLASS_CACHE(jc_Component, "java/awt/Component");
    static JNF_MEMBER_CACHE(jf_peer, jc_Component, "peer", "Ljava/awt/peer/ComponentPeer;");
    jobject peer = JNFGetObjectField(env, target, jf_peer);

    static JNF_CLASS_CACHE(jc_ComponentPeer, "sun/lwawt/LWComponentPeer");
    static JNF_MEMBER_CACHE(jf_platformComponent, jc_ComponentPeer,
                            "platformComponent", "Lsun/lwawt/PlatformComponent;");
    jobject platformComponent = JNFGetObjectField(env, peer, jf_platformComponent);

    static JNF_CLASS_CACHE(jc_PlatformComponent, "sun/lwawt/macosx/CPlatformComponent");
    static JNF_MEMBER_CACHE(jm_getPointer, jc_PlatformComponent, "getPointer", "()J");
    AWTSurfaceLayers *surfaceLayers = jlong_to_ptr(JNFCallLongMethod(env, platformComponent, jm_getPointer));
    // REMIND: assert(surfaceLayers)

    dsi->platformInfo = surfaceLayers;
    dsi->ds = ds;

    static JNF_MEMBER_CACHE(jf_x, jc_Component, "x", "I");
    static JNF_MEMBER_CACHE(jf_y, jc_Component, "y", "I");
    static JNF_MEMBER_CACHE(jf_width, jc_Component, "width", "I");
    static JNF_MEMBER_CACHE(jf_height, jc_Component, "height", "I");

    dsi->bounds.x = JNFGetIntField(env, target, jf_x);
    dsi->bounds.y = JNFGetIntField(env, target, jf_y);
    dsi->bounds.width = JNFGetIntField(env, target, jf_width);
    dsi->bounds.height = JNFGetIntField(env, target, jf_height);

    dsi->clipSize = 1;
    dsi->clip = &(dsi->bounds);

    return dsi;
}

JNIEXPORT jint JNICALL awt_DrawingSurface_Lock
(JAWT_DrawingSurface* ds)
{
    // TODO: implement
    return 0;
}

JNIEXPORT void JNICALL awt_DrawingSurface_Unlock
(JAWT_DrawingSurface* ds)
{
    // TODO: implement
}

JNIEXPORT void JNICALL awt_DrawingSurface_FreeDrawingSurfaceInfo
(JAWT_DrawingSurfaceInfo* dsi)
{
    free(dsi);
}

JNIEXPORT JAWT_DrawingSurface* JNICALL awt_GetDrawingSurface
(JNIEnv* env, jobject target)
{
    JAWT_DrawingSurface* ds = (JAWT_DrawingSurface*)malloc(sizeof(JAWT_DrawingSurface));

    // TODO: "target instanceof" check

    ds->env = env;
    ds->target = (*env)->NewGlobalRef(env, target);
    ds->Lock = awt_DrawingSurface_Lock;
    ds->GetDrawingSurfaceInfo = awt_DrawingSurface_GetDrawingSurfaceInfo;
    ds->FreeDrawingSurfaceInfo = awt_DrawingSurface_FreeDrawingSurfaceInfo;
    ds->Unlock = awt_DrawingSurface_Unlock;

    return ds;
}

JNIEXPORT void JNICALL awt_FreeDrawingSurface
(JAWT_DrawingSurface* ds)
{
    JNIEnv *env = ds->env;
    (*env)->DeleteGlobalRef(env, ds->target);
    free(ds);
}

JNIEXPORT void JNICALL awt_Lock
(JNIEnv* env)
{
    // TODO: implement
}

JNIEXPORT void JNICALL awt_Unlock
(JNIEnv* env)
{
    // TODO: implement
}

JNIEXPORT jobject JNICALL awt_GetComponent
(JNIEnv* env, void* platformInfo)
{
    // TODO: implement
    return NULL;
}
