/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.codegen.types;

/**
 * This is a numeric type, i.e. NUMBER, LONG, INT, INT32.
 */
public abstract class NumericType extends Type implements BytecodeNumericOps {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param name   name of type
     * @param clazz  Java class used to represent type
     * @param weight weight of type
     * @param slots  number of bytecode slots this type takes up
     */
    protected NumericType(final String name, final Class<?> clazz, final int weight, final int slots) {
        super(name, clazz, weight, slots);
    }
}
