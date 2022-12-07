/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

#import <jawt_md.h>

/*
 * The CALayer-based rendering model returns an object conforming
 * to the JAWT_SurfaceLayers protocol
 *
 * @protocol JAWT_SurfaceLayers
 * @property (readwrite, retain) CALayer *layer;
 * @property (readonly) CALayer *windowLayer;
 * @end
 */

@interface AWTSurfaceLayers : NSObject<JAWT_SurfaceLayers> {
@private
    CALayer *layer;
    CALayer *windowLayer;
}

@property (retain) CALayer *windowLayer;

- (id) initWithWindowLayer: (CALayer *)windowLayer;
- (void) setBounds: (CGRect)rect;

@end
