/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef CGLLayer_h_Included
#define CGLLayer_h_Included

#import "AWTView.h"

@interface CGLLayer : CAOpenGLLayer
{
@private
    JNFWeakJObjectWrapper *javaLayer;

    // intermediate buffer, used the RQ lock to synchronize
    GLuint textureID;
    GLenum target;
    float textureWidth;
    float textureHeight;
#ifdef REMOTELAYER
    CGLLayer *parentLayer;
    CGLLayer *remoteLayer;
    NSObject<JRSRemoteLayer> *jrsRemoteLayer;
#endif /* REMOTELAYER */
}

@property (nonatomic, retain) JNFWeakJObjectWrapper *javaLayer;
@property (readwrite, assign) GLuint textureID;
@property (readwrite, assign) GLenum target;
@property (readwrite, assign) float textureWidth;
@property (readwrite, assign) float textureHeight;

#ifdef REMOTELAYER
@property (nonatomic, retain) CGLLayer *parentLayer;
@property (nonatomic, retain) CGLLayer *remoteLayer;
@property (nonatomic, retain) NSObject<JRSRemoteLayer> *jrsRemoteLayer;
#endif

- (id) initWithJavaLayer:(JNFWeakJObjectWrapper *)javaLayer;
- (void) blitTexture;
@end

#endif /* CGLLayer_h_Included */
