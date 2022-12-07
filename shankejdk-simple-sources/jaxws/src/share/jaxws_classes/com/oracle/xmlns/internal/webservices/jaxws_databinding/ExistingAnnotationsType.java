/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.oracle.xmlns.internal.webservices.jaxws_databinding;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * This file was generated by JAXB-RI v2.2.6 and afterwards modified
 * to implement appropriate Annotation
 *
 * <p>Java class for existing-annotations-type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="existing-annotations-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="merge"/>
 *     &lt;enumeration value="ignore"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "existing-annotations-type")
@XmlEnum
public enum ExistingAnnotationsType {

    @XmlEnumValue("merge")
    MERGE("merge"),
    @XmlEnumValue("ignore")
    IGNORE("ignore");
    private final String value;

    ExistingAnnotationsType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExistingAnnotationsType fromValue(String v) {
        for (ExistingAnnotationsType c: ExistingAnnotationsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
