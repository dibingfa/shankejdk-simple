/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

class ThreadObjectTypeNode extends ObjectTypeNode {

    String docType() {
        return "threadID";
    }

    String javaType() {
        return "ThreadReferenceImpl";
    }

    String javaRead() {
        return "ps.readThreadReference()";
    }
}
