/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.processor.model;



/**
 * @author Vivek Pandey
 *
 * Async WSDLOperation type
 */
public final class AsyncOperationType {

    public static final AsyncOperationType POLLING = new AsyncOperationType();
    public static final AsyncOperationType CALLBACK = new AsyncOperationType();

    private AsyncOperationType() {
    }

}
