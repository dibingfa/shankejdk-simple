/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import org.w3c.dom.Node;

public class StringConstant extends Element<Framework> {
    public final String value;

    public StringConstant(final Node node, final Framework parent) {
        super(node, parent);
        this.value = getAttr(node, "value");
    }
}
