/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.istack.internal;

import org.xml.sax.SAXException;

/**
 * {@link SAXException} that handles exception chaining correctly.
 *
 * @author Kohsuke Kawaguchi
 * @since 2.0 FCS
 */
public class SAXException2 extends SAXException {
    public SAXException2(String message) {
        super(message);
    }

    public SAXException2(Exception e) {
        super(e);
    }

    public SAXException2(String message, Exception e) {
        super(message, e);
    }

    public Throwable getCause() {
        return getException();
    }
}
