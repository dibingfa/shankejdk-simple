/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class ArrayRegionTypeNode extends AbstractSimpleTypeNode {

    String docType() {
        return "arrayregion";
    }

    String javaType() {
        return "List<?>";
    }

    public void genJavaWrite(PrintWriter writer, int depth,
                             String writeLabel) {
        error("Not implemented");
    }

    String javaRead() {
        return "ps.readArrayRegion()";
    }
}
