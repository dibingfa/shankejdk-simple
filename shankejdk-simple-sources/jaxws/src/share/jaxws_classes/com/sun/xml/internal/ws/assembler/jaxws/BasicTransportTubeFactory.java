/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.assembler.jaxws;

import com.sun.xml.internal.ws.api.pipe.Tube;
import com.sun.xml.internal.ws.assembler.dev.ClientTubelineAssemblyContext;
import com.sun.xml.internal.ws.assembler.dev.ServerTubelineAssemblyContext;
import com.sun.xml.internal.ws.assembler.dev.TubeFactory;

import javax.xml.ws.WebServiceException;


/**
 * TubeFactory implementation creating one of the standard JAX-WS RI tubes
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public final class BasicTransportTubeFactory implements TubeFactory {

    public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
        return context.getWrappedContext().createTransportTube();
    }

    public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
        return context.getTubelineHead();
    }

}
