/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
 */
package sun.net.www.protocol.http;

/* Authentication schemes supported by the http implementation. New schemes, if
 * supported, should be defined here.
 */
public enum AuthScheme {
    BASIC,
    DIGEST,
    NTLM,
    NEGOTIATE,
    KERBEROS,
    UNKNOWN;
}

