/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.iiop.IIOPAddress ;
import com.sun.corba.se.spi.ior.TaggedComponent ;

/**
 * @author Ken Cavanaugh
 */
public interface AlternateIIOPAddressComponent extends TaggedComponent
{
    public IIOPAddress getAddress() ;
}
