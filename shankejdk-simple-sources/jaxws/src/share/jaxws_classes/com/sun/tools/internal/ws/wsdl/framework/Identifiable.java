/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.framework;

/**
 * An interface implemented by entities which have an ID.
 *
 * @author WS Development Team
 */
public interface Identifiable extends Elemental {
    public String getID();
}
