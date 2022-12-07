/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.charsetmapping;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws Exception {
        if (args.length < 3 ) {
            System.out.println("Usage: java -jar charsetmapping.jar src dst mType [copyrightSrc]");
            System.exit(1);
        }
        if ("sbcs".equals(args[2]) || "extsbcs".equals(args[2])) {
            SBCS.genClass(args);
        } else if ("dbcs".equals(args[2])) {
            DBCS.genClass(args);
        } else if ("euctw".equals(args[2])) {
            EUC_TW.genClass(args);
        } else if ("sjis0213".equals(args[2])) {
            JIS0213.genClass(args);
        } else if ("hkscs".equals(args[2])) {
            HKSCS.genClass(args);
        }
    }
}
