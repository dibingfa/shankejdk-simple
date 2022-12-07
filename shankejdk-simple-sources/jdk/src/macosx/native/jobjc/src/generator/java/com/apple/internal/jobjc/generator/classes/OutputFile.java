/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.*;

public abstract class OutputFile {
    final String pkg;
    final String fileName;

    public OutputFile(final String pkg, final String filename) {
        this.pkg = pkg;
        this.fileName = filename;
    }

    public PrintStream open(final File parentDir) throws IOException {
        final File pkgDir = new File(parentDir, pkg.replace('.', '/'));
        pkgDir.mkdirs();
        final File classFile = new File(pkgDir, fileName);
        classFile.createNewFile();
        return new PrintStream(classFile);
    }

    public void close(final PrintStream out) {
        out.close();
    }

    public abstract void write(final File parentDir);

    public boolean isClass(final Class<?> clazz) {
        final String pkgName = clazz.getPackage().getName();
        if (!pkgName.equals(pkg)) return false;

        return fileName.contains(clazz.getSimpleName());
    }
}
