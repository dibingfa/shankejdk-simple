/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.source.doctree;

/**
 * A tree node used as the base class for the different types of
 * block tags.
 *
 * @since 1.8
 */
@jdk.Exported
public interface BlockTagTree extends DocTree {
    String getTagName();
}
