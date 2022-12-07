/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.io.*;

class ErrorSetNode extends AbstractSimpleNode {

    void constrainComponent(Context ctx, Node node) {
        if (node instanceof ErrorNode) {
            node.constrain(ctx);
        } else {
            error("Expected 'Error' item, got: " + node);
        }
    }

    void document(PrintWriter writer) {

        writer.println("<dt>" + "Error Data");
        if (components.isEmpty()) {
            writer.println("<dd>(None)");
        } else {
            writer.println("<dd><table border=1 cellpadding=3 cellspacing=0 width=\"90%\" summary=\"\">");
        for (Node node : components) {
            node.document(writer);
        }
        writer.println("</table>");
        }
    }

    void genJavaComment(PrintWriter writer, int depth) {}

    void genJava(PrintWriter writer, int depth) {}

    void genCInclude(PrintWriter writer) {}

    void genJavaDebugWrite(PrintWriter writer, int depth,
                           String writeLabel) {}

    void genJavaDebugWrite(PrintWriter writer, int depth,
                           String writeLabel, String displayValue) {}

    public void genJavaRead(PrintWriter writer, int depth,
                            String readLabel) {}

    void genJavaDebugRead(PrintWriter writer, int depth,
                          String readLabel, String displayValue) {}

    void genJavaPreDef(PrintWriter writer, int depth) {}

}
