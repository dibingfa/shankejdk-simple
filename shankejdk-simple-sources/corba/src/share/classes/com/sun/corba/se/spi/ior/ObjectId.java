/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.ior;

/**
 * @author Ken Cavanaugh
 */
public interface ObjectId extends Writeable
{
    public byte[] getId() ;
}
