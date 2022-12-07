/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

import java.io.IOException;
import java.util.Map;
import java.util.SortedSet;

public interface BundleGenerator {
    static enum BundleType {
        PLAIN("java.util.ListResourceBundle"),
        OPEN("sun.util.resources.OpenListResourceBundle"),
        TIMEZONE("sun.util.resources.TimeZoneNamesBundle");

        private final String pathName, className;
        private BundleType(String name) {
            pathName = name;
            int x = name.lastIndexOf('.');
            className = name.substring(x + 1);
        }
        String getPathName() {
            return pathName;
        }
        String getClassName() {
            return className;
        }
    };

    public void generateBundle(String packageName, String baseName, String localeID,
            boolean useJava, Map<String, ?> map, BundleType type) throws IOException;

    public void generateMetaInfo(Map<String, SortedSet<String>> metaInfo) throws IOException;
}
