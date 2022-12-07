/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.wsdl.parser;

import javax.xml.ws.WebServiceException;


/**
 * listen to static errors found during building a the WSDL Model.
 *
 * @author Vivek Pandey
 */
public interface ErrorHandler {
    /**
     * Receives a notification for an error in the annotated code.
     */
    void error( Throwable e );
}
