/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.jmx.remote.internal;

import java.io.IOException;
import java.rmi.MarshalledObject;

public interface Unmarshal {
    public Object get(MarshalledObject<?> mo)
            throws IOException, ClassNotFoundException;
}
