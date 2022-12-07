/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.assembler.dev;

import com.sun.xml.internal.ws.api.pipe.Pipe;
import com.sun.xml.internal.ws.api.pipe.Tube;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public interface TubelineAssemblyContext {

    Pipe getAdaptedTubelineHead();

    <T> T getImplementation(Class<T> type);

    Tube getTubelineHead();
}
