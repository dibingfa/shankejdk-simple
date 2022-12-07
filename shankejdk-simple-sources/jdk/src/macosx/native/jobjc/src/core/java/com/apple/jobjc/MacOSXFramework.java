/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;



public class MacOSXFramework {
    private static native long retainFramework(final String frameworkName);
    private static native void releaseFramework(final long frameworkPtr);
    private static native void getConstant(final long frameworkPtr, String symbol, final long bufferPtr, final int size);

    private final JObjCRuntime runtime;
    protected final long[] nativeLibPtrs;

    final long getFrameworkPtr() { return nativeLibPtrs.length > 0 ? nativeLibPtrs[0] : 0; }

    private static long[] createFrameworkPtrsFromPaths(final String[] frameworkLibPaths) {
        final long[] libPtrs = new long[frameworkLibPaths.length];
        for(int i = 0; i < libPtrs.length; i++){
            libPtrs[i] = retainFramework(frameworkLibPaths[i]);
            if(libPtrs[i] == 0) throw new RuntimeException("Could not open library at " + frameworkLibPaths[i]);
        }
        return libPtrs;
    }

    protected MacOSXFramework(final JObjCRuntime runtime, final String[] nativeLibPaths) {
        runtime.assertOK();
        this.runtime = runtime;
        this.nativeLibPtrs = createFrameworkPtrsFromPaths(nativeLibPaths);
    }

    @Override protected final synchronized void finalize() throws Throwable {
        for(long lib : nativeLibPtrs)
            if(lib != 0) releaseFramework(lib);
    }

    protected final JObjCRuntime getRuntime(){ return runtime; }

    protected void getConstant(final String symbol, final long retValPtr, final int size){
        assert size >= 0;
        assert retValPtr != 0;
        getConstant(getFrameworkPtr(), symbol, retValPtr, size);
    }

    protected void getConstant(final String symbol, final NativeArgumentBuffer out, final int size){
        getConstant(symbol, out.retValPtr, size);
    }

    protected void getConstant(final String symbol, final Struct out, final int size){
        getConstant(symbol, out.raw.bufferPtr, size);
    }
}
