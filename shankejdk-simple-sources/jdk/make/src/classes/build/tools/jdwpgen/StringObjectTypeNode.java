/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class StringObjectTypeNode extends ObjectTypeNode {

    String docType() {
        return "stringID";
    }

    String javaType() {
        return "StringReferenceImpl";
    }

    String javaRead() {
        return "ps.readStringReference()";
    }
}
