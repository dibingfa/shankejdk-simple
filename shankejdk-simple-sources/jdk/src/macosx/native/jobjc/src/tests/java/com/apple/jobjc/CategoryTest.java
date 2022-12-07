/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.appkit.AppKitFramework;
import com.apple.jobjc.appkit.NSStringCategory;
import com.apple.jobjc.foundation.NSSize;
import com.apple.jobjc.foundation.NSString;

public class CategoryTest extends PooledTestCase {
    public void testAppKit_NSString(){
        AppKitFramework APPKIT = JObjC.getInstance().AppKit();

        NSString nstr = Utils.get().strings().nsString("mirzapirza");
        NSStringCategory nstrx = APPKIT.NSStringCategory(nstr);
        NSSize sz = nstrx.sizeWithAttributes(null);

        assertEquals(57.0, sz.width());
        assertEquals(15.0, sz.height());
    }

    public static void main(String[] args){
        junit.textui.TestRunner.run(CategoryTest.class);
    }
}
