/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
 */

package javax.swing.plaf;

import javax.swing.InputMap;


/**
 * A subclass of javax.swing.InputMap that implements UIResource.
 * UI classes which provide a InputMap should use this class.
 *
 * @author Scott Violet
 * @since 1.3
 */
public class InputMapUIResource extends InputMap implements UIResource {
    public InputMapUIResource() {
    }
}
