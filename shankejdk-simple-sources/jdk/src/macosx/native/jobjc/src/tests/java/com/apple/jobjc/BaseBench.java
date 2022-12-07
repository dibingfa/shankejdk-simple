/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import sun.misc.Unsafe;

public class BaseBench extends PooledTestCase {
    protected final static JObjCRuntime RUNTIME = JObjCRuntime.getInstance();
    protected final static JObjC JOBJC = JObjC.getInstance();
    protected final static Unsafe UNSAFE = JObjCRuntime.getInstance().unsafe;
    protected final static NativeArgumentBuffer ARGS = JObjCRuntime.getInstance().getThreadLocalState();

    public abstract static class Task{
        final String name;
        public Task(String name){ this.name = name; }
        public abstract void run();
    }

    public void bench(final String title, final long warmup, final long runs, final long iterations, final Task... tasks){
        final long[] runtimes = new long[tasks.length];

        for(int t = 0; t < tasks.length; ++t){
            long runtime = 0;
            for(int i = 0; i < warmup; ++i)
                singleBench(iterations, tasks[t]);
            for(int i = 0; i < runs; ++i)
                runtime = runtime + singleBench(iterations, tasks[t]);
            runtimes[t] = runtime;
        }

        final float[] relatives = new float[tasks.length];

        for(int t = 0; t < tasks.length; ++t)
            relatives[t] = ((float) runtimes[t] / (float) runs);

        float min = relatives[0];
        for(float t : relatives)
            if(t < min)
                min = t;

        for(int t = 0; t < tasks.length; ++t)
            relatives[t] = relatives[t] / min;

        System.out.format("\n* %1$s\n", title);
        for(int t = 0; t < tasks.length; ++t)
            System.out.format("%1$60s : %2$.1f\n", tasks[t].name, relatives[t]);
    }

    public long singleBench(final long iterations, final Task task){
        long start = System.currentTimeMillis();
        for(long i = 0; i < iterations; ++i)
            task.run();
        long end = System.currentTimeMillis();
        return end - start;
    }
}
