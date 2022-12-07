/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/**
*
* @author SAAJ RI Development Team
*/
package com.sun.xml.internal.messaging.saaj.soap;

public interface SOAPDocument {
    SOAPPartImpl getSOAPPart();
    SOAPDocumentImpl getDocument();
}
