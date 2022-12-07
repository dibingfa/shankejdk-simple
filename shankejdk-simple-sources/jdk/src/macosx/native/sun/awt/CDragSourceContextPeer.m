/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import "sun_lwawt_macosx_CDragSourceContextPeer.h"

#import <JavaNativeFoundation/JavaNativeFoundation.h>

#import "CDragSource.h"
#import "ThreadUtilities.h"


/*
 * Class:     sun_lwawt_macosx_CDragSourceContextPeer
 * Method:    createNativeDragSource
 * Signature: (Ljava/awt/Component;JLjava/awt/datatransfer/Transferable;
               Ljava/awt/event/InputEvent;IIIIJIJIII[JLjava/util/Map;)J
 */
JNIEXPORT jlong JNICALL Java_sun_lwawt_macosx_CDragSourceContextPeer_createNativeDragSource
  (JNIEnv *env, jobject jthis, jobject jcomponent, jlong jnativepeer, jobject jtransferable,
   jobject jtrigger, jint jdragposx, jint jdragposy, jint jextmodifiers, jint jclickcount, jlong jtimestamp,
   jobject jnsdragimage, jint jdragimageoffsetx, jint jdragimageoffsety,
   jint jsourceactions, jlongArray jformats, jobject jformatmap)
{
    id controlObj = (id) jlong_to_ptr(jnativepeer);
    __block CDragSource* dragSource = nil;

JNF_COCOA_ENTER(env);
    [ThreadUtilities performOnMainThreadWaiting:YES block:^(){
        dragSource = [[CDragSource alloc] init:jthis
                                     component:jcomponent
                                       control:controlObj
                                  transferable:jtransferable
                                  triggerEvent:jtrigger
                                      dragPosX:jdragposx
                                      dragPosY:jdragposy
                                     modifiers:jextmodifiers
                                    clickCount:jclickcount
                                     timeStamp:jtimestamp
                                     dragImage:jnsdragimage
                              dragImageOffsetX:jdragimageoffsetx
                              dragImageOffsetY:jdragimageoffsety
                                 sourceActions:jsourceactions
                                       formats:jformats
                                     formatMap:jformatmap];
    }];
JNF_COCOA_EXIT(env);

    return ptr_to_jlong(dragSource);
}

/*
 * Class:     sun_lwawt_macosx_CDragSourceContextPeer
 * Method:    doDragging
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CDragSourceContextPeer_doDragging
  (JNIEnv *env, jobject jthis, jlong nativeDragSourceVal)
{
    AWT_ASSERT_NOT_APPKIT_THREAD;

    CDragSource* dragSource = (CDragSource*) jlong_to_ptr(nativeDragSourceVal);

JNF_COCOA_ENTER(env);
    [dragSource drag];
JNF_COCOA_EXIT(env);
}

/*
 * Class:     sun_lwawt_macosx_CDragSourceContextPeer
 * Method:    releaseNativeDragSource
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_sun_lwawt_macosx_CDragSourceContextPeer_releaseNativeDragSource
  (JNIEnv *env, jobject jthis, jlong nativeDragSourceVal)
{
      CDragSource* dragSource = (CDragSource*) jlong_to_ptr(nativeDragSourceVal);

JNF_COCOA_ENTER(env);
    [dragSource removeFromView:env];
JNF_COCOA_EXIT(env);
}
