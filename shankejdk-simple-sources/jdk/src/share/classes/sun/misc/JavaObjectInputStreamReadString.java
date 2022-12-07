/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 */

package sun.misc;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Interface to specify methods for accessing {@code ObjectInputStream}.
 */
@FunctionalInterface
public interface JavaObjectInputStreamReadString {
    String readString(ObjectInputStream ois) throws IOException;
}

