/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

class StringArrayElement extends Container {
    StringArrayEntry array;
    int index;

    StringArrayElement(String qName, Container parent, int index) {
        super(qName, parent);
        while (!(parent instanceof StringArrayEntry)) {
            parent = parent.getParent();
        }
        array = (StringArrayEntry) parent;
        this.index = index;
    }

    @Override
    void addCharacters(char[] characters, int start, int length) {
        array.addCharacters(index, characters, start, length);
    }

}
