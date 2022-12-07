/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.tools.nasgen;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * A visitor that does nothing on visitXXX calls.
 *
 */
public class NullVisitor extends ClassVisitor {
    NullVisitor() {
        super(Opcodes.ASM4);
    }

    @Override
    public MethodVisitor visitMethod(
        final int access,
        final String name,
        final String desc,
        final String signature,
        final String[] exceptions) {
        return new MethodVisitor(Opcodes.ASM4) {
            @Override
            public AnnotationVisitor visitAnnotationDefault() {
                return new NullAnnotationVisitor();
            }

            @Override
            public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
                return new NullAnnotationVisitor();
            }
        };
    }

    @Override
    public FieldVisitor visitField(
        final int access,
        final String name,
        final String desc,
        final String signature,
        final Object value) {
        return new FieldVisitor(Opcodes.ASM4) {
            @Override
            public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
                return new NullAnnotationVisitor();
            }
        };
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        return new NullAnnotationVisitor();
    }

    private static class NullAnnotationVisitor extends AnnotationVisitor {
        NullAnnotationVisitor() {
            super(Opcodes.ASM4);
        }
    }
}
