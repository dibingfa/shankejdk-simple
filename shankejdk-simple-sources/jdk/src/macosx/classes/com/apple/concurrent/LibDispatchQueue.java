/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.concurrent;

class LibDispatchQueue extends LibDispatchRetainedResource {
        LibDispatchQueue(final long queuePtr) {
                super(queuePtr);
        }
}
