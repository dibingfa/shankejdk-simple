/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.document.http;

import com.sun.tools.internal.ws.wsdl.framework.ExtensionImpl;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;

/**
 * A HTTP urlEncoded extension.
 *
 * @author WS Development Team
 */
public class HTTPUrlEncoded extends ExtensionImpl {

    public HTTPUrlEncoded(Locator locator) {
        super(locator);
    }

    public QName getElementName() {
        return HTTPConstants.QNAME_URL_ENCODED;
    }

    public void validateThis() {
    }
}
