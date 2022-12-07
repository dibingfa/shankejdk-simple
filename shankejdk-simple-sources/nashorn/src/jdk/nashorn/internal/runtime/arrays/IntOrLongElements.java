/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved.
 */
package jdk.nashorn.internal.runtime.arrays;

/**
 * Marker interface for any ContinuousArray with int or long elements
 * Used for type checks that throw ClassCastExceptions and force relinks
 * for fast NativeArray specializations of builtin methods
 */
public interface IntOrLongElements extends NumericElements {
    //empty
}
