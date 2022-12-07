/*
 * Copyright (c) 1998, 2007, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.hotspot.igv.coordinator.actions;

import org.openide.nodes.Node;

/**
 *
 * @author Thomas Wuerthinger
 */
public interface RemoveCookie extends Node.Cookie {
        void remove();
}
