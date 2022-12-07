/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 */

package sun.reflect.generics.scope;

import java.lang.reflect.TypeVariable;


public interface Scope {
    TypeVariable<?> lookup(String name);
}
