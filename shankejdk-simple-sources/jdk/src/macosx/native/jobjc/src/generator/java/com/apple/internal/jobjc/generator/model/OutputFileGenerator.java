/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.util.List;

import com.apple.internal.jobjc.generator.classes.OutputFile;

public interface OutputFileGenerator {
    public void generateClasses(final List<OutputFile> generatedClassFiles);
}
