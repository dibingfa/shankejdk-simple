/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.wsdl;

import com.sun.xml.internal.ws.api.message.Message;

/**
 * {@link Exception} that demands a specific fault message to be sent back.
 *
 * TODO: think about a way to generalize it, as it seems to be useful
 * in other places.
 *
 * @author Kohsuke Kawaguchi
 */
public final class DispatchException extends Exception {
    public final Message fault;

    public DispatchException(Message fault) {
        this.fault = fault;
    }

}
