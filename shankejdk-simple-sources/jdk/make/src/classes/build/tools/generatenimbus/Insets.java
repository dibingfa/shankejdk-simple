/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;

class Insets {
    @XmlAttribute int top;
    @XmlAttribute int left;
    @XmlAttribute int bottom;
    @XmlAttribute int right;

    public Insets() {
        this(0, 0, 0, 0);
    }

    public Insets(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public String write(boolean uiResource) {
        String uiSuffix = (uiResource ? "UIResource" : "");
        return String.format("new Insets%s(%d, %d, %d, %d)",
                             uiSuffix, top, left, bottom, right);
    }
}
