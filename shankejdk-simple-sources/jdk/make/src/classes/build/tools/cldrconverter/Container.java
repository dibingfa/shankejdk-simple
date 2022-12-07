/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

class Container {
    private final String qName;
    private final Container parent;

    Container(String qName, Container parent) {
        this.qName = qName;
        this.parent = parent;
    }

    void addCharacters(char[] characters, int start, int length) {
        if (getParent() != null) {
            getParent().addCharacters(characters, start, length);
        }
    }

    Container getParent() {
        return parent;
    }

    /**
     * @return the qName
     */
    String getqName() {
        return qName;
    }

}
