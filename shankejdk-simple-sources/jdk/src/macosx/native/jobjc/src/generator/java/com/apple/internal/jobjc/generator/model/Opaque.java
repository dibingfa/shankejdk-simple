/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.util.List;

import org.w3c.dom.Node;

import com.apple.internal.jobjc.generator.classes.OpaqueClassFile;
import com.apple.internal.jobjc.generator.classes.OutputFile;

public class Opaque extends TypeElement<Framework> implements OutputFileGenerator {
    public Opaque(final Node node, final Framework parent) {
        super(node, getAttr(node, "name"), parent);
    }

    public void generateClasses(final List<OutputFile> generatedClassFiles) {
        generatedClassFiles.add(new OpaqueClassFile(this));
    }
}
