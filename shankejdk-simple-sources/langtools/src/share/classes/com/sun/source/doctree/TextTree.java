/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.source.doctree;

/**
 * A tree node for plain text.
 *
 * @since 1.8
 */
@jdk.Exported
public interface TextTree extends DocTree {
    String getBody();
}
