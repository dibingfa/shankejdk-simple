/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.customizesecurityfile;

import java.io.*;

/**
 * Alters the crypto.policy security property
 * if --enable-unlimited-crypto is enabled.
 */
public class CryptoLevel {

    private static final String PROP_NAME = "crypto.policy";

    public static void main(String[] args) throws Exception {
        boolean fileModified = false;

        if (args.length < 3) {
            System.err.println("Usage: java CryptoLevel" +
                               "[input java.security file name] " +
                               "[output java.security file name] " +
                               "[unlimited|limited]");
            System.exit(1);
        }
        if (!args[2].equals("unlimited") && !args[2].equals("limited")) {
            System.err.println("CryptoLevel error: Unexpected " +
                "input: " + args[2]);
            System.exit(1);
        }

        try (FileReader fr = new FileReader(args[0]);
             BufferedReader br = new BufferedReader(fr);
             FileWriter fw = new FileWriter(args[1]);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            // parse the file line-by-line, looking for crypto.policy
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith('#' + PROP_NAME) ||
                    line.startsWith(PROP_NAME)) {
                    writeLine(bw, PROP_NAME + "=" + args[2]);
                    fileModified = true;
                } else {
                    writeLine(bw, line);
                }
                line = br.readLine();
            }
            if (!fileModified) {
                //no previous setting seen. Insert at end
                writeLine(bw, PROP_NAME + "=" + args[2]);
            }
            bw.flush();
        }
    }

    private static void writeLine(BufferedWriter bw, String line)
        throws IOException
    {
        bw.write(line);
        bw.newLine();
    }
}
