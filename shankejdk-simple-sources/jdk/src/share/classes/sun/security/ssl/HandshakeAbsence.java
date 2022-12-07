/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;
import sun.security.ssl.SSLHandshake.HandshakeMessage;

/**
 * Interface for handshake message or extension absence on handshake
 * message processing.
 *
 * This is typically used after the SSLSession object created, so that the
 * extension can update/impact the session object.
 */
interface HandshakeAbsence {
    void absent(ConnectionContext context,
            HandshakeMessage message) throws IOException;
}

