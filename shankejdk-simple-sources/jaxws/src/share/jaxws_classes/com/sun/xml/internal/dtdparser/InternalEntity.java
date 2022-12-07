/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.dtdparser;


final class InternalEntity extends EntityDecl {
    InternalEntity(String name, char value []) {
        this.name = name;
        this.buf = value;
    }

    char buf [];
}
