/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


public class Opaque extends Pointer<Void> {
    protected Opaque(long ptr) { super(ptr); }
    protected Opaque(Pointer<?> ptr) { super(ptr.ptr); }
}
