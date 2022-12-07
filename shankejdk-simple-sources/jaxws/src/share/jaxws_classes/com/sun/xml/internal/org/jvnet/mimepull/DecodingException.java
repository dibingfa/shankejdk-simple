/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/* FROM mail.jar */
package com.sun.xml.internal.org.jvnet.mimepull;

import java.io.IOException;

/**
 * A special IOException that indicates a failure to decode data due
 * to an error in the formatting of the data.  This allows applications
 * to distinguish decoding errors from other I/O errors.
 *
 * @author Bill Shannon
 */

public final class DecodingException extends IOException {

    /**
     * Constructor
     */
    public DecodingException(String s) {
        super(s);
    }
}
