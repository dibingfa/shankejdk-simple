/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlElement;

class UIFont extends UIDefault<Typeface> {
    @XmlElement
    public void setTypeface(Typeface t) {
        setValue(t);
    }

    public String write() {
        return String.format("        d.put(\"%s\", %s);\n",
                             getName(), getValue().write());
    }
}
