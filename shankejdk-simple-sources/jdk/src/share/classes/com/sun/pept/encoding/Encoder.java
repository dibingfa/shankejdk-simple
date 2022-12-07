/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
 */

/** Java interface "Encoder.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package com.sun.pept.encoding;

import com.sun.pept.ept.MessageInfo;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * <p>
 *
 * @author Arun Gupta
 * </p>
 */
public interface Encoder {

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
    public void encodeAndSend(MessageInfo messageInfo);
/**
 * <p>
 * Does ...
 * </p><p>
 *
 * @return a ByteBuffer with ...
 * </p><p>
 * @param messageInfo ...
 * </p>
 */
    public ByteBuffer encode(MessageInfo messageInfo);

} // end Encoder
