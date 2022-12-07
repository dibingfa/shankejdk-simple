/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.awt.Font;

import javax.xml.bind.annotation.XmlAttribute;

class Typeface {
    public enum DeriveStyle {
        Default, Off, On;

        @Override public String toString() {
            switch (this) {
                default:  return "null";
                case On:  return "true";
                case Off: return "false";
            }
        }
    }

    @XmlAttribute private String uiDefaultParentName;
    @XmlAttribute(name="family") private String name;
    @XmlAttribute private int size;
    @XmlAttribute private DeriveStyle bold = DeriveStyle.Default;
    @XmlAttribute private DeriveStyle italic = DeriveStyle.Default;
    @XmlAttribute private float sizeOffset = 1f;

    public boolean isAbsolute() {
        return uiDefaultParentName == null;
    }

    public String write() {
        if (isAbsolute()) {
            int style = Font.PLAIN;
            if (bold == DeriveStyle.On) {
                style = style | Font.BOLD;
            }
            if (italic == DeriveStyle.On) {
                style = style | Font.ITALIC;
            }

            return String.format(
                    "new javax.swing.plaf.FontUIResource(\"%s\", %d, %d)",
                    name, style, size);
        } else {
            return String.format(
                    "new DerivedFont(\"%s\", %sf, %s, %s)",
                    uiDefaultParentName, String.valueOf(sizeOffset), bold, italic);
        }
    }
}
