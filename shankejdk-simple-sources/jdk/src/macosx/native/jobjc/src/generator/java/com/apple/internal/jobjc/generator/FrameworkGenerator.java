/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.apple.internal.jobjc.generator.model.Framework;
import com.apple.internal.jobjc.generator.model.Framework.FrameworkDependency;
import com.apple.internal.jobjc.generator.utils.Fp;
import com.apple.internal.jobjc.generator.utils.StructOffsetResolverBigBang;
import com.apple.jobjc.JObjCRuntime;

public class FrameworkGenerator {
    private static final String BRIDGESUPPORT_FILE_EXTENSION = "Full.bridgesupport";
    private static final String FRAMEWORK_MATCH = "^.*Full\\.bridgesupport$";
    private static final String FRAMEWORK_PRUNE = "^.*(PyObjC|/Versions|\\.lproj|/Headers|/PrivateHeaders).*$";

    static List<File> findFrameworkFilesIn(final File file) throws IOException{
        final List<File> bridgeSupportFiles = Utils.find(file, FRAMEWORK_MATCH, FRAMEWORK_PRUNE);
        System.out.println("found " + bridgeSupportFiles.size() + " frameworks");
        return bridgeSupportFiles;
    }

    static List<Framework> parseFrameworksFrom(final List<File> bridgeSupportFiles) {
        final List<Framework> frameworks = new ArrayList<Framework>();

        System.out.println("Parsing XML");
        for (final File file : bridgeSupportFiles){
            Framework f = new Framework(extractFrameworkNameFrom(file), file);
            try{
                f.load();
                frameworks.add(f);
                System.out.println("Generator@" + JObjCRuntime.ARCH + " loaded "
                        + f.name + " (" + Fp.join(":", f.binaries) + ")");
            }
            catch(Exception x){
                System.out.println("!! Generator@" + JObjCRuntime.ARCH + " failed to load "
                        + f.name + " (" + Fp.join(":", f.binaries) + "). SKIPPING");
            }
        }

        System.out.println("Parsing dependencies");
        for (final Framework f : frameworks) f.parseDependencies(frameworks);

        Set<String> alreadyWarnedDependency = new HashSet<String>();
        for(final Framework f : frameworks)
            for(final FrameworkDependency dep : f.dependencies)
                if(dep.object == null && !alreadyWarnedDependency.contains(dep.name)){
                    System.out.println(String.format("Warning: unresolved dependency: %1$30s -> %2$s", f.name, dep.name));
                    alreadyWarnedDependency.add(dep.name);
                }
        if(alreadyWarnedDependency.size() > 0)
            System.out.println("Unresolved dependencies lead to unresolved types.");

        Utils.topologicalSort(frameworks);
        List<Framework> cycle = Utils.getDependencyCycle(frameworks);
        if(cycle != null)
            System.out.println("Warning: cycle found in framework dependencies: " + Fp.join(" -> ", cycle));

        System.out.println("Parsing types");
        for (final Framework f : frameworks){
            f.parseCFTypes();
            f.parseOpaques();
        }
        for (final Framework f : frameworks) f.parseStructs();

        new StructOffsetResolverBigBang().resolve(frameworks);

        System.out.println("Parsing classes");
        for (final Framework f : frameworks) f.parseClasses();

        System.out.println("Parsing constants");
        for (final Framework f : frameworks) f.parseConstants();

        System.out.println("Parsing functions");
        for (final Framework f : frameworks) f.parseFunctions();

        return frameworks;
    }

    private static String extractFrameworkNameFrom(final File file) {
        final String fileName = file.getName();
        return fileName.substring(0, fileName.lastIndexOf(BRIDGESUPPORT_FILE_EXTENSION));
    }
}
