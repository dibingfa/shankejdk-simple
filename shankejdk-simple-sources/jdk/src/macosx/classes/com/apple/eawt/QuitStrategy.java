/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

/**
 * The strategy use to shut down the application, if Sudden Termination is not enabled.
 *
 * @see Application#setQuitHandler(QuitHandler)
 * @see Application#setQuitStrategy(QuitStrategy)
 * @see Application#enableSuddenTermination()
 * @see Application#disableSuddenTermination()
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public enum QuitStrategy {
    /**
     * Shuts down the application by calling <code>System.exit(0)</code>. This is the default strategy.
     */
    SYSTEM_EXIT_0,

    /**
     * Shuts down the application by closing each window from back-to-front.
     */
    CLOSE_ALL_WINDOWS
}
