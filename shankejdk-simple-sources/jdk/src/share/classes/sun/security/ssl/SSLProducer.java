/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;

interface SSLProducer {
    // return the encoded producing if it has not been dumped to the context
    byte[] produce(ConnectionContext context) throws IOException;
}
