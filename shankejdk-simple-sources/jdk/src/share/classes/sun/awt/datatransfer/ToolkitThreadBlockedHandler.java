/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt.datatransfer;

public interface ToolkitThreadBlockedHandler {
    public void lock();
    public void unlock();
    public void enter();
    public void exit();
}
