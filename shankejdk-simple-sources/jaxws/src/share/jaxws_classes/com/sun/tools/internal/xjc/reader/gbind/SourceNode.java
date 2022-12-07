/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.xjc.reader.gbind;

/**
 * Source node of a graph.
 * @author Kohsuke Kawaguchi
 */
public final class SourceNode extends Element {
    public String toString() {
        return "#source";
    }

    @Override
    boolean isSource() {
        return true;
    }
}
