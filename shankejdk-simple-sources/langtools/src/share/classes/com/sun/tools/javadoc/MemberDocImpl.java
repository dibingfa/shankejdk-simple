/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.javadoc;

import com.sun.javadoc.*;

import com.sun.source.util.TreePath;
import com.sun.tools.javac.code.Symbol;

/**
 * Represents a member of a java class: field, constructor, or method.
 * This is an abstract class dealing with information common to
 * method, constructor and field members. Class members of a class
 * (nested classes) are represented instead by ClassDocImpl.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 *
 * @see MethodDocImpl
 * @see FieldDocImpl
 * @see ClassDocImpl
 *
 * @author Robert Field
 * @author Neal Gafter
 */

public abstract class MemberDocImpl
    extends ProgramElementDocImpl
    implements MemberDoc {

    /**
     * constructor.
     */
    public MemberDocImpl(DocEnv env, Symbol sym, TreePath treePath) {
        super(env, sym, treePath);
    }

    /**
     * Returns true if this field was synthesized by the compiler.
     */
    public abstract boolean isSynthetic();
}
