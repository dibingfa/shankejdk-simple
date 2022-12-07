/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.document.soap;

import org.xml.sax.Locator;

import javax.xml.namespace.QName;

public class SOAP12Binding extends SOAPBinding{
    public SOAP12Binding(Locator locator) {
        super(locator);
    }

    @Override public QName getElementName() {
        return SOAP12Constants.QNAME_BINDING;
    }
}
