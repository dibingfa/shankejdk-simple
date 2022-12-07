/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.OpenURIEvent;

/**
 * An implementor is notified when the application is asked to open a URI.
 * The application only sends {@link com.apple.eawt.EAWTEvent.OpenURIEvent}s when it has been launched as a bundled Mac application, and it's Info.plist claims URL schemes in it's <code>CFBundleURLTypes</code> entry.
 * See the <a href="http://developer.apple.com/mac/library/documentation/General/Reference/InfoPlistKeyReference">Info.plist Key Reference</a> for more information about adding a <code>CFBundleURLTypes</code> key to your app's Info.plist.
 *
 * @see Application#setOpenURIHandler(OpenURIHandler)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface OpenURIHandler {
    /**
     * Called when the application is asked to open a URI
     * @param e the request to open a URI
     */
    public void openURI(final OpenURIEvent e);
}
