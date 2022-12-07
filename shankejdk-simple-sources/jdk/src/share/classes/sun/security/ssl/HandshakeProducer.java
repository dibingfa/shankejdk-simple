/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;
import sun.security.ssl.SSLHandshake.HandshakeMessage;

interface HandshakeProducer {
    // return the encoded producing if it has not been dumped to the context
    //
    // message: the handshake message responded to, can be null for producing
    //          of kickstart handshake message
    byte[] produce(ConnectionContext context,
            HandshakeMessage message) throws IOException;
}
