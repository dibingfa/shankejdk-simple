/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

class AbstractGradient extends Paint {
    public static enum CycleMethod {
        NO_CYCLE, REFLECT, REPEAT
    }

    @XmlElement(name="stop") private ArrayList<GradientStop> stops;
    public List<GradientStop> getStops() { return stops; }
}
