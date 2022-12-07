/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

class GradientStop {
    @XmlAttribute private float position;
    public float getPosition() { return position; }

    @XmlAttribute private float midpoint;
    public float getMidpoint() { return midpoint; }

    @XmlElement private Matte matte;
    public Matte getColor() { return matte; }
}
