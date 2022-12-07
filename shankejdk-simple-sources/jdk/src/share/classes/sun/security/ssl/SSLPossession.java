/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

interface SSLPossession {
    default byte[] encode() {
        return new byte[0];
    }
}
