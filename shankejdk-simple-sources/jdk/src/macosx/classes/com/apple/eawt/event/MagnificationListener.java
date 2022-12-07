/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt.event;

/**
 * Listener interface for receiving magnification events.
 *
 * @see MagnificationEvent
 * @see GesturePhaseListener
 * @see GestureUtilities
 *
 * @since Java for Mac OS X 10.5 Update 7, Java for Mac OS X 10.6 Update 2
 */
public interface MagnificationListener extends GestureListener {
    /**
     * Invoked when a magnification gesture is performed by the user.
     * @param event containing the scale of the magnification.
     */
    public void magnify(final MagnificationEvent e);
}
