/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.nashorn.internal.ir;

/**
 * Interface used by AccessNodes, IndexNodes and IdentNodes to signal that when evaluated, their value will be treated
 * as a function and immediately invoked, e.g. {@code foo()}, {@code foo.bar()} or {@code foo[bar]()}. Used to customize
 * the priority of composite dynamic operations when emitting {@code INVOKEDYNAMIC} instructions that implement them,
 * namely prioritize {@code getMethod} over {@code getElem} or {@code getProp}. An access or ident node with isFunction
 * set to true will be emitted as {@code dyn:getMethod|getProp|getElem} while one with it set to false will be emitted
 * as {@code dyn:getProp|getElem|getMethod}. Similarly, an index node with isFunction set to true will be emitted as
 * {@code dyn:getMethod|getElem|getProp} while the one set to false will be emitted as {@code dyn:getElem|getProp|getMethod}.
 */
public interface FunctionCall {
    /**
     * Returns true if the value of this expression will be treated as a function and immediately invoked.
     * @return true if the value of this expression will be treated as a function and immediately invoked.
     */
    public boolean isFunction();
}
