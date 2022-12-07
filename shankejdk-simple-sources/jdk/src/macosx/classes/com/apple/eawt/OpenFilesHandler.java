/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.OpenFilesEvent;

/**
 * An implementor is notified when the application is asked to open a list of files.
 * This message is only sent if the application has registered that it handles CFBundleDocumentTypes in it's Info.plist.
 *
 * @see Application#setOpenFileHandler(OpenFilesHandler)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface OpenFilesHandler {
    /**
     * Called when the application is asked to open a list of files.
     * @param e the request to open a list of files, and the search term used to find them, if any.
     */
    public void openFiles(final OpenFilesEvent e);
}
