/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.internal.jobjc.generator.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apple.internal.jobjc.generator.model.types.Type;
import com.apple.internal.jobjc.generator.utils.ObjectInspector;

/**
 * Subclasses must implement ctor(Node, P)
 */
public class Element <P extends Element<?>> implements Comparable<Element<?>>{
    public final String name;
    public final P parent;

    public Element(final String name, final P parent) {
        this.name = Type.cleanName(name);
        this.parent = parent;
    }

    public Element(final Node node, final P parent) {
        this(getAttr(node, "name"), parent);
    }

    public static String getAttr(final Node node, final String key) {
        final NamedNodeMap attrs = node.getAttributes();
        if (attrs == null) return null;
        final Node name = attrs.getNamedItem(key);
        if (name == null) return null;
        return name.getNodeValue();
    }

    static <P extends Element<?>, T extends Element<P>> List<T> getNodesFor(final Node parentNode, final String selection, final Class<T> clazz, final P parent) {
        Constructor<T> ctor;
        try {
            ctor = clazz.getConstructor(new Class[] { Node.class, parent.getClass() });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final NodeList childNodes = parentNode.getChildNodes();
        final List<T> nodes = new ArrayList<T>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final Node node = childNodes.item(i);
            if (!selection.equals(node.getLocalName())) continue;

            T obj;
            try {
                obj = ctor.newInstance(new Object[] { node, parent });
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            nodes.add(obj);
        }

        return nodes;
    }

    @Override public String toString() {
        return name;
    }

    public String reflectOnMySelf() {
        return ObjectInspector.inspect(this);
    }

    public int compareTo(Element<?> o) {
        return name.compareTo(o.name);
    }
}
