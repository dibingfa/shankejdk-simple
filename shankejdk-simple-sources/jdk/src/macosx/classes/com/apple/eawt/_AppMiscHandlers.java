/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

class _AppMiscHandlers {
    private static native void nativeOpenHelpViewer();

    private static native void nativeRequestActivation(final boolean allWindows);
    private static native void nativeRequestUserAttention(final boolean critical);

    private static native void nativeEnableSuddenTermination();
    private static native void nativeDisableSuddenTermination();

    static void openHelpViewer() {
        nativeOpenHelpViewer();
    }

    static void requestActivation(final boolean allWindows) {
        nativeRequestActivation(allWindows);
    }

    static void requestUserAttention(final boolean critical) {
        nativeRequestUserAttention(critical);
    }

    static void enableSuddenTermination() {
        nativeEnableSuddenTermination();
    }

    static void disableSuddenTermination() {
        nativeDisableSuddenTermination();
    }
}
