/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;

import com.apple.internal.jobjc.generator.model.Framework;
import com.apple.internal.jobjc.generator.utils.Fp.Map1;
import com.apple.jobjc.JObjCRuntime.Width;

/**
 * Takes a framework, compiles a native source file with all its structs,
 * and figures out their sizes and field offsets.
 *
 * BigBang significantly speeds up the process by
 * compiling all frameworks as one big Objective-C file.
 */
public class StructOffsetResolverBigBang extends StructOffsetResolver{

    @Override protected void _resolve(final Collection<Framework> fws) throws Exception{
        for(final Width arch : Width.values()){
            System.out.println("SORBB -- Getting Struct offsets @" + arch.toString());
            String nativeSrc = generateFileForFrameworks(fws, arch);
            String executable = compileObjC(nativeSrc, arch);
            execute(executable, new Map1<String,Object>(){
                public Object apply(String ln) {
                    try {
                        processLine(ln, fws, arch);
                        return null;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * Generates Objective-C file and returns absolute path name.
     */
    protected String generateFileForFrameworks(final Collection<Framework> fws, final Width arch) throws Exception{
        File tempfile = File.createTempFile("JObjC-SORBB-" + arch + "-", ".mm");
        PrintWriter out = new PrintWriter(new FileWriter(tempfile));

        out.println("#include<iostream>");
        for(Framework fw : fws) printHeaderLines(fw, arch, out);
        out.println("int main(int argc, char** argv){");
        for(Framework fw : fws) printStructInfos(fw, arch, out);
        out.println("\treturn 0;");
        out.println("}");

        out.close();
        return tempfile.getAbsolutePath();
    }
}
