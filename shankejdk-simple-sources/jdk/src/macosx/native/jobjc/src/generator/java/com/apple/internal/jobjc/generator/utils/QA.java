/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.utils;

import com.apple.internal.jobjc.generator.Utils;

public class QA {
    public static void nonNull(Object... os){
        for(Object o : os) if(o == null) throw new NullPointerException(Utils.joinWComma(os));
    }

    public static boolean bothNullOrEquals(Object a, Object b) {
        if(a == null && b == null) return true;
        if(a == null || b == null) return false;
        return a.equals(b);
    }
}
