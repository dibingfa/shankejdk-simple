/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package java.lang.ref;

/**
 * Final references, used to implement finalization
 */
class FinalReference<T> extends Reference<T> {

    public FinalReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
    }
}
