/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.document.soap;

/**
 * A SOAP "use" enumeration.
 *
 * @author WS Development Team
 */
public final class SOAPUse {

    public static final SOAPUse LITERAL = new SOAPUse();
    public static final SOAPUse ENCODED = new SOAPUse();

    private SOAPUse() {
    }
}
