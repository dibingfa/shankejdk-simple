/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.PreferencesEvent;

/**
 * An implementor is notified when the app is asked to show it's preferences UI.
 *
 * @see Application#setPreferencesHandler(PreferencesHandler)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface PreferencesHandler {
    /**
     * Called when the app is asked to show it's preferences UI.
     * @param e the request to show preferences.
     */
    public void handlePreferences(final PreferencesEvent e);
}
