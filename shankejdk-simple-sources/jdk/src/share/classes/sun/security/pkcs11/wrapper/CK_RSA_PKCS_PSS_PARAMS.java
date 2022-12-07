/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 */

package sun.security.pkcs11.wrapper;

import java.security.ProviderException;
import java.security.spec.PSSParameterSpec;
import java.security.spec.MGF1ParameterSpec;

/**
 * This class represents the necessary parameters required by the
 * CKM_RSA_PKCS_PSS mechanism as defined in CK_RSA_PKCS_PSS_PARAMS structure.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 * typedef struct CK_RSA_PKCS_PSS_PARAMS {
 *    CK_MECHANISM_TYPE    hashAlg;
 *    CK_RSA_PKCS_MGF_TYPE mgf;
 *    CK_ULONG             sLen;
 * } CK_RSA_PKCS_PSS_PARAMS;
 * </PRE>
 *
 * @since   13
 */
public class CK_RSA_PKCS_PSS_PARAMS {

    private final long hashAlg;
    private final long mgf;
    private final long sLen;

    public CK_RSA_PKCS_PSS_PARAMS(String hashAlg, String mgfAlg,
            String mgfHash, int sLen) {
        this.hashAlg = Functions.getHashMechId(hashAlg);
        if (!mgfAlg.equals("MGF1")) {
            throw new ProviderException("Only MGF1 is supported");
        }
        // no dash in PKCS#11 mechanism names
        this.mgf = Functions.getMGFId("CKG_MGF1_" + hashAlg.replaceFirst("-", ""));
        this.sLen = sLen;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof CK_RSA_PKCS_PSS_PARAMS)) {
            return false;
        }

        CK_RSA_PKCS_PSS_PARAMS other = (CK_RSA_PKCS_PSS_PARAMS) o;
        return ((other.hashAlg == hashAlg) &&
                (other.mgf == mgf) &&
                (other.sLen == sLen));
    }

    @Override
    public int hashCode() {
        return (int)(hashAlg << 2 + mgf << 1 + sLen);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(Constants.INDENT);
        buffer.append("hashAlg: ");
        buffer.append(Functions.toFullHexString(hashAlg));
        buffer.append(Constants.NEWLINE);

        buffer.append(Constants.INDENT);
        buffer.append("mgf: ");
        buffer.append(Functions.toFullHexString(mgf));
        buffer.append(Constants.NEWLINE);

        buffer.append(Constants.INDENT);
        buffer.append("sLen(in bytes): ");
        buffer.append(sLen);

        return buffer.toString();
    }
}
