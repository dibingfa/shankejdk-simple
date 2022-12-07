/*
 * Copyright (c) 2006, 2015, Oracle and/or its affiliates. All rights reserved.
 */

package sun.misc;

import java.net.URLClassLoader;
import java.net.InetAddress;

public interface JavaNetAccess {
    /**
     * return the URLClassPath belonging to the given loader
     */
    URLClassPath getURLClassPath (URLClassLoader u);

    /**
     * Return the original application specified hostname of
     * the given InetAddress object.
     */
    String getOriginalHostName(InetAddress ia);
}
