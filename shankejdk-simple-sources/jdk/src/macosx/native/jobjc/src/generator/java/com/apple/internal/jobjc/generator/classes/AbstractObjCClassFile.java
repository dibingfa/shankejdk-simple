/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import com.apple.internal.jobjc.generator.model.Clazz;

public abstract class AbstractObjCClassFile extends GeneratedClassFile {
    final Clazz clazz;

    public AbstractObjCClassFile(final Clazz clazz, final String classname, final String genericArgs, final String superClass) {
        super(clazz.getPackage(), classname, genericArgs, superClass);
        this.clazz = clazz;
    }

    public AbstractObjCClassFile(final Clazz clazz, final String classname, final String superClass) {
        super(clazz.getPackage(), classname, superClass);
        this.clazz = clazz;
    }
}
