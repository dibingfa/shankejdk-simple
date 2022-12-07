/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;

class Point {
    @XmlAttribute private double x;
    public double getX() { return x; }

    @XmlAttribute private double y;
    public double getY() { return y; }

    @XmlAttribute(name="cp1x") private double cp1x;
    public double getCp1X() { return cp1x; }

    @XmlAttribute(name="cp1y") private double cp1y;
    public double getCp1Y() { return cp1y; }

    @XmlAttribute(name="cp2x") private double cp2x;
    public double getCp2X() { return cp2x; }

    @XmlAttribute(name="cp2y") private double cp2y;
    public double getCp2Y() { return cp2y; }

    public boolean isP1Sharp() {
        return cp1x == x && cp1y == y;
    }

    public boolean isP2Sharp() {
        return cp2x == x && cp2y == y;
    }
}
