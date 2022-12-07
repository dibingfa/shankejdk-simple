/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class RootNode extends AbstractNamedNode {

    void constrainComponent(Context ctx, Node node) {
        if (node instanceof CommandSetNode ||
                    node instanceof ConstantSetNode) {
            node.constrain(ctx);
        } else {
            error("Expected 'CommandSet' item, got: " + node);
        }
    }

    void document(PrintWriter writer) {
        writer.println("<html><head><title>" + comment() + "</title></head>");
        writer.println("<body bgcolor=\"white\">");
        for (Node node : components) {
            node.documentIndex(writer);
        }
        for (Node node : components) {
            node.document(writer);
        }
        writer.println("</body></html>");
    }

    void genJava(PrintWriter writer, int depth) {
        writer.println("package com.sun.tools.jdi;");
        writer.println();
        writer.println("import com.sun.jdi.*;");
        writer.println("import java.util.*;");
        writer.println();

        genJavaClass(writer, depth);
    }
}
