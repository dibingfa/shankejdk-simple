/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import junit.framework.TestCase;

import com.apple.jobjc.foundation.FoundationFramework;
import com.apple.jobjc.foundation.NSAutoreleasePool;

public class PooledTestCase extends TestCase {
    static{
        System.loadLibrary("JObjC-tests");
    }
    NSAutoreleasePool pool;

    @Override public void setUp() throws Exception {
        FoundationFramework foundation = JObjC.getInstance().Foundation();
        pool = foundation.NSAutoreleasePool().alloc();
        pool.init();
    }

    @Override public void tearDown() throws Exception {
        pool.drain();
    }
}
