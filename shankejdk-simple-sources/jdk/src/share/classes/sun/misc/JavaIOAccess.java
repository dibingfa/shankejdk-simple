/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
 */

package sun.misc;
import java.io.Console;
import java.nio.charset.Charset;

public interface JavaIOAccess {
    public Console console();
    public Charset charset();
}
