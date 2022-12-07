/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class GeneratedClassFile extends OutputFile {
    protected final String className;
    protected final String genericArgs;
    protected final String superClass;

    public GeneratedClassFile(final String pkg, final String classname, final String superClass) {
        this(pkg, classname, null, superClass);
    }

    public GeneratedClassFile(final String pkg, final String classname, final String genericArgs, final String superClass) {
        super(pkg, classname + ".java");
        this.className = classname;
        this.genericArgs = genericArgs;
        this.superClass = superClass;
    }

    @Override
    public void write(final File parentDir) {
        try {
            final PrintStream out = open(parentDir);
            out.println("package " + pkg + ";");
            out.println();
            out.print("public " + (isFinal() ? "final" : "") + " class " + className);
            if(genericArgs != null) out.print("<" + genericArgs + ">");
            if (superClass != null) out.print(" extends " + superClass);
            out.println(" {");
            writeBeginning(out);
            writeBody(out);
            writeEnd(out);
            out.println("}");
            close(out);
        } catch (final IOException e) { throw new RuntimeException(e); }
    }

    public void writeBeginning(final PrintStream out) {

    }

    public void writeBody(final PrintStream out) {

    }

    public void writeEnd(final PrintStream out) {

    }

    protected boolean isFinal(){ return false; }
}
