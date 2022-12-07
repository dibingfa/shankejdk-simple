/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.util.List;

import com.apple.internal.jobjc.generator.classes.CategoryClassClassFile;
import com.apple.internal.jobjc.generator.classes.CategoryClassFile;
import com.apple.internal.jobjc.generator.classes.OutputFile;

public class Category implements OutputFileGenerator{
    public final Clazz category;

    public Category(Clazz fromClass, Clazz target) {
        this.category = new Clazz(fromClass.name + "Category", fromClass.classMethods, fromClass.instanceMethods, target, fromClass.parent);
    }

    public void generateClasses(List<OutputFile> generatedClassFiles) {
        generatedClassFiles.add(new CategoryClassFile(this));
        generatedClassFiles.add(new CategoryClassClassFile(this));
    }
}
