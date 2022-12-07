/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.runtime;

import java.security.CodeSource;
import java.util.Objects;

/**
 * Responsible for loading script generated classes.
 *
 */
final class ScriptLoader extends NashornLoader {
    private static final String NASHORN_PKG_PREFIX = "jdk.nashorn.internal.";

    private final Context context;

    /*package-private*/ Context getContext() {
        return context;
    }

    /**
     * Constructor.
     */
    ScriptLoader(final Context context) {
        super(context.getStructLoader());
        this.context = context;
    }

    @Override
    protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        checkPackageAccess(name);
        return super.loadClass(name, resolve);
    }


     @Override
     protected Class<?> findClass(String name) throws ClassNotFoundException {
         final ClassLoader appLoader = context.getAppLoader();

         /*
          * If the appLoader is null, don't bother side-delegating to it!
          * Bootloader has been already attempted via parent loader
          * delegation from the "loadClass" method.
          *
          * Also, make sure that we don't delegate to the app loader
          * for nashorn's own classes or nashorn generated classes!
          */
         if (appLoader == null || name.startsWith(NASHORN_PKG_PREFIX)) {
             throw new ClassNotFoundException(name);
         }

         /*
          * This split-delegation is used so that caller loader
          * based resolutions of classes would work. For example,
          * java.sql.DriverManager uses caller's class loader to
          * get Driver instances. Without this split-delegation
          * a script class evaluating DriverManager.getDrivers()
          * will not get back any JDBC driver!
          */
         return appLoader.loadClass(name);
     }

    // package-private and private stuff below this point

    /**
     * Install a class for use by the Nashorn runtime
     *
     * @param name Binary name of class.
     * @param data Class data bytes.
     * @param cs CodeSource code source of the class bytes.
     *
     * @return Installed class.
     */
    synchronized Class<?> installClass(final String name, final byte[] data, final CodeSource cs) {
        return defineClass(name, data, 0, data.length, Objects.requireNonNull(cs));
    }
}
