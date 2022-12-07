/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

abstract class AbstractNamedNode extends Node {

    NameNode nameNode = null;
    String name;

    public String name() {
        return name;
    }

    void prune() {
        Iterator<Node> it = components.iterator();

        if (it.hasNext()) {
            Node nameNode = it.next();

            if (nameNode instanceof NameNode) {
                this.nameNode = (NameNode)nameNode;
                this.name = this.nameNode.text();
                it.remove();
            } else {
                error("Bad name: " + name);
            }
        } else {
            error("empty");
        }
        super.prune();
    }

    void constrain(Context ctx) {
        nameNode.constrain(ctx);
        super.constrain(ctx.subcontext(name));
    }

    void document(PrintWriter writer) {
        writer.println("<h4><a name=" + name + ">" + name +
                       " Command Set</a></h4>");
        for (Node node : components) {
            node.document(writer);
        }
    }

    String javaClassName() {
        return name();
    }

    void genJavaClassSpecifics(PrintWriter writer, int depth) {
    }

    String javaClassImplements() {
        return ""; // does not implement anything, by default
    }

    void genJavaClass(PrintWriter writer, int depth) {
        writer.println();
        genJavaComment(writer, depth);
        indent(writer, depth);
        if (depth != 0) {
            writer.print("static ");
        }
        writer.print("class " + javaClassName());
        writer.println(javaClassImplements() + " {");
        genJavaClassSpecifics(writer, depth+1);
        for (Node node : components) {
            node.genJava(writer, depth+1);
        }
        indent(writer, depth);
        writer.println("}");
    }

    void genCInclude(PrintWriter writer) {
        if (nameNode instanceof NameValueNode) {
            writer.println("#define " + context.whereC +
                           " " + nameNode.value());
        }
        super.genCInclude(writer);
    }
}
