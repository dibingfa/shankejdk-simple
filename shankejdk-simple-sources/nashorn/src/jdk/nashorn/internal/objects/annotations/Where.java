/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.objects.annotations;

/**
 * Enum to tell where a Function or Property belongs.
 */
public enum Where {
    /** this means that the item belongs in the Constructor of an object */
    CONSTRUCTOR,
    /** this means that the item belongs in the Prototype of an object */
    PROTOTYPE,
    /** this means that the item belongs in the Instance of an object */
    INSTANCE
}
