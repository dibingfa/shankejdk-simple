/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.icondata.osxapp;

import java.io.*;

public class ToBin {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];

        while ((nRead = System.in.read(data, 0, data.length)) != -1) {
            baos.write(data, 0, nRead);
        }

        baos.flush();

        byte[] buf = baos.toByteArray();
        for (int i = 0; i < buf.length; i++) {
            System.out.print(String.format("0x%1$02X", buf[i]) + ", ");
            if (i % 20 == 0) {
                System.out.println();
            }
        }
    }
}
