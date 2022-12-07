/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;


public class SEL {
    static native long getSelectorPtr(String selectorName);
    static native String getSelectorName(long ptr);

    final long selPtr;

    SEL(long ptr) {
        this.selPtr = ptr;
    }

    public SEL(final String name) {
        this(getSelectorPtr(name));
    }

    @Override public String toString(){
        return ((int)selPtr) + " / " + selPtr + " : " + getSelectorName(selPtr);
    }

    /**
     * Converts something like "performSelectorOnMainThread_withObject_wait"
     * to "performSelectorOnMainThread:withObject:wait:"
     */
    public static String selectorName(String jMethodName, boolean hasArgs){
        String b = jMethodName.replaceAll("_", ":");
        return hasArgs ? b + ":" : b;
    }

    public static String jMethodName(String selectorName){
        return selectorName.replaceAll(":", "_").replaceAll("_$", "");
    }

    public static boolean validName(String selectorName){
        return selectorName.matches("^[a-zA-Z_][a-zA-Z0-9_:]*$");
    }
}
