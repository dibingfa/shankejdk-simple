/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import org.w3c.dom.Node;

public class Arg extends ElementWType<Function>{
    public String javaName;

    public Arg(final Node node, final Function parent) {
        super(node, parent);
        javaName = name;
    }
}
