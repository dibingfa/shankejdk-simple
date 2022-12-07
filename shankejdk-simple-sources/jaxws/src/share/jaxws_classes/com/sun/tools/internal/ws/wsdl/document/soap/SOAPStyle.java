/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.document.soap;

/**
 * A SOAP "style" enumeration.
 *
 * @author WS Development Team
 */
public final class SOAPStyle {

    public static final SOAPStyle RPC = new SOAPStyle();
    public static final SOAPStyle DOCUMENT = new SOAPStyle();

    private SOAPStyle() {
    }
}
