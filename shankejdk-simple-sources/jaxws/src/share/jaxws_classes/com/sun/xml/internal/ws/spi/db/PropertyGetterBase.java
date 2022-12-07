/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.spi.db;

/**
 * This is the Gtter of a bean property.
 *
 * @author shih-chang.chen@oracle.com
 * @exclude
 */
public abstract class PropertyGetterBase implements PropertyGetter {
    protected Class type;

    public Class getType() {
        return type;
    }

    static public boolean getterPattern(java.lang.reflect.Method method) {
        if (!method.getReturnType().equals(void.class) &&
            (method.getParameterTypes() == null ||
             method.getParameterTypes().length == 0)) {
            if (method.getName().startsWith("get") &&
                method.getName().length() > 3) {
                return true;
            } else {
                if (method.getReturnType().equals(boolean.class) &&
                    method.getName().startsWith("is") &&
                    method.getName().length() > 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
