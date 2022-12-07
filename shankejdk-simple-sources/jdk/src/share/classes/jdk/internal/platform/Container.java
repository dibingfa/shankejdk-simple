/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 */
package jdk.internal.platform;

/*
 * @author bobv
 */
public class Container {

    private Container() { }

    /**
     * Returns the platform specific Container Metrics class or
     * null if not supported on this platform.
     *
     * @return Metrics instance or null if not supported
     */
    public static Metrics metrics() {
        return Metrics.systemMetrics();
    }
}
