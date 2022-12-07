/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;


import java.util.*;
import java.io.*;

interface TypeNode {

    String name();

    void genJavaWrite(PrintWriter writer, int depth,  String writeLabel);

    void genJavaRead(PrintWriter writer, int depth, String readLabel);

    void genJavaDeclaration(PrintWriter writer, int depth);

    String javaParam();
}
