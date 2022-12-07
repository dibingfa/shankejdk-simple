/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator;

public class BootClassPathMinus {

    /*
     * return the default boot class path with all parts mentioned in arguments removed
     */
    public static void main(String[] args) {
    String bootClassPath = System.getProperty("sun.boot.class.path");
    StringBuffer newPath = new StringBuffer(bootClassPath.length());
    String[] bootClassPathParts = bootClassPath.split(java.io.File.pathSeparator, 0);
    for (String part : bootClassPathParts) {
        boolean found = false;
        for (String minus : args) {
        if (part.endsWith(minus)) {
            found = true;
        }
        }
        if (!found) {
        if (newPath.length() > 0) newPath.append(java.io.File.pathSeparatorChar);
        newPath.append(part);
        }
    }
    System.out.println(newPath.toString());
    }

}
