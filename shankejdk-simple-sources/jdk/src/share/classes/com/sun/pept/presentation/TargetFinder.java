/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
 */

/** Java interface "TargetFinder.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package com.sun.pept.presentation;

import com.sun.pept.ept.MessageInfo;
import java.util.*;

/**
 * <p>
 *
 * @author Dr. Harold Carr
 * </p>
 */
public interface TargetFinder {

  ///////////////////////////////////////
  // operations

/**
 * <p>
 * Does ...
 * </p><p>
 *
 * @return a Tie with ...
 * </p><p>
 * @param x ...
 * </p>
 */
    public Tie findTarget(MessageInfo x);

} // end TargetFinder
