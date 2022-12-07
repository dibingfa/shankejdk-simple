/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import java.util.concurrent.Callable;

import com.apple.jobjc.foundation.NSString;

public class UtilsTest extends PooledTestCase{
    public void testStrings(){
        String s = "fooBarBazDazzle";
        NSString ns = Utils.get().strings().nsString(s);
        String t = Utils.get().strings().javaString(ns);
        assertEquals(s, t);
    }

    public void testThreadsPerformRunnableOnMainThread(){
        final long testThreadId = Thread.currentThread().getId();
        class Wrap{ public long x = testThreadId; }
        final Wrap wrap = new Wrap();
        assertTrue(testThreadId == wrap.x);

        Utils.get().threads().performOnMainThread(new Runnable(){
            public void run() {
                wrap.x = Thread.currentThread().getId();
            }
        }, true);

        assertTrue(testThreadId != wrap.x);
    }

    public void testThreadsPerformCallableOnMainThread() throws Exception{
        final long testThreadId = Thread.currentThread().getId();
        final long mainThreadId = Utils.get().threads().performOnMainThread(new Callable<Long>(){
            public Long call() { return Thread.currentThread().getId(); }
        });
        assertTrue(testThreadId != mainThreadId);
    }

    public void testThreadsPerformCallableOnMainThreadException() throws Exception{
        class FooException extends RuntimeException{}
        try {
            Utils.get().threads().performOnMainThread(new Callable<Object>(){
                public Object call() { throw new FooException(); }
            });
        } catch (FooException e) {
            return;
        }
        fail("Failed to catch exception.");
    }

    public static void main(String[] args){
        junit.textui.TestRunner.run(UtilsTest.class);
    }
}

