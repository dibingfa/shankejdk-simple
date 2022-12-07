/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

/**
 * Common interface for all event listener sub-types.
 * Implementors may implement multiple sub-types, but only need to call {@link Application#addAppEventListener(AppEventListener)} once to receive all notifications.
 *
 * @see AppReOpenedListener
 * @see AppForegroundListener
 * @see AppHiddenListener
 * @see ScreenSleepListener
 * @see SystemSleepListener
 * @see UserSessionListener
 *
 * @since Java for Mac OS X 10.6 Update 3
 * @since Java for Mac OS X 10.5 Update 8
 */
public interface AppEventListener { }
