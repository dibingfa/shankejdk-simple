/*
 * Copyright (c) 2007, 2008, Oracle and/or its affiliates. All rights reserved.
 */

package sun.java2d;

import java.awt.BufferCapabilities;

/**
 * Provides access to back-buffer's BufferCapabilities.
 */
public interface BackBufferCapsProvider {
    public BufferCapabilities getBackBufferCaps();
}
