/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnumValue;

class Border {
    enum BorderType {
        @XmlEnumValue("empty") EMPTY,
        @XmlEnumValue("painter") PAINTER
    }
    @XmlAttribute private BorderType type;
    @XmlAttribute private String painter;
    @XmlAttribute private int top;
    @XmlAttribute private int left;
    @XmlAttribute private int bottom;
    @XmlAttribute private int right;

    public String write() {
        switch (type) {
            case PAINTER:
                return String.format("new PainterBorder(\"%s\", new Insets(%d, %d, %d, %d))",
                                     painter, top, left, bottom, right);
            case EMPTY:
                return String.format("BorderFactory.createEmptyBorder(%d, %d, %d, %d)",
                                     top, left, bottom, right);
            default:
                return "### Look, here's an unknown border! $$$";
        }
    }
}
