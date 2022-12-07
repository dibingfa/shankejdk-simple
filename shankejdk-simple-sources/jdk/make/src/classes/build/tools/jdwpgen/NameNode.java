/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class NameNode extends AbstractSimpleNode {

    String name;

    NameNode(String name) {
        this.name = name;
    }

    String text() {
        return name;
    }

    String value() {
        error("Valueless Name asked for value");
        return null;
    }

    public String toString() {
        return name;
    }
}
