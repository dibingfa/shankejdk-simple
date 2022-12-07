/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package sun.reflect.misc;

import java.lang.reflect.Constructor;

public final class ConstructorUtil {

    private ConstructorUtil() {
    }

    public static Constructor<?> getConstructor(Class<?> cls, Class<?>[] params)
        throws NoSuchMethodException {
        ReflectUtil.checkPackageAccess(cls);
        return cls.getConstructor(params);
    }

    public static Constructor<?>[] getConstructors(Class<?> cls) {
        ReflectUtil.checkPackageAccess(cls);
        return cls.getConstructors();
    }
}
