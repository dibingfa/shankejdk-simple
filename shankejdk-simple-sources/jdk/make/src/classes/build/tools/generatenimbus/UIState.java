/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatenimbus;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

class UIState {
    @XmlAttribute private String stateKeys;
    public String getStateKeys() { return stateKeys; }

    /** Indicates whether to invert the meaning of the 9-square stretching insets */
    @XmlAttribute private boolean inverted;

    /** A cached string representing the list of stateKeys deliminated with "+" */
    private String cachedName = null;

    @XmlElement private Canvas canvas;
    public Canvas getCanvas() { return canvas; }

    @XmlElement private UIStyle style;
    public UIStyle getStyle() { return style; }

    public boolean hasCanvas() {
        return ! canvas.isBlank();
    }

    public static List<String> stringToKeys(String keysString) {
        return Arrays.asList(keysString.split("\\+"));
    }

    public String getName() {
        if (cachedName == null) {
            StringBuilder buf = new StringBuilder();
            List<String> keys = stringToKeys(stateKeys);
            Collections.sort(keys);
            for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
                buf.append(iter.next());
                if (iter.hasNext()) {
                    buf.append('+');
                }
            }
            cachedName = buf.toString();
        }
        return cachedName;
    }

    public void write(StringBuilder sb, String prefix, String pkg, String fileNamePrefix, String painterPrefix) {
        String statePrefix = prefix + "[" + getName() + "]";
        // write state style
        sb.append(style.write(statePrefix + '.'));
        // write painter
        if (hasCanvas()) {
            writeLazyPainter(sb, statePrefix, pkg, fileNamePrefix, painterPrefix);
        }
    }

    private void writeLazyPainter(StringBuilder sb, String statePrefix, String packageNamePrefix, String fileNamePrefix, String painterPrefix) {
        String cacheModeString = "AbstractRegionPainter.PaintContext.CacheMode." + style.getCacheMode();
        String stateConstant = Utils.statesToConstantName(painterPrefix + "_" + stateKeys);
        sb.append(String.format(
                "        d.put(\"%s.%sPainter\", new LazyPainter(\"%s.%s\", %s.%s, %s, %s, %b, %s, %s, %s));\n",
                statePrefix, painterPrefix, packageNamePrefix, fileNamePrefix,
                fileNamePrefix, stateConstant, canvas.getStretchingInsets().write(false),
                canvas.getSize().write(false), inverted, cacheModeString,
                Utils.formatDouble(style.getMaxHozCachedImgScaling()),
                Utils.formatDouble(style.getMaxVertCachedImgScaling())));
    }
}
