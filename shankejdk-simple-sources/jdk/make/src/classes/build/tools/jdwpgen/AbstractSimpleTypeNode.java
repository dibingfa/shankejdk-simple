/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;

abstract class AbstractSimpleTypeNode extends AbstractTypeNode {

    void constrain(Context ctx) {
        context = ctx;
        nameNode.constrain(ctx);
        if (components.size() != 0) {
            error("Extraneous content: " + components.get(0));
        }
    }
}
