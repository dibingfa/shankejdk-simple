/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import org.w3c.dom.Node;

import com.apple.internal.jobjc.generator.model.types.Type;

/**
 * A TypeElement represents a Type, such as a struct or cftype.
 */
public abstract class TypeElement<P extends Element<?>> extends ElementWType<P> {
    public TypeElement(String name, Type type, final P parent) {
        super(name, type, parent);
    }

    public TypeElement(final Node node, final String declType, final P parent) {
        super(node, declType, parent);
    }

    public TypeElement(final Node node, final P parent) {
        super(node, parent);
    }
}
