/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;


public abstract class Shape {
    @XmlElement
    private PaintPoints paintPoints;
    public double getPaintX1() { return paintPoints.x1; }
    public double getPaintX2() { return paintPoints.x2; }
    public double getPaintY1() { return paintPoints.y1; }
    public double getPaintY2() { return paintPoints.y2; }

    @XmlElements({
        @XmlElement(name = "matte", type = Matte.class),
        @XmlElement(name = "gradient", type = Gradient.class),
        @XmlElement(name = "radialGradient", type = RadialGradient.class)
    })
    private Paint paint;
    public Paint getPaint() { return paint; }

    static class PaintPoints {
        @XmlAttribute double x1;
        @XmlAttribute double y1;
        @XmlAttribute double x2;
        @XmlAttribute double y2;
    }
}
