/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator;

import java.util.*;

import com.apple.internal.jobjc.generator.classes.*;
import com.apple.internal.jobjc.generator.model.Framework;

public class ClassGenerator {
    public static final String JOBJC_PACKAGE = "com.apple.jobjc";

    public static List<OutputFile> generateClasses(final List<Framework> frameworks) {
        final List<OutputFile> generatedClassFiles = new ArrayList<OutputFile>();

        generatedClassFiles.add(new RootJObjCClass(frameworks));
        for (final Framework f : frameworks) {
            f.generateClasses(generatedClassFiles);
        }

        return generatedClassFiles;
    }
}
