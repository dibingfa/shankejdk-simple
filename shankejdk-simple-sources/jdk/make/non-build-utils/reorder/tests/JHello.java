/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 */


import java.awt.*;
import java.io.PrintStream;
import javax.swing.*;

public class JHello extends JFrame {

    JHello() {
        JLabel jlabel = new JLabel("Hello");
        jlabel.setFont(new Font("Monospaced", 0, 144));
        getContentPane().add(jlabel);
        pack();
    }

    public static void main(String args[]) {
        new JHello().show();
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
        }
        System.exit(0);
    }
}
