/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.PrintStream;

import com.apple.internal.jobjc.generator.model.Opaque;
import com.apple.jobjc.Pointer;

public class OpaqueClassFile extends GeneratedClassFile {
    final Opaque opaque;

    public OpaqueClassFile(final Opaque opaque) {
        super(opaque.parent.pkg, opaque.type.getJType().getJavaClassName(), com.apple.jobjc.Opaque.class.getCanonicalName());
        this.opaque = opaque;
    }

    @Override public void writeBeginning(PrintStream out){
        out.println("\t// " + opaque.type);
        out.println("\t// " + opaque.type.getJType());
        out.println("");
        out.println("\tpublic " + className + "(" + Pointer.class.getName() + "<?> ptr){");
        out.println("\t\tsuper(ptr);");
        out.println("\t}");
        out.println("");
        out.println("\tpublic " + className + "(long ptr){");
        out.println("\t\tsuper(ptr);");
        out.println("\t}");
    }

}
