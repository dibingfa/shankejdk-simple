/*
 * Copyright (c) 2000, 2010, Oracle and/or its affiliates. All rights reserved.
 */

/**
 * An object that interrupts a thread blocked in an I/O operation.
 */

package sun.nio.ch;

public interface Interruptible {

    public void interrupt(Thread t);

}
