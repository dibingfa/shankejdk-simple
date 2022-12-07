/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.jdi.request;

import com.sun.jdi.*;

/**
 * Request for notification of a thread in the target VM entering a monitor
 * after waiting for it to be released by another thread.
 * When an enabled MonitorContededEnteredRequest is satisfied, an
 * {@link com.sun.jdi.event.EventSet event set} containing a
 * {@link com.sun.jdi.event.MonitorContendedEnteredEvent MonitorContendedEnteredEvent}
 * will be placed on the
 * {@link com.sun.jdi.event.EventQueue EventQueue}.
 * The collection of existing MonitorContendedEnteredEvents is
 * managed by the {@link EventRequestManager}
 *
 * @see com.sun.jdi.event.MonitorContendedEnteredEvent
 * @see com.sun.jdi.event.EventQueue
 * @see EventRequestManager
 *
 * @author Swamy Venkataramanappa
 * @since  1.6
 */
@jdk.Exported
public interface MonitorContendedEnteredRequest extends EventRequest {

    /**
     * Restricts the events generated by this request to those in
     * the given thread.
     * @param thread the thread to filter on.
     * @throws InvalidRequestStateException if this request is currently
     * enabled or has been deleted.
     * Filters may be added only to disabled requests.
     */
    void addThreadFilter(ThreadReference thread);

    /**
     * Restricts the events generated by this request to those whose
     * method is in the given reference type or any of its subtypes.
     * An event will be generated for any location in a reference type
     * that can be safely cast to the given reference type.
     *
     * @param refType the reference type to filter on.
     * @throws InvalidRequestStateException if this request is currently
     * enabled or has been deleted.
     * Filters may be added only to disabled requests.
     */
    void addClassFilter(ReferenceType refType);

    /**
     * Restricts the events generated by this request to those
     * whose method is in a class whose name matches this restricted
     * regular expression. Regular expressions are limited
     * to exact matches and patterns that begin with '*' or end with '*';
     * for example, "*.Foo" or "java.*".
     *
     * @param classPattern the pattern String to filter for.
     * @throws InvalidRequestStateException if this request is currently
     * enabled or has been deleted.
     * Filters may be added only to disabled requests.
     */
    void addClassFilter(String classPattern);

    /**
     * Restricts the events generated by this request to those
     * whose method is in a class whose name does <b>not</b> match this restricted
     * regular expression, e.g. "java.*" or "*.Foo".
     * @param classPattern the pattern String to filter against.
     * @throws InvalidRequestStateException if this request is currently
     * enabled or has been deleted.
     * Filters may be added only to disabled requests.
     */
    void addClassExclusionFilter(String classPattern);

    /**
     * Restricts the events generated by this request to those in
     * which the currently executing instance ("this") is the object
     * specified.
     * <P>
     * Not all targets support this operation.
     * Use {@link VirtualMachine#canUseInstanceFilters()}
     * to determine if the operation is supported.
     * @param instance the object which must be the current instance
     * in order to pass this filter.
     * @throws java.lang.UnsupportedOperationException if
     * the target virtual machine does not support this
     * operation.
     * @throws InvalidRequestStateException if this request is currently
     * enabled or has been deleted.
     * Filters may be added only to disabled requests.
     */
    void addInstanceFilter(ObjectReference instance);
}
