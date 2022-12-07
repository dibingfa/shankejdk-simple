/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.jdi;

/**
 * Thrown to indicate that the requested class has
 * not yet been loaded through the appropriate class loader.
 * <p>
 * Due to the lazy class linking performed by many VMs, it is
 * possible for a field or variable to be visible in a program
 * before the associated class is loaded. Until the class is loaded
 * all that is available is a signature string. If an attempt is made to
 * set the value of such a field or variable from JDI, the appropriate
 * type checking cannot be done because the destination class has not been
 * loaded. The same is true for the element class of array elements.
 * <p>
 * It is not advisable to solve this problem by attempting a class load on
 * the fly in this case. There are two problems in having the debugger load
 * a class instead of waiting for it to load over the normal course
 * of events.
 * <ul>
 * <li>There can be no guarantee that running the appropriate class
 * loader won't cause a deadlock in loading the
 * class. Class loaders can consist of arbitrary
 * Java<sup><font size=-2>TM</font></sup> programming language code and the
 * class loading methods are usually synchronized. Most of the work
 * done by a debugger happens when threads are suspended. If another
 * application thread is suspended within the same class loader,
 *  a deadlock is very possible.
 * <li>Changing the order in which classes are normally loaded may either mask
 * or reveal bugs in the application. An unintrusive debugger should strive
 * to leave unchanged the behavior of the application being debugged.
 * </ul>
 * To avoid these potential problems, this exception is thrown.
 * <p>
 * Note that this exception will be thrown until the class in question
 * is visible to the class loader of enclosing class. (That is, the
 * class loader of the enclosing class must be an <i>initiating</i> class
 * loader for the class in question.)
 * See
 * <cite>The Java&trade; Virtual Machine Specification</cite>
 * for more details.
 *
 * @author Gordon Hirsch
 * @since  1.3
 */
@jdk.Exported
public class ClassNotLoadedException extends Exception
{
    private static final long serialVersionUID = -6242978768444298722L;
    private String className;

    public ClassNotLoadedException(String className) {
        super();
        this.className = className;
    }

    public ClassNotLoadedException(String className, String message) {
        super(message);
        this.className = className;
    }

    public String className() {
        return className;
    }
}
