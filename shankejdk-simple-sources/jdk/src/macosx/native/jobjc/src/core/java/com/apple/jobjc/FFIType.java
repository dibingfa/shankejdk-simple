/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Coder.PrimitivePointerCoder;


class FFIType{
    private static native void makeFFIType(long ffi_type_buf, long elements_buf);
    private static native int  getFFITypeSizeof();
    private static int FFI_TYPE_SIZEOF = getFFITypeSizeof();
    final NativeBuffer ffi_type;
    final NativeBuffer elements;
    final Coder[] elementCoders;

    public FFIType(final Coder... elementCoders){
        final JObjCRuntime runtime = JObjCRuntime.inst();
        this.elementCoders = elementCoders;
        this.ffi_type = new NativeBuffer(FFI_TYPE_SIZEOF);
        this.elements = new NativeBuffer(JObjCRuntime.PTR_LEN * (elementCoders.length + 1));

        long elIterPtr = elements.bufferPtr;
        for(Coder c : elementCoders){
            PrimitivePointerCoder.INST.push(runtime, elIterPtr, c.getFFITypePtr());
            elIterPtr += PrimitivePointerCoder.INST.sizeof();
        }
        PrimitivePointerCoder.INST.push(runtime, elIterPtr, 0);

        makeFFIType(ffi_type.bufferPtr, elements.bufferPtr);
    }

    public long getPtr(){
        return ffi_type.bufferPtr;
    }
}
