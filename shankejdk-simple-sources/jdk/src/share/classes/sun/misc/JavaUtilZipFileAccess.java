/*
 * Copyright (c) 2013, 2021, Oracle and/or its affiliates. All rights reserved.
 */

package sun.misc;

import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public interface JavaUtilZipFileAccess {
    public boolean startsWithLocHeader(ZipFile zip);
    public int getManifestNum(JarFile zip);
}

