/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class ClassLoaderObjectTypeNode extends ObjectTypeNode {

    String docType() {
        return "classLoaderID";
    }

    String javaType() {
        return "ClassLoaderReferenceImpl";
    }

    String javaRead() {
        return "ps.readClassLoaderReference()";
    }
}
