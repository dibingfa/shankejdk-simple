/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.java2d.cmm;

import java.awt.color.CMMException;

public class Profile {
    private final long nativePtr;

    protected Profile(long ptr) {
        nativePtr = ptr;
    }

    protected final long getNativePtr() {
        if (nativePtr == 0L) {
            throw new CMMException("Invalid profile: ptr is null");
        }
        return nativePtr;
    }
}
