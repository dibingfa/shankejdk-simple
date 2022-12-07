/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.foundation.FoundationFramework;
import com.apple.jobjc.foundation.NSDictionary;
import com.apple.jobjc.foundation.NSString;

public class VarArgsTest extends PooledTestCase {
    FoundationFramework FND = JObjC.getInstance().Foundation();

    public void testNSString_initWithFormat(){
        String expected = "1 + 0.2 = 1.2 abracadabra";
        NSString format = Utils.get().strings().nsString("%d + %.1f = %.1f %@");

        NSString abra = Utils.get().strings().nsString("abracadabra");

        NSString nstr = ((NSString)FND.NSString().alloc()).initWithFormat(format, 1, 0.2, 1.2, abra);
        String actual = Utils.get().strings().javaString(nstr);

        assertEquals(expected, actual);
    }

    public void testNSDictionary(){
        NSString v1 = Utils.get().strings().nsString("value1");
        NSString v2 = Utils.get().strings().nsString("value2");
        NSString k1 = Utils.get().strings().nsString("key1");
        NSString k2 = Utils.get().strings().nsString("key2");

        NSDictionary dict = ((NSDictionary)FND.NSDictionary().alloc()).initWithObjectsAndKeys(v1, k1, v2, k2, null);

        NSString nsdescr = dict.description();
        String jdescr = Utils.get().strings().javaString(nsdescr);

        assertEquals("{\n    key1 = value1;\n    key2 = value2;\n}", jdescr);
    }

    public static void main(String[] args){
        junit.textui.TestRunner.run(VarArgsTest.class);
    }
}
