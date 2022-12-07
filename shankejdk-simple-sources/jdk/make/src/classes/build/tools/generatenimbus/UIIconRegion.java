/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import javax.xml.bind.annotation.XmlAttribute;

class UIIconRegion extends UIRegion {
    @XmlAttribute private String basicKey;

    @Override public void write(StringBuilder sb, StringBuilder styleBuffer, UIComponent comp, String prefix, String pkg) {
        Dimension size = null;
        String fileNamePrefix = Utils.normalize(prefix) + "Painter";
        // write states ui defaults
        for (UIState state : backgroundStates) {
            Canvas canvas = state.getCanvas();
            if (!canvas.isBlank()) {
                state.write(sb, prefix, pkg, fileNamePrefix, getKey());
                size = canvas.getSize();
            }
        }

        if (size != null) {
            // Put SynthIconImpl wrapper in UiDefaults
            String k = (basicKey == null ? prefix + "." + getKey() : basicKey);
            sb.append(String.format(
                    "        d.put(\"%s\", new NimbusIcon(\"%s\", \"%sPainter\", %d, %d));\n",
                    k, prefix, getKey(), size.width, size.height));
        }
    }
}
