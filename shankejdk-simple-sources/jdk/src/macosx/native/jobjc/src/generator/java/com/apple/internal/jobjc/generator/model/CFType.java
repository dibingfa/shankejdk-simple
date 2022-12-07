/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.util.List;

import org.w3c.dom.Node;

import com.apple.internal.jobjc.generator.classes.CFTypeClassFile;
import com.apple.internal.jobjc.generator.classes.OutputFile;

public class CFType extends TypeElement<Framework> implements OutputFileGenerator {
    public final String getTypeIdFunc;
    public final String tollfree;

    public CFType(final Node node, final Framework parent) {
        super(node, getAttr(node, "name"), parent);
        getTypeIdFunc = getAttr(node, "gettypeid_func");
        tollfree = getAttr(node, "tollfree");
    }

    public void generateClasses(final List<OutputFile> generatedClassFiles) {
        generatedClassFiles.add(new CFTypeClassFile(this));
    }
}
