/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.ior;

public interface IORTypeCheckRegistry {
    public boolean isValidIORType(String iorClassName);
}

