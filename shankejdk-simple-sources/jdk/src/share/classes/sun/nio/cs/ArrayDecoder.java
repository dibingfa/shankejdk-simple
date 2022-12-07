/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
 */

package sun.nio.cs;

/*
 * FastPath byte[]->char[] decoder, REPLACE on malformed or
 * unmappable input.
 */

public interface ArrayDecoder {
    int decode(byte[] src, int off, int len, char[] dst);
}
