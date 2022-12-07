/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class ClassObjectTypeNode extends ObjectTypeNode {

    String docType() {
        return "classObjectID";
    }

    String javaType() {
        return "ClassObjectReferenceImpl";
    }

    String javaRead() {
        return "ps.readClassObjectReference()";
    }
}
