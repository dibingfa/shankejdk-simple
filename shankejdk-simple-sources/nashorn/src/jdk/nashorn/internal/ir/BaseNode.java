/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.ir;

import static jdk.nashorn.internal.runtime.UnwarrantedOptimismException.INVALID_PROGRAM_POINT;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.parser.TokenType;

/**
 * IR base for accessing/indexing nodes.
 *
 * @see AccessNode
 * @see IndexNode
 */
@Immutable
public abstract class BaseNode extends Expression implements FunctionCall, Optimistic {
    private static final long serialVersionUID = 1L;

    /** Base Node. */
    protected final Expression base;

    private final boolean isFunction;

    /** Callsite type for this node, if overridden optimistically or conservatively depending on coercion */
    protected final Type type;

    /** Program point id */
    protected final int programPoint;

    /**
     * Constructor
     *
     * @param token  token
     * @param finish finish
     * @param base   base node
     * @param isFunction is this a function
     */
    public BaseNode(final long token, final int finish, final Expression base, final boolean isFunction) {
        super(token, base.getStart(), finish);
        this.base           = base;
        this.isFunction     = isFunction;
        this.type = null;
        this.programPoint   = INVALID_PROGRAM_POINT;
    }

    /**
     * Copy constructor for immutable nodes
     * @param baseNode node to inherit from
     * @param base base
     * @param isFunction is this a function
     * @param callSiteType  the callsite type for this base node, either optimistic or conservative
     * @param programPoint  program point id
     */
    protected BaseNode(final BaseNode baseNode, final Expression base, final boolean isFunction, final Type callSiteType, final int programPoint) {
        super(baseNode);
        this.base           = base;
        this.isFunction     = isFunction;
        this.type = callSiteType;
        this.programPoint   = programPoint;
    }

    /**
     * Get the base node for this access
     * @return the base node
     */
    public Expression getBase() {
        return base;
    }

    @Override
    public boolean isFunction() {
        return isFunction;
    }

    @Override
    public Type getType() {
        return type == null ? getMostPessimisticType() : type;
    }

    @Override
    public int getProgramPoint() {
        return programPoint;
    }

    @Override
    public Type getMostOptimisticType() {
        return Type.INT;
    }

    @Override
    public Type getMostPessimisticType() {
        return Type.OBJECT;
    }

    @Override
    public boolean canBeOptimistic() {
        return true;
    }

    /**
     * Return true if this node represents an index operation normally represented as {@link IndexNode}.
     * @return true if an index access.
     */
    public boolean isIndex() {
        return isTokenType(TokenType.LBRACKET);
    }

    /**
     * Mark this node as being the callee operand of a {@link CallNode}.
     * @return a base node identical to this one in all aspects except with its function flag set.
     */
    public abstract BaseNode setIsFunction();

}
