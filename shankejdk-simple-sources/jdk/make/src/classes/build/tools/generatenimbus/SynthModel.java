/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;


@XmlRootElement(name="synthModel")
public class SynthModel {
    @XmlElement private UIStyle style;

    @XmlElement(name="uiColor")
    @XmlElementWrapper(name="colors")
    private ArrayList<UIColor> colors;

    @XmlElement(name="uiFont")
    @XmlElementWrapper(name="fonts")
    private ArrayList<UIFont> fonts;

    @XmlElement(name="uiComponent")
    @XmlElementWrapper(name="components")
    private ArrayList<UIComponent> components;

    public void initStyles() {
        for (UIComponent c: components) {
            c.initStyles(this.style);
        }
    }

    public void write(StringBuilder defBuffer, StringBuilder styleBuffer, String packageName) {
        defBuffer.append("        //Color palette\n");
        for (UIColor c: colors) defBuffer.append(c.write());
        defBuffer.append('\n');

        defBuffer.append("        //Font palette\n");
        defBuffer.append("        d.put(\"defaultFont\", new FontUIResource(defaultFont));\n");
        for (UIFont f: fonts) defBuffer.append(f.write());
        defBuffer.append('\n');

        defBuffer.append("        //Border palette\n");
        defBuffer.append('\n');

        defBuffer.append("        //The global style definition\n");
        defBuffer.append(style.write(""));
        defBuffer.append('\n');

        for (UIComponent c: components) {
            String prefix = Utils.escape(c.getKey());
            defBuffer.append("        //Initialize ").append(prefix).append("\n");
            c.write(defBuffer, styleBuffer, c, prefix, packageName);
            defBuffer.append('\n');
        }
    }
}
