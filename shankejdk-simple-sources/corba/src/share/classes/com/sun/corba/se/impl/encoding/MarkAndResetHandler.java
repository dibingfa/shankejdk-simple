/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.corba.se.impl.encoding;


/**
 * Defines an abstraction for a RestorableInputStream to
 * implement mark/reset.
 */
interface MarkAndResetHandler
{
    void mark(RestorableInputStream inputStream);

    void fragmentationOccured(ByteBufferWithInfo newFragment);

    void reset();
}
