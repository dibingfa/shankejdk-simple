/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package sun.management.snmp.jvmmib;

//
// Generated by mibgen version 5.0 (06/02/03) when compiling JVM-MANAGEMENT-MIB in standard metadata mode.
//

// java imports
//
import java.io.Serializable;
import java.util.Vector;

// jmx imports
//
import javax.management.MBeanServer;
import javax.management.ObjectName;
import com.sun.jmx.snmp.SnmpCounter;
import com.sun.jmx.snmp.SnmpCounter64;
import com.sun.jmx.snmp.SnmpGauge;
import com.sun.jmx.snmp.SnmpInt;
import com.sun.jmx.snmp.SnmpUnsignedInt;
import com.sun.jmx.snmp.SnmpIpAddress;
import com.sun.jmx.snmp.SnmpTimeticks;
import com.sun.jmx.snmp.SnmpOpaque;
import com.sun.jmx.snmp.SnmpString;
import com.sun.jmx.snmp.SnmpStringFixed;
import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpNull;
import com.sun.jmx.snmp.SnmpValue;
import com.sun.jmx.snmp.SnmpVarBind;
import com.sun.jmx.snmp.SnmpStatusException;

// jdmk imports
//
import com.sun.jmx.snmp.agent.SnmpIndex;
import com.sun.jmx.snmp.agent.SnmpMib;
import com.sun.jmx.snmp.agent.SnmpMibTable;
import com.sun.jmx.snmp.agent.SnmpMibSubRequest;
import com.sun.jmx.snmp.agent.SnmpStandardObjectServer;

/**
 * The class is used for implementing the "JvmRTInputArgsTable" group.
 * The group is defined with the following oid: 1.3.6.1.4.1.42.2.145.3.163.1.1.4.20.
 */
public class JvmRTInputArgsTableMeta extends SnmpMibTable implements Serializable {

    static final long serialVersionUID = 5395531763015738645L;
    /**
     * Constructor for the table. Initialize metadata for "JvmRTInputArgsTableMeta".
     * The reference on the MBean server is updated so the entries created through an SNMP SET will be AUTOMATICALLY REGISTERED in Java DMK.
     */
    public JvmRTInputArgsTableMeta(SnmpMib myMib, SnmpStandardObjectServer objserv) {
        super(myMib);
        objectserver = objserv;
    }


    /**
     * Factory method for "JvmRTInputArgsEntry" entry metadata class.
     *
     * You can redefine this method if you need to replace the default
     * generated metadata class with your own customized class.
     *
     * @param snmpEntryName Name of the SNMP Entry object (conceptual row) ("JvmRTInputArgsEntry")
     * @param tableName Name of the table in which the entries are registered ("JvmRTInputArgsTable")
     * @param mib The SnmpMib object in which this table is registered
     * @param server MBeanServer for this table entries (may be null)
     *
     * @return An instance of the metadata class generated for the
     *         "JvmRTInputArgsEntry" conceptual row (JvmRTInputArgsEntryMeta)
     *
     **/
    protected JvmRTInputArgsEntryMeta createJvmRTInputArgsEntryMetaNode(String snmpEntryName, String tableName, SnmpMib mib, MBeanServer server)  {
        return new JvmRTInputArgsEntryMeta(mib, objectserver);
    }


    // ------------------------------------------------------------
    //
    // Implements the "createNewEntry" method defined in "SnmpMibTable".
    // See the "SnmpMibTable" Javadoc API for more details.
    //
    // ------------------------------------------------------------

    public void createNewEntry(SnmpMibSubRequest req, SnmpOid rowOid, int depth)
        throws SnmpStatusException {
        if (factory != null)
            factory.createNewEntry(req, rowOid, depth, this);
        else
            throw new SnmpStatusException(
                SnmpStatusException.snmpRspNoAccess);
    }



    // ------------------------------------------------------------
    //
    // Implements the "isRegistrationRequired" method defined in "SnmpMibTable".
    // See the "SnmpMibTable" Javadoc API for more details.
    //
    // ------------------------------------------------------------

    public boolean isRegistrationRequired()  {
        return false;
    }



    public void registerEntryNode(SnmpMib mib, MBeanServer server)  {
        node = createJvmRTInputArgsEntryMetaNode("JvmRTInputArgsEntry", "JvmRTInputArgsTable", mib, server);
    }


    // ------------------------------------------------------------
    //
    // Implements the "addEntry" method defined in "SnmpMibTable".
    // See the "SnmpMibTable" Javadoc API for more details.
    //
    // ------------------------------------------------------------

    public synchronized void addEntry(SnmpOid rowOid, ObjectName objname,
                 Object entry)
        throws SnmpStatusException {
        if (! (entry instanceof JvmRTInputArgsEntryMBean) )
            throw new ClassCastException("Entries for Table \"" +
                           "JvmRTInputArgsTable" + "\" must implement the \"" +
                           "JvmRTInputArgsEntryMBean" + "\" interface.");
        super.addEntry(rowOid, objname, entry);
    }


    // ------------------------------------------------------------
    //
    // Implements the "get" method defined in "SnmpMibTable".
    // See the "SnmpMibTable" Javadoc API for more details.
    //
    // ------------------------------------------------------------

    public void get(SnmpMibSubRequest req, SnmpOid rowOid, int depth)
        throws SnmpStatusException {
        JvmRTInputArgsEntryMBean entry = (JvmRTInputArgsEntryMBean) getEntry(rowOid);
        synchronized (this) {
            node.setInstance(entry);
            node.get(req,depth);
        }
    }

    // ------------------------------------------------------------
    //
    // Implements the "set" method defined in "SnmpMibTable".
    // See the "SnmpMibTable" Javadoc API for more details.
    //
    // ------------------------------------------------------------

    public void set(SnmpMibSubRequest req, SnmpOid rowOid, int depth)
        throws SnmpStatusException {
        if (req.getSize() == 0) return;

        JvmRTInputArgsEntryMBean entry = (JvmRTInputArgsEntryMBean) getEntry(rowOid);
        synchronized (this) {
            node.setInstance(entry);
            node.set(req,depth);
        }
    }

    // ------------------------------------------------------------
    //
    // Implements the "check" method defined in "SnmpMibTable".
    // See the "SnmpMibTable" Javadoc API for more details.
    //
    // ------------------------------------------------------------

    public void check(SnmpMibSubRequest req, SnmpOid rowOid, int depth)
        throws SnmpStatusException {
        if (req.getSize() == 0) return;

        JvmRTInputArgsEntryMBean entry = (JvmRTInputArgsEntryMBean) getEntry(rowOid);
        synchronized (this) {
            node.setInstance(entry);
            node.check(req,depth);
        }
    }

    /**
     * check that the given "var" identifies a columnar object.
     */
    public void validateVarEntryId( SnmpOid rowOid, long var, Object data )
        throws SnmpStatusException {
        node.validateVarId(var, data);
    }

    /**
     * Returns true if "var" identifies a readable scalar object.
     */
    public boolean isReadableEntryId( SnmpOid rowOid, long var, Object data )
        throws SnmpStatusException {
        return node.isReadable(var);
    }

    /**
     * Returns the arc of the next columnar object following "var".
     */
    public long getNextVarEntryId( SnmpOid rowOid, long var, Object data )
        throws SnmpStatusException {
        long nextvar = node.getNextVarId(var, data);
        while (!isReadableEntryId(rowOid, nextvar, data))
            nextvar = node.getNextVarId(nextvar, data);
        return nextvar;
    }

    // ------------------------------------------------------------
    //
    // Implements the "skipEntryVariable" method defined in "SnmpMibTable".
    // See the "SnmpMibTable" Javadoc API for more details.
    //
    // ------------------------------------------------------------

    public boolean skipEntryVariable( SnmpOid rowOid, long var, Object data, int pduVersion) {
        try {
            JvmRTInputArgsEntryMBean entry = (JvmRTInputArgsEntryMBean) getEntry(rowOid);
            synchronized (this) {
                node.setInstance(entry);
                return node.skipVariable(var, data, pduVersion);
            }
        } catch (SnmpStatusException x) {
            return false;
        }
    }


    /**
     * Reference to the entry metadata.
     */
    private JvmRTInputArgsEntryMeta node;

    /**
     * Reference to the object server.
     */
    protected SnmpStandardObjectServer objectserver;

}
