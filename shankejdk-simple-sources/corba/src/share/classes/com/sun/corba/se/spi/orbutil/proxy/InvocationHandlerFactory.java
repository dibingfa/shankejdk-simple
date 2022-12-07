/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.corba.se.spi.orbutil.proxy ;

import java.lang.reflect.InvocationHandler ;

public interface InvocationHandlerFactory
{
    /** Get an InvocationHandler.
     */
    InvocationHandler getInvocationHandler() ;

    /** Get the interfaces that InvocationHandler instances
     * produced by this InvocationHandlerFactory support.
     */
    Class[] getProxyInterfaces() ;
}
