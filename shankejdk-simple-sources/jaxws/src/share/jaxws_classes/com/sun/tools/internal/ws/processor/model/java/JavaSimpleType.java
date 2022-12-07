/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.processor.model.java;

import com.sun.tools.internal.ws.processor.model.jaxb.JAXBTypeAndAnnotation;

/**
 *
 * @author WS Development Team
 */
public class JavaSimpleType extends JavaType {

    public JavaSimpleType() {}

    public JavaSimpleType(String name, String initString) {
        super(name, true, initString);
    }

    public JavaSimpleType(JAXBTypeAndAnnotation jtype) {
        super(jtype);
    }

}
