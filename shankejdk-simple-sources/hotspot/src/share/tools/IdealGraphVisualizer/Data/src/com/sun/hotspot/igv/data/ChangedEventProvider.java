/*
 * Copyright (c) 1998, 2007, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.hotspot.igv.data;

/**
 *
 * @author Thomas Wuerthinger
 */
public interface ChangedEventProvider<T> {

    public ChangedEvent<T> getChangedEvent();
}
