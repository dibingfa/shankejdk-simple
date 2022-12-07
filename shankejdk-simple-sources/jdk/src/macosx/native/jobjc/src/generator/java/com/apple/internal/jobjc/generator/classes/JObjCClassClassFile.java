/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import com.apple.internal.jobjc.generator.ClassGenerator;
import com.apple.internal.jobjc.generator.FunctionGenerator;
import com.apple.internal.jobjc.generator.model.Clazz;
import com.apple.internal.jobjc.generator.model.Method;
import com.apple.jobjc.JObjCRuntime;
import com.apple.jobjc.Invoke.MsgSend;

public class JObjCClassClassFile extends AbstractObjCClassFile {
    public JObjCClassClassFile(final Clazz clazz) {
        super(clazz, clazz.name + "Class", clazz.superClass != null ? clazz.superClass.getFullPath() + "Class" : ClassGenerator.JOBJC_PACKAGE + ".NSClass<" + clazz.name + ">");
    }

    @Override public void writeBeginning(final PrintStream out) {
        out.format(
                "\tpublic %1$s(%2$s runtime) {\n" +
                "\t\tsuper(runtime);\n" +
                "\t}\n",
                className, JObjCRuntime.class.getCanonicalName());
        out.format(
                "\tpublic %1$s(String name, %2$s runtime) {\n" +
                "\t\tsuper(name, runtime);\n" +
                "\t}\n",
                className, JObjCRuntime.class.getCanonicalName());
        out.format(
                "\tpublic %1$s(long ptr, %2$s runtime) {\n" +
                "\t\tsuper(ptr, runtime);\n" +
                "\t}\n",
                className, JObjCRuntime.class.getCanonicalName());
    }

    @Override public void writeBody(final PrintStream out) {
        Set<String> written = new HashSet<String>();
        for(final Method method : this.clazz.classMethods)
            if(written.add(method.name))
                FunctionGenerator.writeOutFunction(out, MsgSend.class, method, "this");
            else
                System.out.format("Duplicate method: %1$s %2$s -%3$s\n", clazz.parent.name, className, method.name);
    }
}
