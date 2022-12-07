/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.PrintStream;

import com.apple.internal.jobjc.generator.model.CFType;
import com.apple.jobjc.Pointer;

public class CFTypeClassFile extends GeneratedClassFile {
    final CFType cftype;

    public CFTypeClassFile(final CFType cftype) {
        super(cftype.parent.pkg, cftype.type.getJType().getJavaClassName(), com.apple.jobjc.CFType.class.getCanonicalName());
        this.cftype = cftype;
    }

    @Override public void writeBeginning(PrintStream out){
        out.println("\tpublic " + className + "(" + Pointer.class.getName() + "<?> ptr){");
        out.println("\t\tsuper(ptr);");
        out.println("\t}");
        out.println("");
        out.println("\tpublic " + className + "(long ptr){");
        out.println("\t\tsuper(ptr);");
        out.println("\t}");
    }

    @Override public void writeBody(PrintStream out){
        if(cftype.getTypeIdFunc != null){
            out.println("\tpublic static long getTypeId(){");
            out.println("\t\treturn " + RootJObjCClass.runtimeFrameworkInst(cftype.parent.name) + "." + cftype.getTypeIdFunc + "();");
            out.println("\t}");
        }
        else
            out.println("\t// getTypeIdFunc not found");
    }
}
