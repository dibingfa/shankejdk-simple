/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator;

import java.io.File;
import java.util.*;

import com.apple.internal.jobjc.generator.classes.*;

public class FileCopier {
    public static List<OutputFile> addSourceFilesFrom(final String srcPath) {
        final List<OutputFile> outputFileList = new ArrayList<OutputFile>();

        final List<File> fileList = getFileList(srcPath);
        for (final File file : fileList) {
            outputFileList.add(new CopiedFile(file, ClassGenerator.JOBJC_PACKAGE, file.getName().replace("\\.java", "")));
        }

        return outputFileList;
    }

    private static List<File> getFileList(final String srcPath) {
        final File srcRoot = new File(srcPath);
        if (!srcRoot.exists()) throw new RuntimeException("Source root " + srcRoot + " does not exist. Nowhere to copy base runtime objects from.");

        final File targetDir = new File(srcRoot, ClassGenerator.JOBJC_PACKAGE.replaceAll("\\.", "\\/"));
        if (!targetDir.exists() || !targetDir.isDirectory()) throw new RuntimeException("Base runtime object source directory " + targetDir + " does not exist. No runtime class files to copy.");

        final List<File> fileList = new ArrayList<File>();
        final File[] targetDirFileList = targetDir.listFiles();
        for (final File file : targetDirFileList) {
            if (!file.isFile()) continue;
            if (!file.getName().endsWith(".java")) continue;
            fileList.add(file);
        }

        return fileList;
    }
}
