/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlElement;

class UIColor extends UIDefault<Matte> {

    @XmlElement
    public void setMatte(Matte m) {
        setValue(m);
    }

    public String write() {
        return String.format("        addColor(d, \"%s\", %s);\n",
                             getName(), getValue().write());
    }
}
