/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.xsom;


/**
 * Named model group declaration.
 *
 * @author
 *  Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public interface XSModelGroupDecl extends XSDeclaration, XSTerm
{
    /**
     * Gets the body of this declaration.
     */
    XSModelGroup getModelGroup();
}
