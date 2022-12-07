/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.util.*;

import org.w3c.dom.*;

import com.apple.internal.jobjc.generator.RestrictedKeywords;

public class Function extends Element<Framework> {
    public final boolean variadic;
    public final List<Arg> args;
    public final ReturnValue returnValue;

    public Function(final Node node, final Framework parent) {
        this(node, getAttr(node, "name"), parent);
    }

    public Function(final Node node, final String name, final Framework parent) {
        super(name, parent);

        this.variadic = "true".equals(getAttr(node, "variadic"));
        this.args = new ArrayList<Arg>();

        ReturnValue returnValue = null;

        final NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);
            final String childName = child.getLocalName();

            if ("retval".equals(childName)) {
                returnValue = new ReturnValue(child, this);
            }

            if ("arg".equals(childName)) {
                final Arg arg = new Arg(child, this);
                if (arg.name == null || "".equals(arg.name)) {
                    arg.javaName = "arg" + i;
                }
                args.add(arg);
            }
        }

        if (returnValue == null) returnValue = ReturnValue.VOID;
        this.returnValue = returnValue;
    }

    public String getJavaName(){ return name; }

    public void disambiguateArgs() {
        final Set<String> priorArgs = RestrictedKeywords.getNewRestrictedSet();
        for (int i = 0; i < args.size(); i++) {
            final Arg arg = args.get(i);
            if (priorArgs.contains(arg.name)) arg.javaName = arg.javaName + i;
            priorArgs.add(arg.javaName);
        }
    }
}
