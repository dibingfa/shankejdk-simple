/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.xml.internal.ws.wsdl.writer.document.soap12;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

/**
 *
 * @author WS Development Team
 */
@XmlElement("fault")
public interface SOAPFault
    extends TypedXmlWriter, BodyType
{
   @XmlAttribute
   public SOAPFault name(String value);

}
