/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class TaggedObjectTypeNode extends ObjectTypeNode {

    String docType() {
        return "tagged-objectID";
    }

    public void genJavaWrite(PrintWriter writer, int depth,
                             String writeLabel) {
        error("Why write a tagged-object?");
    }

    String javaRead() {
        return "ps.readTaggedObjectReference()";
    }
}
