/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.oracle.xmlns.internal.webservices.jaxws_databinding;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.lang.annotation.Annotation;

import static com.oracle.xmlns.internal.webservices.jaxws_databinding.Util.nullSafe;


/**
 * This file was generated by JAXB-RI v2.2.6 and afterwards modified
 * to implement appropriate Annotation
 *
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="style" type="{http://xmlns.oracle.com/webservices/jaxws-databinding}soap-binding-style" default="DOCUMENT" />
 *       &lt;attribute name="use" type="{http://xmlns.oracle.com/webservices/jaxws-databinding}soap-binding-use" default="LITERAL" />
 *       &lt;attribute name="parameter-style" type="{http://xmlns.oracle.com/webservices/jaxws-databinding}soap-binding-parameter-style" default="WRAPPED" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "soap-binding")
public class XmlSOAPBinding implements javax.jws.soap.SOAPBinding {

    @XmlAttribute(name = "style")
    protected SoapBindingStyle style;
    @XmlAttribute(name = "use")
    protected SoapBindingUse use;
    @XmlAttribute(name = "parameter-style")
    protected SoapBindingParameterStyle parameterStyle;

    /**
     * Gets the value of the style property.
     *
     * @return
     *     possible object is
     *     {@link SoapBindingStyle }
     *
     */
    public SoapBindingStyle getStyle() {
        if (style == null) {
            return SoapBindingStyle.DOCUMENT;
        } else {
            return style;
        }
    }

    /**
     * Sets the value of the style property.
     *
     * @param value
     *     allowed object is
     *     {@link SoapBindingStyle }
     *
     */
    public void setStyle(SoapBindingStyle value) {
        this.style = value;
    }

    /**
     * Gets the value of the use property.
     *
     * @return
     *     possible object is
     *     {@link SoapBindingUse }
     *
     */
    public SoapBindingUse getUse() {
        if (use == null) {
            return SoapBindingUse.LITERAL;
        } else {
            return use;
        }
    }

    /**
     * Sets the value of the use property.
     *
     * @param value
     *     allowed object is
     *     {@link SoapBindingUse }
     *
     */
    public void setUse(SoapBindingUse value) {
        this.use = value;
    }

    /**
     * Gets the value of the parameterStyle property.
     *
     * @return
     *     possible object is
     *     {@link SoapBindingParameterStyle }
     *
     */
    public SoapBindingParameterStyle getParameterStyle() {
        if (parameterStyle == null) {
            return SoapBindingParameterStyle.WRAPPED;
        } else {
            return parameterStyle;
        }
    }

    /**
     * Sets the value of the parameterStyle property.
     *
     * @param value
     *     allowed object is
     *     {@link SoapBindingParameterStyle }
     *
     */
    public void setParameterStyle(SoapBindingParameterStyle value) {
        this.parameterStyle = value;
    }

    @Override
    public Style style() {
        return nullSafe(style, Style.DOCUMENT);
    }

    @Override
    public Use use() {
        return nullSafe(use, Use.LITERAL);
    }

    @Override
    public ParameterStyle parameterStyle() {
        return nullSafe(parameterStyle, ParameterStyle.WRAPPED);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return javax.jws.soap.SOAPBinding.class;
    }
}
