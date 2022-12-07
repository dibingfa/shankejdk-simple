/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;

interface SSLKeyAgreementGenerator {
    SSLKeyDerivation createKeyDerivation(
            HandshakeContext context) throws IOException;
}
