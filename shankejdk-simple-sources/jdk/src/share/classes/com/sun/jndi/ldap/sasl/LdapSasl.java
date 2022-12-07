/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.jndi.ldap.sasl;

import java.io.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.NamingException;

import javax.naming.ldap.Control;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.*;
import com.sun.jndi.ldap.Connection;
import com.sun.jndi.ldap.LdapClient;
import com.sun.jndi.ldap.LdapResult;

/**
  * Handles SASL support.
  *
  * @author Vincent Ryan
  * @author Rosanna Lee
  */

final public class LdapSasl {
    // SASL stuff
    private static final String SASL_CALLBACK = "java.naming.security.sasl.callback";
    private static final String SASL_AUTHZ_ID =
        "java.naming.security.sasl.authorizationId";
    private static final String SASL_REALM =
        "java.naming.security.sasl.realm";

    private static final int LDAP_SUCCESS = 0;
    private static final int LDAP_SASL_BIND_IN_PROGRESS = 14;   // LDAPv3

    private LdapSasl() {
    }

    /**
     * Performs SASL bind.
     * Creates a SaslClient by using a default CallbackHandler
     * that uses the Context.SECURITY_PRINCIPAL and Context.SECURITY_CREDENTIALS
     * properties to satisfy the callbacks, and by using the
     * SASL_AUTHZ_ID property as the authorization id. If the SASL_AUTHZ_ID
     * property has not been set, Context.SECURITY_PRINCIPAL is used.
     * If SASL_CALLBACK has been set, use that instead of the default
     * CallbackHandler.
     *<p>
     * If bind is successful and the selected SASL mechanism has a security
     * layer, set inStream and outStream to be filter streams that use
     * the security layer. These will be used for subsequent communication
     * with the server.
     *<p>
     * @param conn The non-null connection to use for sending an LDAP BIND
     * @param server Non-null string name of host to connect to
     * @param dn Non-null DN to bind as; also used as authentication ID
     * @param pw Possibly null password; can be byte[], char[] or String
     * @param authMech A non-null space-separated list of SASL authentication
     *        mechanisms.
     * @param env The possibly null environment of the context, possibly containing
     *        properties for used by SASL mechanisms
     * @param bindCtls The possibly null controls to accompany the bind
     * @return LdapResult containing status of the bind
     */
    @SuppressWarnings("unchecked")
    public static LdapResult saslBind(LdapClient clnt, Connection conn,
        String server, String dn, Object pw,
        String authMech, Hashtable<?,?> env, Control[] bindCtls)
        throws IOException, NamingException {

        SaslClient saslClnt = null;
        boolean cleanupHandler = false;

        // Use supplied callback handler or create default
        CallbackHandler cbh =
            (env != null) ? (CallbackHandler)env.get(SASL_CALLBACK) : null;
        if (cbh == null) {
            cbh = new DefaultCallbackHandler(dn, pw, (String)env.get(SASL_REALM));
            cleanupHandler = true;
        }

        // Prepare parameters for creating SASL client
        String authzId = (env != null) ? (String)env.get(SASL_AUTHZ_ID) : null;
        String[] mechs = getSaslMechanismNames(authMech);

        try {
            // Create SASL client to use using SASL package
            saslClnt = Sasl.createSaslClient(
                mechs, authzId, "ldap", server, (Hashtable<String, ?>)env, cbh);

            if (saslClnt == null) {
                throw new AuthenticationNotSupportedException(authMech);
            }

            LdapResult res;
            String mechName = saslClnt.getMechanismName();
            byte[] response = saslClnt.hasInitialResponse() ?
                saslClnt.evaluateChallenge(NO_BYTES) : null;

            res = clnt.ldapBind(null, response, bindCtls, mechName, true);

            while (!saslClnt.isComplete() &&
                (res.status == LDAP_SASL_BIND_IN_PROGRESS ||
                 res.status == LDAP_SUCCESS)) {

                response = saslClnt.evaluateChallenge(
                    res.serverCreds != null? res.serverCreds : NO_BYTES);
                if (res.status == LDAP_SUCCESS) {
                    if (response != null) {
                        throw new AuthenticationException(
                            "SASL client generated response after success");
                    }
                    break;
                }
                res = clnt.ldapBind(null, response, bindCtls, mechName, true);
            }

            if (res.status == LDAP_SUCCESS) {
                if (!saslClnt.isComplete()) {
                    throw new AuthenticationException(
                        "SASL authentication not complete despite server claims");
                }

                String qop = (String) saslClnt.getNegotiatedProperty(Sasl.QOP);

                // If negotiated integrity or privacy,
                if (qop != null && (qop.equalsIgnoreCase("auth-int")
                    || qop.equalsIgnoreCase("auth-conf"))) {

                    InputStream newIn = new SaslInputStream(saslClnt,
                        conn.inStream);
                    OutputStream newOut = new SaslOutputStream(saslClnt,
                        conn.outStream);

                    conn.replaceStreams(newIn, newOut);
                } else {
                    saslClnt.dispose();
                }
            }
            return res;
        } catch (SaslException e) {
            NamingException ne = new AuthenticationException(
                authMech);
            ne.setRootCause(e);
            throw ne;
        } finally {
            if (cleanupHandler) {
                ((DefaultCallbackHandler)cbh).clearPassword();
            }
        }
    }

    /**
      * Returns an array of SASL mechanisms given a string of space
      * separated SASL mechanism names.
      * @param The non-null string containing the mechanism names
      * @return A non-null array of String; each element of the array
      * contains a single mechanism name.
      */
    private static String[] getSaslMechanismNames(String str) {
        StringTokenizer parser = new StringTokenizer(str);
        Vector<String> mechs = new Vector<>(10);
        while (parser.hasMoreTokens()) {
            mechs.addElement(parser.nextToken());
        }
        String[] mechNames = new String[mechs.size()];
        for (int i = 0; i < mechs.size(); i++) {
            mechNames[i] = mechs.elementAt(i);
        }
        return mechNames;
    }

    private static final byte[] NO_BYTES = new byte[0];
}
