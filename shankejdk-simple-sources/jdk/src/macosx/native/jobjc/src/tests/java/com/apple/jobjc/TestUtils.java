/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


public class TestUtils {
    static MacOSXFramework getAppKit() {
        return UnsafeRuntimeAccess.getFramework(new String[]{"/System/Library/Frameworks/AppKit.framework/AppKit"});
    }

    static MacOSXFramework getFoundation() {
        return UnsafeRuntimeAccess.getFramework(new String[]{"/System/Library/Frameworks/Foundation.framework/Foundation"});
    }
}
