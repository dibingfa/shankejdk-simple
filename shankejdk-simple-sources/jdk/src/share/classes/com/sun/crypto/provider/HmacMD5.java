/*
 * Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.crypto.provider;

import java.nio.ByteBuffer;

import javax.crypto.MacSpi;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.*;

/**
 * This is an implementation of the HMAC-MD5 algorithm.
 *
 * @author Jan Luehe
 */
public final class HmacMD5 extends HmacCore {
    /**
     * Standard constructor, creates a new HmacMD5 instance.
     */
    public HmacMD5() throws NoSuchAlgorithmException {
        super("MD5", 64);
    }
}
