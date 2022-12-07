/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import com.apple.internal.jobjc.generator.model.types.NType;
import org.w3c.dom.Node;

import com.apple.internal.jobjc.generator.model.types.Type;
import com.apple.internal.jobjc.generator.utils.NTypeParser;

/**
 * An ElementWType has a type but does not necessarily represent a type. Examples are constants, enums, arguments, return values.
 */
public class ElementWType<P extends Element<?>> extends Element<P> {
    public final Type type;

    public ElementWType(final String name, final Type t, final P parent) {
        super(name, parent);
        this.type = t;
    }

    public ElementWType(final Node node, final Type t, final P parent) {
        super(node, parent);
        this.type = t;
    }

    public ElementWType(final Node node, final String declType, final P parent) {
        super(node, parent);
        final String type32 = getAttr(node, "type");
        final String type64 = getAttr(node, "type64");
        this.type = Type.getType(declType,
                        type32 == null ? NType.NUnknown.inst() : NTypeParser.parseFrom(type32),
                        type64 == null ? null : NTypeParser.parseFrom(type64));
    }

    public ElementWType(final Node node, final P parent){
        this(node, getAttr(node, "declared_type"), parent);
    }
}
