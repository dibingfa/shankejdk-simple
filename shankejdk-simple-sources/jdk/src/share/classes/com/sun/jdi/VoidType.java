/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.jdi;

/**
 * The type of all primitive <code>void</code> values
 * accessed in the target VM. Calls to {@link Value#type} will return an
 * implementor of this interface.
 *
 * @see VoidValue
 *
 * @author Robert Field
 * @since  1.3
 */
@jdk.Exported
public interface VoidType extends Type {
}
