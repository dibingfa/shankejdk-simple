/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt.event;

/**
 * Listener interface for receiving rotation events.
 *
 * @see RotationEvent
 * @see GesturePhaseListener
 * @see GestureUtilities
 *
 * @since Java for Mac OS X 10.5 Update 7, Java for Mac OS X 10.6 Update 2
 */
public interface RotationListener extends GestureListener {
    /**
     * Invoked when a rotation gesture is performed by the user.
     * @param event containing an abstract measure of rotation.
     */
    public void rotate(final RotationEvent e);
}
