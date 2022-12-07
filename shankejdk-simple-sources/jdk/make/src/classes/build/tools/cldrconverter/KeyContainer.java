/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

/**
 * A container that provides a key for contained elements.
 * This container does not provide a value.
 */
class KeyContainer extends Container {
    private String key;

    KeyContainer(String qName, Container parent, String key) {
        super(qName, parent);
        this.key = key;
    }

    String getKey() {
        return key;
    }

}
