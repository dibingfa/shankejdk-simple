/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator;

import java.util.List;

import com.apple.internal.jobjc.generator.model.Clazz;
import com.apple.internal.jobjc.generator.model.Framework;
import com.apple.internal.jobjc.generator.model.Function;
import com.apple.internal.jobjc.generator.model.types.TypeCache;

public class MethodDisambiguator {
    static void disambiguateMethodNames() {
        // link all subclassers off their parents
        for (final Clazz clazz : TypeCache.inst().getAllClasses()) {
            final Clazz superClazz = clazz.superClass;
            if (superClazz != null) superClazz.subClassers.add(clazz);
        }

        // recursively call all subclassers, starting from NSObject on down
        disambiguateMethodNamesFor(TypeCache.inst().getClassForName("NSObject"));

        // NSProxy does not appear to subclass from NSObject, but it is still a real full class...?
        disambiguateMethodNamesFor(TypeCache.inst().getClassForName("NSProxy"));
    }

    static void disambiguateMethodNamesFor(final Clazz clazz) {
        clazz.disambiguateMethods();
        for (final Clazz subClazz : clazz.subClassers) {
            disambiguateMethodNamesFor(subClazz);
        }
    }

    public static void disambiguateFunctionsIn(final List<Framework> frameworks) {
        for (final Framework framework : frameworks) {
            disambiguateFunctionsInFramework(framework);
        }
    }

    static void disambiguateFunctionsInFramework(final Framework framework) {
        for (final Function fxn : framework.functions)
            fxn.disambiguateArgs();
    }
}
