/*
 * Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.jfr.internal.instrument;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface JITypeMapping {
    String from();
    String to();
}
