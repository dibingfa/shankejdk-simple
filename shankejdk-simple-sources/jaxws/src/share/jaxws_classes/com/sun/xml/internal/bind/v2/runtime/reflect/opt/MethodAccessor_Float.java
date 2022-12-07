/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.bind.v2.runtime.reflect.opt;

import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;

/**
 * Template {@link Accessor} for boolean getter/setter.
 * <p><b>
 *     Auto-generated, do not edit.
 * </b></p>
 * <p>
 *     All the MethodAccessors are generated from <code>MethodAccessor_B y t e</code>
 * </p>
 * @author Kohsuke Kawaguchi
 */
public class MethodAccessor_Float extends Accessor {
    public MethodAccessor_Float() {
        super(Float.class);
    }

    public Object get(Object bean) {
        return ((Bean)bean).get_float();
    }

    public void set(Object bean, Object value) {
        ((Bean)bean).set_float( value==null ? Const.default_value_float : (Float)value );
    }
}
