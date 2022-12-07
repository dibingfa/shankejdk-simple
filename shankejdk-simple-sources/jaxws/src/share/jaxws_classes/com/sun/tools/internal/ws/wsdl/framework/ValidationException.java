/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.framework;

import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;

/**
 * An exception signalling that validation of an entity failed.
 *
 * @author WS Development Team
 */
public class ValidationException extends JAXWSExceptionBase {

    public ValidationException(String key, Object... args) {
        super(key, args);
    }

    public ValidationException(Throwable throwable) {
        super(throwable);
    }

    public String getDefaultResourceBundleName() {
        return "com.sun.tools.internal.ws.resources.wsdl";
    }
}
