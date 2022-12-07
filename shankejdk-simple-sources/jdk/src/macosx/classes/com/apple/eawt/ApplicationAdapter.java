/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

/**
 * An abstract adapter class for receiving <code>ApplicationEvents</code>.
 *
 * ApplicationEvents are deprecated. Use individual app event listeners or handlers instead.
 *
 * @see Application#addAppEventListener(AppEventListener)
 *
 * @see AboutHandler
 * @see PreferencesHandler
 * @see OpenURIHandler
 * @see OpenFilesHandler
 * @see PrintFilesHandler
 * @see QuitHandler
 *
 * @see AppReOpenedListener
 * @see AppForegroundListener
 * @see AppHiddenListener
 * @see UserSessionListener
 * @see ScreenSleepListener
 * @see SystemSleepListener
 *
 * @deprecated replaced by {@link AboutHandler}, {@link PreferencesHandler}, {@link AppReOpenedListener}, {@link OpenFilesHandler}, {@link PrintFilesHandler}, {@link QuitHandler}, {@link QuitResponse}.
 * @since 1.4
 */
@SuppressWarnings("deprecation")
@Deprecated
public class ApplicationAdapter implements ApplicationListener {
    @Deprecated
    public void handleAbout(final ApplicationEvent event) { }

    @Deprecated
    public void handleOpenApplication(final ApplicationEvent event) { }

    @Deprecated
    public void handleOpenFile(final ApplicationEvent event) { }

    @Deprecated
    public void handlePreferences(final ApplicationEvent event) { }

    @Deprecated
    public void handlePrintFile(final ApplicationEvent event) { }

    @Deprecated
    public void handleQuit(final ApplicationEvent event) { }

    @Deprecated
    public void handleReOpenApplication(final ApplicationEvent event) { }
}
