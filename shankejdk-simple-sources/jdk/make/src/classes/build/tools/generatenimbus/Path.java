/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

class Path extends Shape {
    @XmlElement(name="point")
    @XmlElementWrapper(name="points")
    private List<Point> controlPoints = new ArrayList<Point>();
    public List<Point> getControlPoints() { return controlPoints; }
}
