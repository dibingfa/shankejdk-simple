/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */

package sun.management.counter;

/**
 * Interface for a performance counter wrapping a <code>String</code> object.
 *
 * @author   Brian Doherty
 */
public interface StringCounter extends Counter {

    /**
     * Get a copy of the value of the StringCounter.
     */
    public String stringValue();
}
