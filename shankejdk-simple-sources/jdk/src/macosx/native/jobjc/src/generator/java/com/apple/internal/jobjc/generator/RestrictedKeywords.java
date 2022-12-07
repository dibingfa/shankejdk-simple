/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RestrictedKeywords {
    static final String[] JAVA_KEYWORD_CONFLICTS = {
        "wait", "null", "class", "new", "toString", "finalize", "boolean", "interface", "final", "static"
    };

    static final Set<String> originalRestrictedSet = new HashSet<String>(Arrays.asList(JAVA_KEYWORD_CONFLICTS));

    public static Set<String> getNewRestrictedSet() {
        return new HashSet<String>(originalRestrictedSet);
    }

    public static boolean isRestricted(String s){
        return originalRestrictedSet.contains(s);
    }
}
