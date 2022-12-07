/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class ConstantNode extends AbstractCommandNode {

    ConstantNode() {
        this(new ArrayList<Node>());
    }

    ConstantNode(List<Node> components) {
        this.kind = "Constant";
        this.components = components;
        this.lineno = 0;
    }

    void constrain(Context ctx) {
        if (components.size() != 0) {
            error("Constants have no internal structure");
        }
        super.constrain(ctx);
    }

    void genJava(PrintWriter writer, int depth) {
        indent(writer, depth);
        writer.println("static final int " + name + " = " +
                       nameNode.value() + ";");
    }

    void document(PrintWriter writer) {

        //Add anchor to each constant with format <constant table name>_<constant name>
        writer.println("<tr><td>" + name + "<td>" + nameNode.value() +
                       "<td>" + comment() + " &nbsp;");
    }

    public String getName(){

        if (name == null || name.length() == 0) {
            prune();
        }
        return name;
    }
}
