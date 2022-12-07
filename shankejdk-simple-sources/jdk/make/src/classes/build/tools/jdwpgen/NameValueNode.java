/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class NameValueNode extends NameNode {

    String val;

    NameValueNode(String name, String val) {
        super(name);
        this.val = val;
    }

    NameValueNode(String name, int ival) {
        super(name);
        this.val = Integer.toString(ival);
    }

    String value() {
        return val;
    }

    public String toString() {
        return name + "=" + val;
    }
}
