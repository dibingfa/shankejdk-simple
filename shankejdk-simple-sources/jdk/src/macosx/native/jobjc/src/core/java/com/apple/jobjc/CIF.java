/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Coder.PrimitivePointerCoder;

class CIF {
    private static native int getSizeofCIF();
    private static final int SIZEOF = getSizeofCIF();
    private static native boolean prepCIF(long cifPtr, int nargs, long retFFITypePtr, long argsPtr);

    public static CIF createCIFFor(final NativeArgumentBuffer args, final Coder returnCoder, final Coder ... argCoders) {
        NativeBuffer cifBuf = new NativeBuffer(SIZEOF + (argCoders.length * JObjCRuntime.PTR_LEN));
        final long argsPtr = cifBuf.bufferPtr + SIZEOF;

        {
            long argsIterPtr = argsPtr;
            for(final Coder coder : argCoders){
                PrimitivePointerCoder.INST.push(args.runtime, argsIterPtr, coder.getFFITypePtr());
                argsIterPtr += JObjCRuntime.PTR_LEN;
            }
        }

        boolean ok = prepCIF(cifBuf.bufferPtr, argCoders.length, returnCoder.getFFITypePtr(), argsPtr);
        if(!ok)
            throw new RuntimeException("ffi_prep_cif failed.");

        return new CIF(cifBuf, returnCoder, argCoders);
    }

    final NativeBuffer cif;
    // CIF needs to keep refs to the Coders, so they don't get finalized and their FFITypes freed.
    final Coder returnCoder;
    final Coder[] argCoders;

    private CIF(final NativeBuffer cif, Coder returnCoder, Coder... argCoders) {
        this.cif = cif;
        this.returnCoder = returnCoder;
        this.argCoders = argCoders;
    }
}
