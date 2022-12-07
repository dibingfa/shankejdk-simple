/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.doclets.formats.html.markup;

import com.sun.tools.javac.util.StringUtils;

/**
 * Enum representing HTML tag attributes.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 *
 * @author Bhavesh Patel
 */
public enum HtmlAttr {
    ALT,
    BORDER,
    CELLPADDING,
    CELLSPACING,
    CLASS,
    CLEAR,
    COLS,
    CONTENT,
    HREF,
    HTTP_EQUIV("http-equiv"),
    ID,
    LANG,
    NAME,
    ONLOAD,
    REL,
    ROWS,
    SCOPE,
    SCROLLING,
    SRC,
    SUMMARY,
    TARGET,
    TITLE,
    TYPE,
    WIDTH;

    private final String value;

    HtmlAttr() {
        this.value = StringUtils.toLowerCase(name());
    }

    HtmlAttr(String name) {
        this.value = name;
    }

    public String toString() {
        return value;
    }
}
