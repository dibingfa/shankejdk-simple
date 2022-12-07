/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic;

/**
 * Specialization info for a {@link SpecializedFunction}
 */
public final class Specialization {
    private final MethodHandle mh;
    private final Class<? extends LinkLogic> linkLogicClass;
    private final boolean isOptimistic;

    /**
     * Constructor
     *
     * @param mh  invoker method handler
     */
    public Specialization(final MethodHandle mh) {
        this(mh, false);
    }

    /**
     * Constructor
     *
     * @param mh  invoker method handler
     * @param isOptimistic is this an optimistic native method, i.e. can it throw {@link UnwarrantedOptimismException}
     *   which would have to lead to a relink and return value processing
     */
    public Specialization(final MethodHandle mh, final boolean isOptimistic) {
        this(mh, null, isOptimistic);
    }

    /**
     * Constructor
     *
     * @param mh  invoker method handler
     * @param linkLogicClass extra link logic needed for this function. Instances of this class also contains logic for checking
     *  if this can be linked on its first encounter, which is needed as per our standard linker semantics
     * @param isOptimistic is this an optimistic native method, i.e. can it throw {@link UnwarrantedOptimismException}
     *   which would have to lead to a relink and return value processing
     */
    public Specialization(final MethodHandle mh, final Class<? extends LinkLogic> linkLogicClass, final boolean isOptimistic) {
        this.mh             = mh;
        this.isOptimistic   = isOptimistic;
        if (linkLogicClass != null) {
            //null out the "empty" link logic class for optimization purposes
            //we only use the empty instance because we can't default class annotations
            //to null
            this.linkLogicClass = LinkLogic.isEmpty(linkLogicClass) ? null : linkLogicClass;
        } else {
            this.linkLogicClass = null;
        }
     }

    /**
     * Get the method handle for the invoker of this ScriptFunction
     * @return the method handle
     */
    public MethodHandle getMethodHandle() {
        return mh;
    }

    /**
     * Get the link logic class for this ScriptFunction
     * @return link logic class info, i.e. one whose instance contains stuff like
     *  "do we need exception check for every call", and logic to check if we may link
     */
    public Class<? extends LinkLogic> getLinkLogicClass() {
        return linkLogicClass;
    }

    /**
     * An optimistic specialization is one that can throw UnwarrantedOptimismException.
     * This is allowed for native methods, as long as they are functional, i.e. don't change
     * any state between entering and throwing the UOE. Then we can re-execute a wider version
     * of the method in the continuation. Rest-of method generation for optimistic builtins is
     * of course not possible, but this approach works and fits into the same relinking
     * framework
     *
     * @return true if optimistic
     */
    public boolean isOptimistic() {
        return isOptimistic;
    }

}

