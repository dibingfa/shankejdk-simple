/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.tools.jdeps;

import com.sun.tools.classfile.Dependency.Location;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents the source of the class files.
 */
public class Archive {
    public static Archive getInstance(Path p) throws IOException {
        return new Archive(p, ClassFileReader.newInstance(p));
    }

    private final Path path;
    private final String filename;
    private final ClassFileReader reader;
    protected Map<Location, Set<Location>> deps = new ConcurrentHashMap<>();

    protected Archive(String name) {
        this.path = null;
        this.filename = name;
        this.reader = null;
    }

    protected Archive(Path p, ClassFileReader reader) {
        this.path = p;
        this.filename = path.getFileName().toString();
        this.reader = reader;
    }

    public ClassFileReader reader() {
        return reader;
    }

    public String getName() {
        return filename;
    }

    public void addClass(Location origin) {
        Set<Location> set = deps.get(origin);
        if (set == null) {
            set = new HashSet<>();
            deps.put(origin, set);
        }
    }

    public void addClass(Location origin, Location target) {
        Set<Location> set = deps.get(origin);
        if (set == null) {
            set = new HashSet<>();
            deps.put(origin, set);
        }
        set.add(target);
    }

    public Set<Location> getClasses() {
        return deps.keySet();
    }

    public void visitDependences(Visitor v) {
        for (Map.Entry<Location,Set<Location>> e: deps.entrySet()) {
            for (Location target : e.getValue()) {
                v.visit(e.getKey(), target);
            }
        }
    }

    public boolean isEmpty() {
        return getClasses().isEmpty();
    }

    public String getPathName() {
        return path != null ? path.toString() : filename;
    }

    public String toString() {
        return filename;
    }

    interface Visitor {
        void visit(Location origin, Location target);
    }
}
