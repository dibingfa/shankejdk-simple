/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.AboutEvent;

/**
 * An implementor receives notification when the app is asked to show it's about dialog.
 *
 * @see Application#setAboutHandler(AboutHandler)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface AboutHandler {
    /**
     * Called when the application is asked to show it's about dialog.
     * @param e the request to show the about dialog.
     */
    public void handleAbout(final AboutEvent e);
}
