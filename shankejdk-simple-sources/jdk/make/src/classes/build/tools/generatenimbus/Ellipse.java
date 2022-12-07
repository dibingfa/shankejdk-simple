/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;

class Ellipse extends Shape {
    @XmlAttribute private double x1;
    public double getX1() { return x1; }

    @XmlAttribute private double x2;
    public double getX2() { return x2; }

    @XmlAttribute private double y1;
    public double getY1() { return y1; }

    @XmlAttribute private double y2;
    public double getY2() { return y2; }
}
