/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.framework;

import com.sun.tools.internal.ws.api.wsdl.TWSDLExtension;

/**
 * A visitor working on extension entities.
 *
 * @author WS Development Team
 */
public interface ExtensionVisitor {
    public void preVisit(TWSDLExtension extension) throws Exception;
    public void postVisit(TWSDLExtension extension) throws Exception;
}
