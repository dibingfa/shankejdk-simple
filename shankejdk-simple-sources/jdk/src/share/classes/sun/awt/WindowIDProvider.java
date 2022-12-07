/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt;

/**
 * Classes implementing this interface have public getWindow method,
 * returning handle to the native window.
 */
public interface WindowIDProvider {
    public long getWindow();
}
