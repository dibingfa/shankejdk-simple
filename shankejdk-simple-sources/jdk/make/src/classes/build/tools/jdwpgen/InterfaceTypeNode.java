/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class InterfaceTypeNode extends ReferenceTypeNode {

    String docType() {
        return "interfaceID";
    }

    String javaType() {
        return "InterfaceTypeImpl";
    }

    String javaRead() {
        return "vm.interfaceType(ps.readClassRef())";
    }
}
