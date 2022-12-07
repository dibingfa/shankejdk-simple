/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 */



public class IntToString {

    public static void main(String[] args) {
        String n = "10000";
        System.err.println("Hello");

        if (args.length == 0) {
            try {
                Thread.currentThread().sleep(Integer.parseInt(n));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
