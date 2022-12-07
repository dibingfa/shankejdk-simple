/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.foundation.FoundationFramework;
import com.apple.jobjc.foundation.NSString;
import com.apple.jobjc.foundation.NSStringClass;

public class NSClassTest extends PooledTestCase{
    JObjC JOBJC = JObjC.getInstance();
    FoundationFramework FND = JOBJC.Foundation();

    public void testNSClassName(){
        NSString s = ((NSString) FND.NSString().alloc()).init();

        NSString cname = s.className();
        String jcname = Utils.get().strings().javaString(cname);
        assertEquals("NSCFString", jcname);
    }

    public void testNSClassPop(){
        NSString s = ((NSString) FND.NSString().alloc()).init();

        NSStringClass c = s.classNSClass();
        String jdescr = Utils.get().strings().javaString(c.description());
        assertEquals("NSCFString", jdescr);
    }
}
