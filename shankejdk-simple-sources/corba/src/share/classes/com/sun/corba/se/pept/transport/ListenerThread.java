/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.pept.transport;

/**
 * @author Harold Carr
 */
public interface ListenerThread
{
    public Acceptor getAcceptor();
    public void close();
}

// End of file.
