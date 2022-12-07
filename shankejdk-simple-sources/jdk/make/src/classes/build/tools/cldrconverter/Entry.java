/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

abstract class Entry<T> extends Container {
    private String key;

    Entry(String qName, Container parent, String key) {
        super(qName, parent);
        this.key = key;
    }

    String getKey() {
        return key;
    }

    abstract T getValue();

}
