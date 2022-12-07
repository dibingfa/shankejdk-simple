/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

class UIStateType {
    @XmlAttribute private String key;
    public String getKey() { return key; }

    @XmlElement private String codeSnippet;
    public String getCodeSnippet() { return codeSnippet; }
}
