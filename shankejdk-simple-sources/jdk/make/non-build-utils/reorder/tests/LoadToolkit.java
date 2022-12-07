/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 */


import java.awt.Toolkit;

public class LoadToolkit {

    public static void main(String[] args) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        // This starts a thread which never exits - so we suicide.
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        System.exit(0);
    }
}
