/*
 * Copyright (c) 2007, 2008, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.classfile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class Attributes implements Iterable<Attribute> {
    Attributes(ClassReader cr) throws IOException {
        map = new HashMap<String,Attribute>();
        int attrs_count = cr.readUnsignedShort();
        attrs = new Attribute[attrs_count];
        for (int i = 0; i < attrs_count; i++) {
            Attribute attr = Attribute.read(cr);
            attrs[i] = attr;
            try {
                map.put(attr.getName(cr.getConstantPool()), attr);
            } catch (ConstantPoolException e) {
                // don't enter invalid names in map
            }
        }
    }

    public Attributes(ConstantPool constant_pool, Attribute[] attrs) {
        this.attrs = attrs;
        map = new HashMap<String,Attribute>();
        for (int i = 0; i < attrs.length; i++) {
            Attribute attr = attrs[i];
            try {
                map.put(attr.getName(constant_pool), attr);
            } catch (ConstantPoolException e) {
                // don't enter invalid names in map
            }
        }
    }

    public Iterator<Attribute> iterator() {
        return Arrays.asList(attrs).iterator();
    }

    public Attribute get(int index) {
        return attrs[index];
    }

    public Attribute get(String name) {
        return map.get(name);
    }

    public int getIndex(ConstantPool constant_pool, String name) {
        for (int i = 0; i < attrs.length; i++) {
            Attribute attr = attrs[i];
            try {
                if (attr != null && attr.getName(constant_pool).equals(name))
                    return i;
            } catch (ConstantPoolException e) {
                // ignore invalid entries
            }
        }
        return -1;
    }

    public int size() {
        return attrs.length;
    }

    public int byteLength() {
        int length = 2;
        for (Attribute a: attrs)
            length += a.byteLength();
        return length;
    }

    public final Attribute[] attrs;
    public final Map<String, Attribute> map;
}
