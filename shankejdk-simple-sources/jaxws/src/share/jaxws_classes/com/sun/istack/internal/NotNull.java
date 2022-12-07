/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.istack.internal;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Designates that a field, return value, argument, or a variable is guaranteed to be non-null.
 *
 * @author Kohsuke Kawaguchi
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,ElementType.LOCAL_VARIABLE})
public @interface NotNull {
}
