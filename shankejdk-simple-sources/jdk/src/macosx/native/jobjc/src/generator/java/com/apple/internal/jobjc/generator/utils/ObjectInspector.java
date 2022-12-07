/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.utils;

import java.io.StringWriter;
import java.lang.reflect.Field;

public abstract class ObjectInspector {
    /**
     * @return  a string representation of object internals.
     */
    public static String inspect(Object obj) {
        StringWriter sw = new StringWriter();
        inspectForClass(obj, obj.getClass(), sw);
        return sw.toString();
    }

    private static void inspectForClass(Object instance, Class clazz, StringWriter sw){
        boolean willGoSuper = clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object");

        sw.append(clazz.getSimpleName());
        sw.append("{");
        Field[] fs = clazz.getDeclaredFields();
        for(int i = 0; i < fs.length; i++){
            Field f = fs[i];
            f.setAccessible(true);
            sw.append(f.getName());
            sw.append(": ");
            try {
                Object o = f.get(instance);
                sw.append(o == null ? "null" : o.toString());
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
            if(i < fs.length - 1 || willGoSuper)
                sw.append(", ");
        }

        if(willGoSuper){
            sw.append("super: ");
            inspectForClass(instance, clazz.getSuperclass(), sw);
        }

        sw.append("}");
    }
}
