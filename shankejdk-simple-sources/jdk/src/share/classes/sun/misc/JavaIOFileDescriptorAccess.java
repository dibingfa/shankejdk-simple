/*
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 */
package sun.misc;

import java.io.FileDescriptor;

/*
 * @author Chris Hegarty
 */

public interface JavaIOFileDescriptorAccess {
    public void set(FileDescriptor obj, int fd);
    public int get(FileDescriptor fd);

    // Only valid on Windows
    public void setHandle(FileDescriptor obj, long handle);
    public long getHandle(FileDescriptor obj);
}
