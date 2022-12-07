/*
 * Copyright (c) 1994, 2003, Oracle and/or its affiliates. All rights reserved.
 */

package sun.tools.java;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * This class is used to represent an attribute from a binary class.
 * This class should go away once arrays are objects.
 *
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public final
class BinaryAttribute implements Constants {
    Identifier name;
    byte data[];
    BinaryAttribute next;

    /**
     * Constructor
     */
    BinaryAttribute(Identifier name, byte data[], BinaryAttribute next) {
        this.name = name;
        this.data = data;
        this.next = next;
    }

    /**
     * Load a list of attributes
     */
    public static BinaryAttribute load(DataInputStream in, BinaryConstantPool cpool, int mask) throws IOException {
        BinaryAttribute atts = null;
        int natt = in.readUnsignedShort();  // JVM 4.6 method_info.attrutes_count

        for (int i = 0 ; i < natt ; i++) {
            // id from JVM 4.7 attribute_info.attribute_name_index
            Identifier id = cpool.getIdentifier(in.readUnsignedShort());
            // id from JVM 4.7 attribute_info.attribute_length
            int len = in.readInt();

            if (id.equals(idCode) && ((mask & ATT_CODE) == 0)) {
                in.skipBytes(len);
            } else {
                byte data[] = new byte[len];
                in.readFully(data);
                atts = new BinaryAttribute(id, data, atts);
            }
        }
        return atts;
    }

    // write out the Binary attributes to the given stream
    // (note that attributes may be null)
    static void write(BinaryAttribute attributes, DataOutputStream out,
                      BinaryConstantPool cpool, Environment env) throws IOException {
        // count the number of attributes
        int attributeCount = 0;
        for (BinaryAttribute att = attributes; att != null; att = att.next)
            attributeCount++;
        out.writeShort(attributeCount);

        // write out each attribute
        for (BinaryAttribute att = attributes; att != null; att = att.next) {
            Identifier name = att.name;
            byte data[] = att.data;
            // write the identifier
            out.writeShort(cpool.indexString(name.toString(), env));
            // write the length
            out.writeInt(data.length);
            // write the data
            out.write(data, 0, data.length);
        }
    }

    /**
     * Accessors
     */

    public Identifier getName() { return name; }

    public byte getData()[] { return data; }

    public BinaryAttribute getNextAttribute() { return next; }

}
