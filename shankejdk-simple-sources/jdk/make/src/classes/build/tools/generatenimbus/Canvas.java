/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

class Canvas {
    @XmlElement private Dimension size;
    public Dimension getSize() { return size; }

    @XmlElement(name="layer") private List<Layer> layers;
    public List<Layer> getLayers() { return layers; }

    @XmlElement private Insets stretchingInsets = null;
    public Insets getStretchingInsets() { return stretchingInsets; }

    public boolean isBlank() {
        return layers.size() == 0 || (layers.size() == 1 && layers.get(0).isEmpty());
    }
}
