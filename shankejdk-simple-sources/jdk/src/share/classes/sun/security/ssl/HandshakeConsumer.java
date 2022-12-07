/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;
import sun.security.ssl.SSLHandshake.HandshakeMessage;

interface HandshakeConsumer {
    // message: the handshake message to be consumed.
    void consume(ConnectionContext context,
            HandshakeMessage message) throws IOException;
}
