/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class ArrayObjectTypeNode extends ObjectTypeNode {

    String docType() {
        return "arrayID";
    }

    String javaType() {
        return "ArrayReferenceImpl";
    }

    String javaRead() {
        return "ps.readArrayReference()";
    }
}
