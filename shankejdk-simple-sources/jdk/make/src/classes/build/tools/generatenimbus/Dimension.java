/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;

class Dimension {
    @XmlAttribute int width;
    @XmlAttribute int height;

    public String write(boolean uiResource) {
        String uiSuffix = (uiResource ? "UIResource" : "");
        return String.format("new Dimension%s(%d, %d)", uiSuffix, width, height);
    }
}
