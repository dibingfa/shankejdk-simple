/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class MethodTypeNode extends AbstractSimpleTypeNode {

    String docType() {
        return "methodID";
    }

    String javaType() {
        return "long";
    }

    public void genJavaWrite(PrintWriter writer, int depth,
                             String writeLabel) {
        genJavaDebugWrite(writer, depth, writeLabel);
        indent(writer, depth);
        writer.println("ps.writeMethodRef(" + writeLabel + ");");
    }

    String javaRead() {
        return "ps.readMethodRef()";
    }
}
