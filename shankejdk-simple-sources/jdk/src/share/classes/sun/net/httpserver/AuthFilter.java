/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 */

package sun.net.httpserver;

import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import sun.net.www.MessageHeader;
import java.util.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;

public class AuthFilter extends Filter {

    private Authenticator authenticator;

    public AuthFilter (Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public String description () {
        return "Authentication filter";
    }

    public void setAuthenticator (Authenticator a) {
        authenticator = a;
    }

    public void consumeInput (HttpExchange t) throws IOException {
        InputStream i = t.getRequestBody();
        byte[] b = new byte [4096];
        while (i.read (b) != -1);
        i.close ();
    }

    /**
     * The filter's implementation, which is invoked by the server
     */
    public void doFilter (HttpExchange t, Filter.Chain chain) throws IOException
    {
        if (authenticator != null) {
            Authenticator.Result r = authenticator.authenticate (t);
            if (r instanceof Authenticator.Success) {
                Authenticator.Success s = (Authenticator.Success)r;
                ExchangeImpl e = ExchangeImpl.get (t);
                e.setPrincipal (s.getPrincipal());
                chain.doFilter (t);
            } else if (r instanceof Authenticator.Retry) {
                Authenticator.Retry ry = (Authenticator.Retry)r;
                consumeInput (t);
                t.sendResponseHeaders (ry.getResponseCode(), -1);
            } else if (r instanceof Authenticator.Failure) {
                Authenticator.Failure f = (Authenticator.Failure)r;
                consumeInput (t);
                t.sendResponseHeaders (f.getResponseCode(), -1);
            }
        } else {
            chain.doFilter (t);
        }
    }
}
