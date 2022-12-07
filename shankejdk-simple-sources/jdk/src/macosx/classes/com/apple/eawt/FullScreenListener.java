/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import java.util.EventListener;

import com.apple.eawt.AppEvent.FullScreenEvent;

/**
 *
 *
 * @since Java for Mac OS X 10.7 Update 1
 */
public interface FullScreenListener extends EventListener {
        /**
     * Invoked when a window has started to enter full screen.
     * @param event containing the specific window entering full screen.
     */
        public void windowEnteringFullScreen(final FullScreenEvent e);

        /**
     * Invoked when a window has fully entered full screen.
     * @param event containing the specific window which has entered full screen.
     */
        public void windowEnteredFullScreen(final FullScreenEvent e);

        /**
     * Invoked when a window has started to exit full screen.
     * @param event containing the specific window exiting full screen.
     */
        public void windowExitingFullScreen(final FullScreenEvent e);

        /**
     * Invoked when a window has fully exited full screen.
     * @param event containing the specific window which has exited full screen.
     */
        public void windowExitedFullScreen(final FullScreenEvent e);
}
