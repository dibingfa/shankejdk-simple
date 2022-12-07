/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

class StringEntry extends Entry<String> {
    private String value;

    StringEntry(String qName, Container parent, String key) {
        super(qName, parent, key);
    }

    StringEntry(String qName, Container parent, String key, String value) {
        super(qName, parent, key);
        this.value = value;
    }

    @Override
    void addCharacters(char[] characters, int start, int length) {
        String s = new String(characters, start, length);
        if (value != null) {
            value += s;
        } else {
            value = s;
        }
    }

    @Override
    String getValue() {
        return value;
    }
}
