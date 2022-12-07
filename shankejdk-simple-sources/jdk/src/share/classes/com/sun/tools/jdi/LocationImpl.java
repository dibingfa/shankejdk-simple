/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.jdi;

import com.sun.jdi.*;

import java.util.*;

public class LocationImpl extends MirrorImpl implements Location {
    private final ReferenceTypeImpl declaringType;
    private Method method;
    private long methodRef;
    private long codeIndex;
    private LineInfo baseLineInfo = null;
    private LineInfo otherLineInfo = null;

    LocationImpl(VirtualMachine vm,
                 Method method, long codeIndex) {
        super(vm);

        this.method = method;
        this.codeIndex = method.isNative()? -1 : codeIndex;
        this.declaringType = (ReferenceTypeImpl)method.declaringType();
    }

    /*
     * This constructor allows lazy creation of the method mirror. This
     * can be a performance savings if the method mirror does not yet
     * exist.
     */
    LocationImpl(VirtualMachine vm, ReferenceTypeImpl declaringType,
                 long methodRef, long codeIndex) {
        super(vm);

        this.method = null;
        this.codeIndex = codeIndex;
        this.declaringType = declaringType;
        this.methodRef = methodRef;
    }

    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof Location)) {
            Location other = (Location)obj;
            return (method().equals(other.method())) &&
                   (codeIndex() == other.codeIndex()) &&
                   super.equals(obj);
        } else {
            return false;
        }
    }

    public int hashCode() {
        /*
         * TO DO: better hash code?
         */
        return method().hashCode() + (int)codeIndex();
    }

    public int compareTo(Location object) {
        LocationImpl other = (LocationImpl)object;
        int rc = method().compareTo(other.method());
        if (rc == 0) {
            long diff = codeIndex() - other.codeIndex();
            if (diff < 0)
                return -1;
            else if (diff > 0)
                return 1;
            else
                return 0;
        }
        return rc;
    }

    public ReferenceType declaringType() {
        return declaringType;
    }

    public Method method() {
        if (method == null) {
            method = declaringType.getMethodMirror(methodRef);
            if (method.isNative()) {
                codeIndex = -1;
            }
        }
        return method;
    }

    public long codeIndex() {
        method();  // be sure information is up-to-date
        return codeIndex;
    }

    LineInfo getBaseLineInfo(SDE.Stratum stratum) {
        LineInfo lineInfo;

        /* check if there is cached info to use */
        if (baseLineInfo != null) {
            return baseLineInfo;
        }

        /* compute the line info */
        MethodImpl methodImpl = (MethodImpl)method();
        lineInfo = methodImpl.codeIndexToLineInfo(stratum,
                                                  codeIndex());

        /* cache it */
        addBaseLineInfo(lineInfo);

        return lineInfo;
    }

    LineInfo getLineInfo(SDE.Stratum stratum) {
        LineInfo lineInfo;

        /* base stratum is done slighly differently */
        if (stratum.isJava()) {
            return getBaseLineInfo(stratum);
        }

        /* check if there is cached info to use */
        lineInfo = otherLineInfo; // copy because of concurrency
        if (lineInfo != null &&
                           stratum.id().equals(lineInfo.liStratum())) {
            return lineInfo;
        }

        int baseLineNumber = lineNumber(SDE.BASE_STRATUM_NAME);
        SDE.LineStratum lineStratum =
                  stratum.lineStratum(declaringType, baseLineNumber);

        if (lineStratum != null && lineStratum.lineNumber() != -1) {
            lineInfo = new StratumLineInfo(stratum.id(),
                                           lineStratum.lineNumber(),
                                           lineStratum.sourceName(),
                                           lineStratum.sourcePath());
        } else {
            /* find best match */
            MethodImpl methodImpl = (MethodImpl)method();
            lineInfo = methodImpl.codeIndexToLineInfo(stratum,
                                                      codeIndex());
        }

        /* cache it */
        addStratumLineInfo(lineInfo);

        return lineInfo;
    }

    void addStratumLineInfo(LineInfo lineInfo) {
        otherLineInfo = lineInfo;
    }

    void addBaseLineInfo(LineInfo lineInfo) {
        baseLineInfo = lineInfo;
    }

    public String sourceName() throws AbsentInformationException {
        return sourceName(vm.getDefaultStratum());
    }

    public String sourceName(String stratumID)
                               throws AbsentInformationException {
        return sourceName(declaringType.stratum(stratumID));
    }

    String sourceName(SDE.Stratum stratum)
                               throws AbsentInformationException {
        return getLineInfo(stratum).liSourceName();
    }

    public String sourcePath() throws AbsentInformationException {
        return sourcePath(vm.getDefaultStratum());
    }

    public String sourcePath(String stratumID)
                               throws AbsentInformationException {
        return sourcePath(declaringType.stratum(stratumID));
    }

    String sourcePath(SDE.Stratum stratum)
                               throws AbsentInformationException {
        return getLineInfo(stratum).liSourcePath();
    }

    public int lineNumber() {
        return lineNumber(vm.getDefaultStratum());
    }

    public int lineNumber(String stratumID) {
        return lineNumber(declaringType.stratum(stratumID));
    }

    int lineNumber(SDE.Stratum stratum) {
        return getLineInfo(stratum).liLineNumber();
    }

    public String toString() {
        if (lineNumber() == -1) {
            return method().toString() + "+" + codeIndex();
        } else {
            return declaringType().name() + ":" + lineNumber();
        }
    }
}
