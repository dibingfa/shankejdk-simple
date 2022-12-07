/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import com.apple.eawt.AppEvent.UserSessionEvent;

/**
 * Implementors receive notification when Fast User Switching changes the user session.
 *
 * This notification is useful for discontinuing a costly animation, or indicating that the user is no longer present on a network service.
 *
 * @see Application#addAppEventListener(AppEventListener)
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface UserSessionListener extends AppEventListener {
    /**
     * Called when the user session has been switched away.
     * @param e the user session switch event
     */
    public void userSessionDeactivated(final UserSessionEvent e);

    /**
     * Called when the user session has been switched to.
     * @param e the user session switch event
     */
    public void userSessionActivated(final UserSessionEvent e);
}
