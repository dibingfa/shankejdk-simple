/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
 */

/*
 *
 *  (C) Copyright IBM Corp. 1999 All Rights Reserved.
 *  Copyright 1997 The Open Group Research Institute.  All rights reserved.
 */

package sun.security.krb5.internal.crypto;

public class Nonce {

    public static synchronized int value() {
        return sun.security.krb5.Confounder.intValue() & 0x7fffffff;
    }

}
