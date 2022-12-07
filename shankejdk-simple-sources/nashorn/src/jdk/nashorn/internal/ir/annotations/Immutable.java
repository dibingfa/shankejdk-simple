/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.ir.annotations;

/**
 * Tag for nodes that are immutable. To be immutable all fields must be
 * final and copy on write semantics must be in place
 */
public @interface Immutable {
    //empty
}
