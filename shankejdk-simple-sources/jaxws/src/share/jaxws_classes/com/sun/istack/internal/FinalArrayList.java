/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.istack.internal;

import java.util.ArrayList;
import java.util.Collection;

/**
 * {@link ArrayList} with the final keyword.
 *
 * <p>
 * This gives HotSpot a better hint that all methods can be inlined.
 *
 * @author Kohsuke Kawaguchi
 */
public final class FinalArrayList<T> extends ArrayList<T> {
    public FinalArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public FinalArrayList() {
    }

    public FinalArrayList(Collection<? extends T> ts) {
        super(ts);
    }
}
