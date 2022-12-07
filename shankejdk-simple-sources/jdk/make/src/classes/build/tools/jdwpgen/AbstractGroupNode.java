/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.io.*;

abstract class AbstractGroupNode extends AbstractTypeListNode {

    void document(PrintWriter writer) {
        for (Node node : components) {
            node.document(writer);
        }
    }

    String javaType() {
        return name();
    }

    void genJava(PrintWriter writer, int depth) {
        genJavaClass(writer, depth);
    }

    void genJavaWriteMethod(PrintWriter writer, int depth) {
        genJavaWriteMethod(writer, depth, "private ");
    }

    void genJavaWriteMethod(PrintWriter writer, int depth, String modifier) {
        writer.println();
        indent(writer, depth);
        writer.print(modifier);
        writer.println("void write(PacketStream ps) {");
        genJavaWrites(writer, depth+1);
        indent(writer, depth);
        writer.println("}");
    }

    void genJavaClassSpecifics(PrintWriter writer, int depth) {
        switch (context.state) {
            case Context.readingReply:
                genJavaReadingClassBody(writer, depth, name());
                break;

            case Context.writingCommand:
                genJavaWritingClassBody(writer, depth, name());
                genJavaWriteMethod(writer, depth);
                break;

            default:
                error("Group in outer");
                break;
        }
    }

    public void genJavaDeclaration(PrintWriter writer, int depth) {
        writer.println();
        genJavaComment(writer, depth);
        indent(writer, depth);
        writer.print("final ");
        writer.print(name());
        writer.print(" a" + name());
        writer.println(";");
    }

    public String javaParam() {
        return name() + " a" + name();
    }

    public void genJavaWrite(PrintWriter writer, int depth,
                             String writeLabel) {
        genJavaDebugWrite(writer, depth, writeLabel, "\"\"");
        indent(writer, depth);
        writer.println(writeLabel + ".write(ps);");
    }

    String javaRead() {
        error("Internal - Should not call AbstractGroupNode.javaRead()");
        return "";
    }

    public void genJavaRead(PrintWriter writer, int depth,
                            String readLabel) {
        genJavaDebugRead(writer, depth, readLabel, "\"\"");
        indent(writer, depth);
        writer.print(readLabel);
        writer.print(" = new ");
        writer.print(name());
        writer.println("(vm, ps);");
    }
}
