/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import com.apple.internal.jobjc.generator.FunctionGenerator;
import com.apple.internal.jobjc.generator.model.Category;
import com.apple.internal.jobjc.generator.model.Method;
import com.apple.jobjc.JObjCRuntime;
import com.apple.jobjc.Invoke.MsgSend;

public class CategoryClassClassFile extends AbstractObjCClassFile {
    final Category category;

    public CategoryClassClassFile(final Category category) {
        super(category.category, category.category.name + "Class",
                category.category.superClass.getFullPath() + "Class");
        this.category = category;
    }

    @Override public void writeBeginning(final PrintStream out) {
        out.format(
                "\t%1$s(%2$s runtime) {\n" +
                "\t\tsuper(\"%3$s\", runtime);\n" +
                "\t}\n",
                className, JObjCRuntime.class.getCanonicalName(), category.category.superClass.name);
    }

    @Override public void writeBody(final PrintStream out) {
        Set<String> written = new HashSet<String>();
        for(final Method method : this.clazz.classMethods)
            if(written.add(method.name))
                FunctionGenerator.writeOutFunction(out, MsgSend.class, method, "this");
            else
                System.out.format("Duplicate method: %1$s %2$s -%3$s\n", clazz.parent.name, className, method.name);
    }

    @Override protected boolean isFinal(){ return true; }
}
