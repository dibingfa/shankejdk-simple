/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.wsdl.document;

/**
 * Enumeration of the supported WSDL operation styles.
 *
 * @author WS Development Team
 */
public final class OperationStyle {

    public static final OperationStyle ONE_WAY = new OperationStyle();
    public static final OperationStyle REQUEST_RESPONSE = new OperationStyle();
    public static final OperationStyle SOLICIT_RESPONSE = new OperationStyle();
    public static final OperationStyle NOTIFICATION = new OperationStyle();

    private OperationStyle() {
    }
}
