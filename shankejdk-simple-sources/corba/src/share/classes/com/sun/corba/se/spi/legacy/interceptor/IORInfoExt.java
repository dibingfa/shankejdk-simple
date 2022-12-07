/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.legacy.interceptor;

import com.sun.corba.se.spi.oa.ObjectAdapter;

public interface IORInfoExt
{
    public int getServerPort(String type)
        throws
            UnknownType;

    public ObjectAdapter getObjectAdapter();
}

// End of file.
