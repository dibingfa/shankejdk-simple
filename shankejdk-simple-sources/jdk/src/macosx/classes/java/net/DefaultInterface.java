/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package java.net;

/**
 * Choose a network interface to be the default for
 * outgoing IPv6 traffic that does not specify a scope_id (and which needs one).
 * We choose the first interface that is up and is (in order of preference):
 * 1. neither loopback nor point to point
 * 2. point to point
 * 3. loopback
 * 4. none.
 * Platforms that do not require a default interface implement a dummy
 * that returns null.
 */

import java.util.Enumeration;
import java.io.IOException;

class DefaultInterface {

    private final static NetworkInterface defaultInterface =
        chooseDefaultInterface();

    static NetworkInterface getDefault() {
        return defaultInterface;
    }

    /**
     * Choose a default interface. This method returns an interface that is
     * both "up" and supports multicast. This method choses an interface in
     * order of preference:
     * 1. neither loopback nor point to point
     * 2. point to point
     * 3. loopback
     *
     * @return  the chosen interface or {@code null} if there isn't a suitable
     *          default
     */
    private static NetworkInterface chooseDefaultInterface() {
        Enumeration<NetworkInterface> nifs;

        try {
           nifs = NetworkInterface.getNetworkInterfaces();
        } catch (IOException ignore) {
            // unable to enumate network interfaces
            return null;
        }

        NetworkInterface ppp = null;
        NetworkInterface loopback = null;

        while (nifs.hasMoreElements()) {
            NetworkInterface ni = nifs.nextElement();
            try {
                if (ni.isUp() && ni.supportsMulticast()) {
                    boolean isLoopback = ni.isLoopback();
                    boolean isPPP = ni.isPointToPoint();
                    if (!isLoopback && !isPPP) {
                        // found an interface that is not the loopback or a
                        // point-to-point interface
                        return ni;
                    }
                    if (ppp == null && isPPP)
                        ppp = ni;
                    if (loopback == null && isLoopback)
                        loopback = ni;
                }
            } catch (IOException skip) { }
        }

        return (ppp != null) ? ppp : loopback;
    }
}
