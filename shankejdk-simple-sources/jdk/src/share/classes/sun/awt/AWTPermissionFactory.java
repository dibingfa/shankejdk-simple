/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt;

import java.awt.AWTPermission;
import sun.security.util.PermissionFactory;

/**
 * A factory object for AWTPermission objects.
 */

public class AWTPermissionFactory
    implements PermissionFactory<AWTPermission>
{
    @Override
    public AWTPermission newPermission(String name) {
        return new AWTPermission(name);
    }
}
