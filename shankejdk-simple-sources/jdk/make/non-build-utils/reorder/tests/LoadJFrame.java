/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 */


import javax.swing.JFrame;

public class LoadJFrame {

    public static void main(String[] args) {
        new JFrame().show();
        // This starts a thread which never exits - so we suicide.
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
        }
        System.exit(0);
    }
}
