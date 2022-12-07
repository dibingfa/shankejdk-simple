/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.SystemSleepEvent;

/**
 * Implementors receive notification as the system is entering sleep, and after the system wakes.
 *
 * This notification is useful for disconnecting from network services prior to sleep, or re-establishing a connection if the network configuration has changed during sleep.
 *
 * @see Application#addAppEventListener(AppEventListener)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface SystemSleepListener extends AppEventListener {
    /**
     * Called when the system is about to sleep.
     * Note: This message may not be delivered prior to the actual system sleep, and may be processed after the corresponding wake has occurred.
     * @param e the system sleep event
     */
    public void systemAboutToSleep(final SystemSleepEvent e);

    /**
     * Called after the system has awoke from sleeping.
     * @param e the system sleep event
     */
    public void systemAwoke(final SystemSleepEvent e);
}
