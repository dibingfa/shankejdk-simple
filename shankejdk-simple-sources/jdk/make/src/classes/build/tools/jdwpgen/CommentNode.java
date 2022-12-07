/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.jdwpgen;

import java.util.*;
import java.io.*;

class CommentNode extends AbstractSimpleNode {

    String text;

    CommentNode(String text) {
        this.text = text;
    }

    String text() {
        return text;
    }
}
