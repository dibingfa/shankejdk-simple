/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.api.message;

import javax.xml.stream.XMLStreamReader;

public interface StreamingSOAP {
    public XMLStreamReader readEnvelope();
}
