/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.io.*;

class AbstractCommandNode extends AbstractNamedNode {

    void document(PrintWriter writer) {
        writer.println("<h5><a name=\"" + context.whereC + "\">" + name +
                       " Command</a> (" + nameNode.value() + ")</h5>");
        writer.println(comment());
        writer.println("<dl>");
        for (Node node : components) {
            node.document(writer);
        }
        writer.println("</dl>");
    }

    void documentIndex(PrintWriter writer) {
        writer.print("        <li><a href=\"#" + context.whereC + "\">");
        writer.println(name() + "</a> (" + nameNode.value() + ")");
    }
}
