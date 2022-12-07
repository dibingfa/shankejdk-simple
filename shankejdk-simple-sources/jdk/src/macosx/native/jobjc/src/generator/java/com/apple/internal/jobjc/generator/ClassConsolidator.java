/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apple.internal.jobjc.generator.model.Category;
import com.apple.internal.jobjc.generator.model.Clazz;
import com.apple.internal.jobjc.generator.model.Framework;

public class ClassConsolidator {
    private static String[] PREFERRED_FRAMEWORKS = { "Foundation", "AppKit" };

    static void consolidateClassesForFrameworks(final List<Framework> frameworks) throws Throwable {
        System.out.println("--2-- Resolving duplicate classes:");
        final Map<String, List<Clazz>> allClasses = new HashMap<String, List<Clazz>>();

        for (final Framework framework : frameworks) {
            for (final Clazz clazz : framework.classes) {
                final List<Clazz> existingClazzList = allClasses.get(clazz.name);
                if(existingClazzList != null)
                    existingClazzList.add(clazz);
                else
                    allClasses.put(clazz.name, new ArrayList<Clazz>(Arrays.asList(clazz)));
            }
        }

        final Map<String, Clazz> filteredClasses = new HashMap<String, Clazz>();
        final List<List<Clazz>> dreggs = new ArrayList<List<Clazz>>();

        final Collection<List<Clazz>> clazzLists = allClasses.values();
        for (final List<Clazz> clazzList : clazzLists) {
            if (clazzList.size() > 1) {
                // add to the list for later analysis
                dreggs.add(clazzList);
                continue;
            }

            // if there is only one class definition, go with it!
            final Clazz clazz = clazzList.get(0);
            filteredClasses.put(clazz.name, clazz);
        }

        // figure out which class is the real class, and convert the rest to categories
        for (final List<Clazz> dreg : dreggs)
            deriveCategoriesFrom(dreg, filteredClasses);

        // patch up the inheritance hierarchy
        System.out.println("Determining super classes:");
        for (final Framework framework : frameworks)
            framework.resolveSuperClasses(filteredClasses);
    }

    private static void deriveCategoriesFrom(final List<Clazz> clazzes, final Map<String, Clazz> filteredClasses) {
        final List<Clazz> clazzesToDerive = new ArrayList<Clazz>(clazzes);

        for (final String preferredFrameworkName : PREFERRED_FRAMEWORKS) {
            for (final Clazz clazz : clazzesToDerive) {
                if (!preferredFrameworkName.equals(clazz.parent.name)) continue;

                System.out.print("\t" + clazz.parent.name + " owns \"" + clazz.name + "\", ");
                addCategoriesAndPatchClasses(clazzes, clazz);
                filteredClasses.put(clazz.name, clazz);
                return;
            }
        }

        final List<String> frameworkNameList = new ArrayList<String>(clazzes.size());
        for (final Clazz clazz : clazzes) frameworkNameList.add(clazz.parent.name);
        throw new RuntimeException("Could not derived a preferred framework for: " + clazzes.get(0).name + ", from (" + Utils.joinWComma(frameworkNameList) + ")");
    }

    private static void addCategoriesAndPatchClasses(final List<Clazz> clazzes, final Clazz clazz) {
        final List<String> fwNames = new ArrayList<String>(clazzes.size());

        for (final Clazz cls : clazzes) {
            if (cls == clazz) continue;
            fwNames.add(cls.parent.name);
            cls.parent.classes.remove(cls);
            cls.parent.categories.add(new Category(cls, clazz));
        }

        System.out.println("creating categories in: " + Utils.joinWComma(fwNames));
    }
}
