/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.framework;

/**
 * An interface implemented by entities that define target namespaces.
 *
 * @author WS Development Team
 */
public interface Defining extends Elemental {
    public String getTargetNamespaceURI();
}
