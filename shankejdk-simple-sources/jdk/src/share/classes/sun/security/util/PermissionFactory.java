/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.util;

import java.security.Permission;

/**
 * A factory object that creates Permission objects.
 */

public interface PermissionFactory<T extends Permission> {
    T newPermission(String name);
}
