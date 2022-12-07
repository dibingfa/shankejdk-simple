/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.api.model.wsdl;

import javax.xml.namespace.QName;

/**
 * Abstracts wsdl:part descriptor that is defined using element or type attribute.
 *
 * @author Vivek Pandey
 */
public interface WSDLPartDescriptor extends WSDLObject {
    /**
     * Gives Qualified name of the XML Schema element or type
     */
    public QName name();

    /**
     * Gives whether wsdl:part references a schema type or a global element.
     */
    public WSDLDescriptorKind type();

}
