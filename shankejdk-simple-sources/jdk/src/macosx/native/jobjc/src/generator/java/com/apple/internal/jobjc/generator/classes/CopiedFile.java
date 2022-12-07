/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.classes;

import java.io.*;

public class CopiedFile extends OutputFile {
    final File sourceFile;

    public CopiedFile(final File sourceFile, final String pkg, final String filename) {
        super(pkg, filename);
        this.sourceFile = sourceFile;
    }

    @Override
    public void write(final File parentDir) {
        try {
            final PrintStream out = open(parentDir);
            final InputStream in = new FileInputStream(sourceFile);

            copy(in, out);
            close(out);
        } catch (final IOException e) { throw new RuntimeException(e); }
    }

    private static void copy(final InputStream in, final PrintStream out) throws IOException {
        int bit;
        while (-1 != (bit = in.read())) {
            out.write(bit);
        }
    }
}
