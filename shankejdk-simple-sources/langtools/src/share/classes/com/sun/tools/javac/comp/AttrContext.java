/*
 * Copyright (c) 1999, 2014, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.javac.comp;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.*;
import com.sun.tools.javac.code.*;

/** Contains information specific to the attribute and enter
 *  passes, to be used in place of the generic field in environments.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class AttrContext {

    /** The scope of local symbols.
     */
    Scope scope = null;

    /** The number of enclosing `static' modifiers.
     */
    int staticLevel = 0;

    /** Is this an environment for a this(...) or super(...) call?
     */
    boolean isSelfCall = false;

    /** Are we evaluating the selector of a `super' or type name?
     */
    boolean selectSuper = false;

    /** Is the current target of lambda expression or method reference serializable?
     */
    boolean isSerializable = false;

    /** Are arguments to current function applications boxed into an array for varargs?
     */
    Resolve.MethodResolutionPhase pendingResolutionPhase = null;

    /** A record of the lint/SuppressWarnings currently in effect
     */
    Lint lint;

    /** The variable whose initializer is being attributed
     * useful for detecting self-references in variable initializers
     */
    Symbol enclVar = null;

    /** ResultInfo to be used for attributing 'return' statement expressions
     * (set by Attr.visitMethod and Attr.visitLambda)
     */
    Attr.ResultInfo returnResult = null;

    /** Symbol corresponding to the site of a qualified default super call
     */
    Type defaultSuperCallSite = null;

    /** Tree that when non null, is to be preferentially used in diagnostics.
     *  Usually Env<AttrContext>.tree is the tree to be referred to in messages,
     *  but this may not be true during the window a method is looked up in enclosing
     *  contexts (JDK-8145466)
     */
    JCTree preferredTreeForDiagnostics;

    /** Duplicate this context, replacing scope field and copying all others.
     */
    AttrContext dup(Scope scope) {
        AttrContext info = new AttrContext();
        info.scope = scope;
        info.staticLevel = staticLevel;
        info.isSelfCall = isSelfCall;
        info.selectSuper = selectSuper;
        info.pendingResolutionPhase = pendingResolutionPhase;
        info.lint = lint;
        info.enclVar = enclVar;
        info.returnResult = returnResult;
        info.defaultSuperCallSite = defaultSuperCallSite;
        info.isSerializable = isSerializable;
        info.preferredTreeForDiagnostics = preferredTreeForDiagnostics;
        return info;
    }

    /** Duplicate this context, copying all fields.
     */
    AttrContext dup() {
        return dup(scope);
    }

    public Iterable<Symbol> getLocalElements() {
        if (scope == null)
            return List.nil();
        return scope.getElements();
    }

    boolean lastResolveVarargs() {
        return pendingResolutionPhase != null &&
                pendingResolutionPhase.isVarargsRequired();
    }

    @Override
    public String toString() {
        return "AttrContext[" + scope.toString() + "]";
    }
}
