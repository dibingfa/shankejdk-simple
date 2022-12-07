/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

class UIComponent extends UIRegion {
    @XmlAttribute private String componentName;

    @XmlElement(name="stateType")
    @XmlElementWrapper(name="stateTypes")
    private List<UIStateType> stateTypes = new ArrayList<UIStateType>();
    public List<UIStateType> getStateTypes() { return stateTypes; }

    @Override public String getKey() {
        if (key == null || "".equals(key)) {
            if (componentName == null || "".equals(componentName)) {
                return name;
            } else {
                return "\"" + componentName + "\"";
            }
        } else {
            return key;
        }
    }
}
