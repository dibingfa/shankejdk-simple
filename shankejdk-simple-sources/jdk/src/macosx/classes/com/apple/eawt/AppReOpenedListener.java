/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.AppReOpenedEvent;

/**
 * Implementors receive notification when the app has been asked to open again.
 * Re-open events occur when the user clicks on the running app's Dock icon.
 * Re-open events also occur when the app is double-clicked in the Finder and the app is already running.
 *
 * This notification is useful for showing a new document when your app has no open windows.
 *
 * @see Application#addAppEventListener(AppEventListener)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface AppReOpenedListener extends AppEventListener {
    /**
     * Called when the app has been re-opened (it's Dock icon was clicked on, or was double-clicked in the Finder)
     * @param e the request to re-open the app
     */
    public void appReOpened(final AppReOpenedEvent e);
}
