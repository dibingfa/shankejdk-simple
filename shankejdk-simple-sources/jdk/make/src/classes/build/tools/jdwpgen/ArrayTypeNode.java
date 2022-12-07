/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class ArrayTypeNode extends ReferenceTypeNode {

    String docType() {
        return "arrayTypeID";
    }

    String javaType() {
        return "ArrayTypeImpl";
    }

    String javaRead() {
        return "--- should not get generated ---";
    }
}
