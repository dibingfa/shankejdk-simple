/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class ReferenceIDTypeNode extends ReferenceTypeNode {

    String javaType() {
        return "long";
    }

    String debugValue(String label) {
        return "\"ref=\"+" + label;
    }

    public void genJavaWrite(PrintWriter writer, int depth,
                             String writeLabel) {
        genJavaDebugWrite(writer, depth, writeLabel);
        indent(writer, depth);
        writer.println("ps.writeClassRef(" + writeLabel + ");");
    }

    String javaRead() {
        return "ps.readClassRef()";
    }
}
