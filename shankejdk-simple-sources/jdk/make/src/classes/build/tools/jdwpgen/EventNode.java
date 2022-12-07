/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class EventNode extends ReplyNode {

    void constrain(Context ctx) {
        super.constrain(ctx.inEventSubcontext());
    }
}
