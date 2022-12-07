/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.crypto.provider;

import java.security.Key;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is a helper class which construct key objects
 * from encoded keys.
 *
 * @author Sharon Liu
 *
 */

final class ConstructKeys {
    /**
     * Construct a public key from its encoding.
     *
     * @param encodedKey the encoding of a public key.
     *
     * @param encodedKeyAlgorithm the algorithm the encodedKey is for.
     *
     * @return a public key constructed from the encodedKey.
     */
    private static final PublicKey constructPublicKey(byte[] encodedKey,
                                              String encodedKeyAlgorithm)
        throws InvalidKeyException, NoSuchAlgorithmException
    {
        PublicKey key = null;

        try {
            KeyFactory keyFactory =
                KeyFactory.getInstance(encodedKeyAlgorithm,
                    SunJCE.getInstance());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
            key = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException nsae) {
            // Try to see whether there is another
            // provider which supports this algorithm
            try {
                KeyFactory keyFactory =
                    KeyFactory.getInstance(encodedKeyAlgorithm);
                X509EncodedKeySpec keySpec =
                    new X509EncodedKeySpec(encodedKey);
                key = keyFactory.generatePublic(keySpec);
            } catch (NoSuchAlgorithmException nsae2) {
                throw new NoSuchAlgorithmException("No installed providers " +
                                                   "can create keys for the " +
                                                   encodedKeyAlgorithm +
                                                   "algorithm");
            } catch (InvalidKeySpecException ikse2) {
                InvalidKeyException ike =
                    new InvalidKeyException("Cannot construct public key");
                ike.initCause(ikse2);
                throw ike;
            }
        } catch (InvalidKeySpecException ikse) {
            InvalidKeyException ike =
                new InvalidKeyException("Cannot construct public key");
            ike.initCause(ikse);
            throw ike;
        }

        return key;
    }

    /**
     * Construct a private key from its encoding.
     *
     * @param encodedKey the encoding of a private key.
     *
     * @param encodedKeyAlgorithm the algorithm the wrapped key is for.
     *
     * @return a private key constructed from the encodedKey.
     */
    private static final PrivateKey constructPrivateKey(byte[] encodedKey,
                                                String encodedKeyAlgorithm)
        throws InvalidKeyException, NoSuchAlgorithmException
    {
        PrivateKey key = null;

        try {
            KeyFactory keyFactory =
                KeyFactory.getInstance(encodedKeyAlgorithm,
                    SunJCE.getInstance());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException nsae) {
            // Try to see whether there is another
            // provider which supports this algorithm
            try {
                KeyFactory keyFactory =
                    KeyFactory.getInstance(encodedKeyAlgorithm);
                PKCS8EncodedKeySpec keySpec =
                    new PKCS8EncodedKeySpec(encodedKey);
                key = keyFactory.generatePrivate(keySpec);
            } catch (NoSuchAlgorithmException nsae2) {
                throw new NoSuchAlgorithmException("No installed providers " +
                                                   "can create keys for the " +
                                                   encodedKeyAlgorithm +
                                                   "algorithm");
            } catch (InvalidKeySpecException ikse2) {
                InvalidKeyException ike =
                    new InvalidKeyException("Cannot construct private key");
                ike.initCause(ikse2);
                throw ike;
            }
        } catch (InvalidKeySpecException ikse) {
            InvalidKeyException ike =
                new InvalidKeyException("Cannot construct private key");
            ike.initCause(ikse);
            throw ike;
        }

        return key;
    }

    /**
     * Construct a secret key from its encoding.
     *
     * @param encodedKey the encoding of a secret key.
     *
     * @param encodedKeyAlgorithm the algorithm the secret key is for.
     *
     * @return a secret key constructed from the encodedKey.
     */
    private static final SecretKey constructSecretKey(byte[] encodedKey,
                                              String encodedKeyAlgorithm)
    {
        return (new SecretKeySpec(encodedKey, encodedKeyAlgorithm));
    }

    static final Key constructKey(byte[] encoding, String keyAlgorithm,
                                  int keyType)
        throws InvalidKeyException, NoSuchAlgorithmException {
        Key result = null;
        switch (keyType) {
        case Cipher.SECRET_KEY:
            result = ConstructKeys.constructSecretKey(encoding,
                                                      keyAlgorithm);
            break;
        case Cipher.PRIVATE_KEY:
            result = ConstructKeys.constructPrivateKey(encoding,
                                                       keyAlgorithm);
            break;
        case Cipher.PUBLIC_KEY:
            result = ConstructKeys.constructPublicKey(encoding,
                                                      keyAlgorithm);
            break;
        }
        return result;
    }
}
