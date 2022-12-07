/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
 */

/**
 * $Id: Interceptors.java,v 1.1 2005/05/23 22:09:18 bbissett Exp $
 */

/** Java interface "Interceptors.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package com.sun.pept.protocol;

import com.sun.pept.ept.MessageInfo;
import java.util.*;

/**
 * <p>
 *
 * @author Dr. Harold Carr
 * </p>
 */
public interface Interceptors {

  ///////////////////////////////////////
  // operations

/**
 * <p>
 * Does ...
 * </p><p>
 *
 * </p><p>
 *
 * @param messageInfo ...
 * </p>
 */
    public void interceptMessage(MessageInfo messageInfo);

} // end Interceptors
