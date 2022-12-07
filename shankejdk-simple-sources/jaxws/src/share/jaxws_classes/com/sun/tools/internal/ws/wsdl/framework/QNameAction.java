/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.framework;

import javax.xml.namespace.QName;

/**
 * An action operating on a QName.
 *
 * @author WS Development Team
 */
public interface QNameAction {
    public void perform(QName name);
}
