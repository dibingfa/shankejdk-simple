/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import org.w3c.dom.Node;

import com.apple.internal.jobjc.generator.model.types.Type;

public class ReturnValue extends ElementWType<Function>{
    public static ReturnValue VOID = new ReturnValue(Type.VOID);

    public ReturnValue(final Node node, final Function parent) {
        super(node, parent);
    }

    public ReturnValue(Type type) {
        super("return value", type, null); // TODO bad style, the null might lead to trouble
    }

    @Override public boolean equals(Object o){
        return o instanceof ReturnValue && type.equals(((ReturnValue)o).type);
    }
}
