/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class ThreadGroupObjectTypeNode extends ObjectTypeNode {

    String docType() {
        return "threadGroupID";
    }

    String javaType() {
        return "ThreadGroupReferenceImpl";
    }

    String javaRead() {
        return "ps.readThreadGroupReference()";
    }
}
