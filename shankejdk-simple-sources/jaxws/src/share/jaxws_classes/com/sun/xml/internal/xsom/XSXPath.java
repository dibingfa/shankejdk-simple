/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom;

/**
 * Selector or field of {@link XSIdentityConstraint}.
 *
 * @author Kohsuke Kawaguchi
 */
public interface XSXPath extends XSComponent  {

    /**
     * Returns the {@link XSIdentityConstraint} to which
     * this XPath belongs to.
     *
     * @return
     *      never null.
     */
    XSIdentityConstraint getParent();

    /**
     * Gets the XPath as a string.
     *
     * @return
     *      never null.
     */
    XmlString getXPath();
}
