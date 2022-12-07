/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.pept.transport;

/**
 * @author Harold Carr
 */
public interface ReaderThread {
    public Connection getConnection();
    public void close();
}

// End of file.
