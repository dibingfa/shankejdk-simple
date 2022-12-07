/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package java.lang;

import java.io.File;

class ClassLoaderHelper {

    private ClassLoaderHelper() {}

    /**
     * Returns an alternate path name for the given file
     * such that if the original pathname did not exist, then the
     * file may be located at the alternate location.
     * For mac, this replaces the final .dylib suffix with .jnilib
     */
    static File mapAlternativeName(File lib) {
        String name = lib.toString();
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return null;
        }
        return new File(name.substring(0, index) + ".jnilib");
    }
}
