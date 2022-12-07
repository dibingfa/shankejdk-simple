/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import java.util.Map;

import com.apple.internal.jobjc.generator.model.Clazz;

/*
 * Isolating all the reflection trickery to hijack the runtime into giving up its secrets
 * without actually having a full working version of JObjC. Below is a bunch of evil reflection,
 * but it allows the generated output to have a cleaner design.
 */
public class SuperClassExtractor {
    public static Clazz getSuperClassFor(final String className, final MacOSXFramework nativeFramework, final Map<String, Clazz> allClasses) throws Throwable {
        final NSClass<ID> nativeClass = new NSClass<ID>(className, nativeFramework.getRuntime());
        final NSClass<? extends ID> nativeSuperClass = UnsafeRuntimeAccess.getSuperClass(nativeClass);
        final String superClassName = UnsafeRuntimeAccess.getClassNameFor(nativeSuperClass);
        if ("nil".equals(superClassName)) return null;

        final Clazz superClazz = allClasses.get(superClassName);
        if (superClazz != null) return superClazz;

        final Clazz superClazzX = getSuperClassFor(superClassName, nativeFramework, allClasses);
        System.out.print("[Warning] class \"" + superClassName + "\" not found in bridge support files, ");
        System.out.println("using \"" + superClazzX.name + "\" as superclass for \"" + className + "\"");
        return superClazzX;
    }
}
