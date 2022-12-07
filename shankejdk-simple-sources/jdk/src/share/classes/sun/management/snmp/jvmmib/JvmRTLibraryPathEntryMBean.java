/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 */

package sun.management.snmp.jvmmib;

//
// Generated by mibgen version 5.0 (06/02/03) when compiling JVM-MANAGEMENT-MIB in standard metadata mode.
//


// jmx imports
//
import com.sun.jmx.snmp.SnmpStatusException;

/**
 * This interface is used for representing the remote management interface for the "JvmRTLibraryPathEntry" MBean.
 */
public interface JvmRTLibraryPathEntryMBean {

    /**
     * Getter for the "JvmRTLibraryPathItem" variable.
     */
    public String getJvmRTLibraryPathItem() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTLibraryPathIndex" variable.
     */
    public Integer getJvmRTLibraryPathIndex() throws SnmpStatusException;

}
