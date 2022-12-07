/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.pept.transport;

/**
 * @author Harold Carr
 */
public interface OutboundConnectionCache
    extends ConnectionCache
{
    public Connection get(ContactInfo contactInfo);

    public void put(ContactInfo contactInfo, Connection connection);

    public void remove(ContactInfo contactInfo);
}

// End of file.
