/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.PrintStream;
import java.util.Collection;

import com.apple.internal.jobjc.generator.ClassGenerator;
import com.apple.internal.jobjc.generator.model.coders.ComplexCoderDescriptor.MixedEncodingDescriptor;
import com.apple.jobjc.PrimitiveCoder;

public class MixedPrimitiveCoderClassFile extends GeneratedClassFile {
    private static final String MULTI_CODER_CLASSNAME = "MixedPrimitiveCoder";
    public static final String FULL_MULTI_CODER_CLASSNAME = ClassGenerator.JOBJC_PACKAGE + "." + MULTI_CODER_CLASSNAME;

    final Collection<MixedEncodingDescriptor> coderDescs;

    public MixedPrimitiveCoderClassFile(final Collection<MixedEncodingDescriptor> coderDescs) {
        super(ClassGenerator.JOBJC_PACKAGE, MULTI_CODER_CLASSNAME, "java.lang.Object");
        this.coderDescs = coderDescs;
    }

    @Override
    public void writeBody(final PrintStream out) {
        for (final MixedEncodingDescriptor desc : coderDescs) {
            out.println("\tpublic static final " + PrimitiveCoder.class.getCanonicalName() + " " + desc.getMixedName() + " = " + desc.getDefinition() + ";");
        }
    }
}
