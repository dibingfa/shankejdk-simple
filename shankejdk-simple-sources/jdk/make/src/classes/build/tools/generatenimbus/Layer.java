/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

class Layer {
    /** List of shapes in this layer, first shape is painted on top */
    @XmlElements({
        @XmlElement(name = "ellipse", type = Ellipse.class),
        @XmlElement(name = "path", type = Path.class),
        @XmlElement(name = "rectangle", type = Rectangle.class)
    })
    @XmlElementWrapper(name="shapes")
    private List<Shape> shapes = new ArrayList<Shape>();
    public List<Shape> getShapes() { return shapes; }

    public boolean isEmpty() {
        return shapes.isEmpty();
    }
}
