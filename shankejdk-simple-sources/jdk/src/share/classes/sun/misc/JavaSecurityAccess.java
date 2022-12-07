/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package sun.misc;

import java.security.AccessControlContext;
import java.security.PrivilegedAction;

public interface JavaSecurityAccess {

    <T> T doIntersectionPrivilege(PrivilegedAction<T> action,
                                  AccessControlContext stack,
                                  AccessControlContext context);

    <T> T doIntersectionPrivilege(PrivilegedAction<T> action,
                                  AccessControlContext context);

}
