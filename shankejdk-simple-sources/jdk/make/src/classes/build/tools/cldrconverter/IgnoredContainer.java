/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

class IgnoredContainer extends Container {

    IgnoredContainer(String qName, Container parent) {
            super(qName, parent);
    }

    @Override
    void addCharacters(char[] characters, int start, int length) {
        // ignore
    }

}
