/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

/**
 * Client authentication type.
 */
enum ClientAuthType {
    CLIENT_AUTH_NONE,           // turn off client authentication
    CLIENT_AUTH_REQUESTED,      // need to request client authentication
    CLIENT_AUTH_REQUIRED        // require client authentication
}

