/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.api.pipe;

/**
 * Factory for (@link FiberContextSwitchInterceptor} instances
 *
 * @since 2.2.6
 */
public interface FiberContextSwitchInterceptorFactory {
        /**
         * Creates {@link FiberContextSwitchInterceptor} instance.
         * @return interceptor instance
         */
        public FiberContextSwitchInterceptor create();
}
