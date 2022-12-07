/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.PrintFilesEvent;

/**
 * An implementor can respond to requests to print documents that the app has been registered to handle.
 *
 * @see Application#setPrintFileHandler(PrintFilesHandler)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface PrintFilesHandler {
    /**
     * Called when the application is asked to print a list of files.
     * @param e the request to print a list of files.
     */
    public void printFiles(final PrintFilesEvent e);
}
