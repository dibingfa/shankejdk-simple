/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.ScreenSleepEvent;

/**
 * Implementors receive notification when the displays attached to the system have entered power save sleep.
 *
 * This notification is useful for discontinuing a costly animation, or indicating that the user is no longer present on a network service.
 *
 * This message is not sent on Mac OS X versions prior to 10.6.
 *
 * @see Application#addAppEventListener(AppEventListener)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface ScreenSleepListener extends AppEventListener {
    /**
     * Called when the system displays have entered power save sleep.
     * @param e the screen sleep event
     */
    public void screenAboutToSleep(final ScreenSleepEvent e);

    /**
     * Called when the system displays have awoke from power save sleep.
     * @param e the screen sleep event
     */
    public void screenAwoke(final ScreenSleepEvent e);
}
