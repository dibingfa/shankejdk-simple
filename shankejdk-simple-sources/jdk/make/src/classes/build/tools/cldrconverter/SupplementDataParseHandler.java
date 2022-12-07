/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Handles parsing of files in Locale Data Markup Language for SupplementData.xml
 * and produces a map that uses the keys and values of JRE locale data.
 */

class SupplementDataParseHandler extends AbstractLDMLHandler<Object> {
    //UNM49 region and composition code used in supplementalData.xml
    private static final String WORLD = "001";

    private static final String JAVA_FIRSTDAY = "firstDayOfWeek";
    private static final String JAVA_MINDAY = "minimalDaysInFirstWeek";

    // The weekData is now in supplementalData.xml,
    // which is not a locale specific file.
    // Map for JRE is created locale specific way.
    // When parsing the locale neutral file (supplementalData.xml),
    // we need to rely on the country code because
    // the weekData is listed using country code.
    private final Map<String, Object> firstDayMap;
    private final Map<String, Object> minDaysMap;

    SupplementDataParseHandler() {
        firstDayMap = new HashMap<>();
        minDaysMap = new HashMap<>();
    }

    /**
     * It returns Map that contains the firstDay and minDays information for
     * the country. The Map is created in JRE format after obtaining the data
     * from two Maps, firstDayMap and minDaysMap.
     *
     * It returns null when there is no firstDay and minDays for the country
     * although this should not happen because supplementalData.xml includes
     * default value for the world ("001") for firstDay and minDays.
     */
    Map<String, Object> getData(String country) {
        Map<String, Object> values = new HashMap<>();
        String countryData = getWeekData(country, JAVA_FIRSTDAY, firstDayMap);
        if (countryData != null) {
            values.put(JAVA_FIRSTDAY, countryData);
        }
        String minDaysData = getWeekData(country, JAVA_MINDAY, minDaysMap);
        if (minDaysData != null) {
            values.put(JAVA_MINDAY, minDaysData);
        }
        return values.isEmpty() ? null : values;
    }

    /**
     * It returns either firstDay or minDays in the JRE format for the country.
     *
     * @param country       territory code of the requested data
     * @param jreDataName   JAVA_FIRSTDAY or JAVA_MINDAY
     * @param dataMap       firstDayMap or minDaysMap
     * @return the value for the given jreDataName, or null if requested value
     *         (firstDay/minDays) is not available although that is highly unlikely
     *         because of the default value for the world (001).
     */
    String getWeekData(String country, final String jreDataName, final Map<String, Object> dataMap) {
        String countryValue = null;
        String defaultWorldValue = null;
        for (String key : dataMap.keySet()) {
            if (key.contains(country)) {
                if (jreDataName.equals(JAVA_FIRSTDAY)) {
                    countryValue = DAY_OF_WEEK_MAP.get((String) dataMap.get(key));
                } else if (jreDataName.equals(JAVA_MINDAY)) {
                    countryValue = (String) dataMap.get(key);
                }
                if (countryValue != null) {
                    return countryValue;
                }
            } else if (key.contains(WORLD)) {
                if (jreDataName.equals(JAVA_FIRSTDAY)) {
                    defaultWorldValue = DAY_OF_WEEK_MAP.get((String) dataMap.get(key));
                } else if (jreDataName.equals(JAVA_MINDAY)) {
                    defaultWorldValue = (String) dataMap.get(key);
                }
            }
        }
        return defaultWorldValue;
    }

    @Override
    public InputSource resolveEntity(String publicID, String systemID) throws IOException, SAXException {
        // avoid HTTP traffic to unicode.org
        if (systemID.startsWith(CLDRConverter.SPPL_LDML_DTD_SYSTEM_ID)) {
            return new InputSource((new File(CLDRConverter.LOCAL_SPPL_LDML_DTD)).toURI().toString());
        }
        return null;
    }

    /**
     * JRE requires all the data to be organized by the locale while CLDR 1.4 list
     * Calendar related data (weekData)in SupplementalData.xml.
     * startElement stores JRE required data into two Maps,
     * firstDayMap and minDaysMap.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // elements we need to actively ignore
        switch (qName) {
        case "firstDay":
            if (!isIgnored(attributes)) {
                firstDayMap.put(attributes.getValue("territories"), attributes.getValue("day"));
            }
            break;
        case "minDays":
            if (!isIgnored(attributes)) {
                minDaysMap.put(attributes.getValue("territories"), attributes.getValue("count"));
            }
            break;
        default:
            // treat anything else as a container
            pushContainer(qName, attributes);
            break;
        }
    }

}
