/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/*
 * @(#)Header.java    1.3 02/03/27
 */



package com.sun.xml.internal.messaging.saaj.packaging.mime;


/**
 * The Header class stores a name/value pair to represent headers.
 *
 * @author John Mani
 */

public interface Header {

    /**
     * Returns the name of this header.
     *
     * @return          name of the header
     */
    String getName();

    /**
     * Returns the value of this header.
     *
     * @return          value of the header
     */
    String getValue();
}
