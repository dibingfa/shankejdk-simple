/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class ClassTypeNode extends ReferenceTypeNode {

    String docType() {
        return "classID";
    }

    String javaType() {
        return "ClassTypeImpl";
    }

    String javaRead() {
        return "vm.classType(ps.readClassRef())";
    }
}
