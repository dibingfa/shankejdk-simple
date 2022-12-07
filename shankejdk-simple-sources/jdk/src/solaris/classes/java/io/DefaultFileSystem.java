/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */

package java.io;

/**
 *
 * @since 1.8
 */
class DefaultFileSystem {

    /**
     * Return the FileSystem object for Unix-based platform.
     */
    public static FileSystem getFileSystem() {
        return new UnixFileSystem();
    }
}
