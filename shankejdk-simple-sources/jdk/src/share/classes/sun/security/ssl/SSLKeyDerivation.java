/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.ssl;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;

interface SSLKeyDerivation {
    SecretKey deriveKey(String algorithm,
            AlgorithmParameterSpec params) throws IOException;
}
