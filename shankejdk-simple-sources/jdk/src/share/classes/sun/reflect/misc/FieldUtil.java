/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package sun.reflect.misc;

import java.lang.reflect.Field;

/*
 * Create a trampoline class.
 */
public final class FieldUtil {

    private FieldUtil() {
    }

    public static Field getField(Class<?> cls, String name)
        throws NoSuchFieldException {
        ReflectUtil.checkPackageAccess(cls);
        return cls.getField(name);
    }

    public static Field[] getFields(Class<?> cls) {
        ReflectUtil.checkPackageAccess(cls);
        return cls.getFields();
    }

    public static Field[] getDeclaredFields(Class<?> cls) {
        ReflectUtil.checkPackageAccess(cls);
        return cls.getDeclaredFields();
    }
}
