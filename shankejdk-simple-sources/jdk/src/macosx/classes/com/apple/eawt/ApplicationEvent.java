/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import java.util.EventObject;

/**
 * The class of events sent to the deprecated ApplicationListener callbacks.
 *
 * @deprecated replaced by {@link AboutHandler}, {@link PreferencesHandler}, {@link AppReOpenedListener}, {@link OpenFilesHandler}, {@link PrintFilesHandler}, {@link QuitHandler}, {@link QuitResponse}
 * @since 1.4
 */
@Deprecated
public class ApplicationEvent extends EventObject {
    private String fFilename = null;
    private boolean fHandled = false;

    ApplicationEvent(final Object source) {
        super(source);
    }

    ApplicationEvent(final Object source, final String filename) {
        super(source);
        fFilename = filename;
    }

    /**
     * Determines whether an ApplicationListener has acted on a particular event.
     * An event is marked as having been handled with <code>setHandled(true)</code>.
     *
     * @return <code>true</code> if the event has been handled, otherwise <code>false</code>
     *
     * @since 1.4
     * @deprecated
     */
    @Deprecated
    public boolean isHandled() {
        return fHandled;
    }

    /**
     * Marks the event as handled.
     * After this method handles an ApplicationEvent, it may be useful to specify that it has been handled.
     * This is usually used in conjunction with <code>getHandled()</code>.
     * Set to <code>true</code> to designate that this event has been handled. By default it is <code>false</code>.
     *
     * @param state <code>true</code> if the event has been handled, otherwise <code>false</code>.
     *
     * @since 1.4
     * @deprecated
     */
    @Deprecated
    public void setHandled(final boolean state) {
        fHandled = state;
    }

    /**
     * Provides the filename associated with a particular AppleEvent.
     * When the ApplicationEvent corresponds to an AppleEvent that needs to act on a particular file, the ApplicationEvent carries the name of the specific file with it.
     * For example, the Print and Open events refer to specific files.
     * For these cases, this returns the appropriate file name.
     *
     * @return the full path to the file associated with the event, if applicable, otherwise <code>null</code>
     *
     * @since 1.4
     * @deprecated use {@link OpenFilesHandler} or {@link PrintFilesHandler} instead
     */
    @Deprecated
    public String getFilename() {
        return fFilename;
    }
}
