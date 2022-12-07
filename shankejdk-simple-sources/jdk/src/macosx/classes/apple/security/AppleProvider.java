/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package apple.security;

import java.security.*;

/**
 * The Apple Security Provider.
 */

/**
 * Defines the Apple provider.
 *
 * This provider only exists to provide access to the Apple keychain-based KeyStore implementation
 */

public final class AppleProvider extends Provider {

    private static final String info = "Apple Provider";

    public AppleProvider() {
        /* We are the Apple provider */
        super("Apple", 1.8d, info);

        AccessController.<Object>doPrivileged(new java.security.PrivilegedAction<Object>() {
            public Object run() {

                /*
                 * KeyStore
                 */
                put("KeyStore.KeychainStore", "apple.security.KeychainStore");

                return null;
            }
        });
    }
}
