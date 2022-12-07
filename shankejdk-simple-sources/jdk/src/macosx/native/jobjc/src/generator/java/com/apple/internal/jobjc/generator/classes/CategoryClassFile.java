/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.PrintStream;

import com.apple.internal.jobjc.generator.model.Category;
import com.apple.jobjc.JObjCRuntime;

public class CategoryClassFile extends JObjCClassFile {
    private final Category category;

    public CategoryClassFile(final Category category) {
        super(category.category);
        this.category = category;
    }

    @Override public void writeBeginning(final PrintStream out) {
        String targetCls = category.category.superClass.getFullPath();
        out.format("\tpublic %1$s(final %2$s obj, final %3$s runtime) {\n" +
                "\t\tsuper(obj, runtime);\n" +
                "\t}\n",
            className, targetCls, JObjCRuntime.class.getCanonicalName());
        super.writeBeginning(out);
    }
}
