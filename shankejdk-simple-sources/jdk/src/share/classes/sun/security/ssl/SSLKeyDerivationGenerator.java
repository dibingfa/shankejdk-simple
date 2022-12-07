/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;
import javax.crypto.SecretKey;

interface SSLKeyDerivationGenerator {
    SSLKeyDerivation createKeyDerivation(HandshakeContext context,
            SecretKey secretKey) throws IOException;
}
