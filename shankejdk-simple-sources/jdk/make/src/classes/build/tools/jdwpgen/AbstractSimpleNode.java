/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

abstract class AbstractSimpleNode extends Node {

    AbstractSimpleNode() {
        kind = "-simple-";
        components = new ArrayList<Node>();
    }

    void document(PrintWriter writer) {
        writer.print(toString());
    }
}
