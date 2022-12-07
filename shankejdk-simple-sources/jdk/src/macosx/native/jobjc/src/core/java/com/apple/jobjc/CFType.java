/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


public class CFType extends Pointer<Void> {
    protected CFType(long ptr) { super(ptr); }
    protected CFType(Pointer<?> ptr) { super(ptr.ptr); }

    protected ID getTollFreeBridge(JObjCRuntime runtime){
        return ID.getObjCObjectFor(runtime, super.ptr);
    }
}
