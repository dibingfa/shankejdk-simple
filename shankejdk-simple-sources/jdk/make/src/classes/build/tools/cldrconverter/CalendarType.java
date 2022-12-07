/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

import java.util.Locale;

/**
 * Constants for the Calendars supported by JRE.
 */
enum CalendarType {
    GREGORIAN("gregory"), BUDDHIST, JAPANESE, ROC,
    ISLAMIC, ISLAMIC_CIVIL("islamicc"), ISLAMIC_UMALQURA("islamic-umalqura");

    private static final int[][] ERA_DATA = {
        // start index, array length
        {0,   2},   // gregorian
        {0,   1},   // buddhist
        {232, 5},   // japanese (eras from Meiji)
        {0,   2},   // roc (Minguo)
        {0,   1},   // islamic (Hijrah)
        {0,   1},   // islamicc (same as islamic)
        {0,   1},   // islamic-umalqura
    };

    private final String lname; // lowercase name
    private final String uname; // unicode key name (e.g., "gregory" for GREGORIAN)

    private CalendarType() {
        this(null);
    }

    private CalendarType(String uname) {
        String lname = name().toLowerCase(Locale.ROOT);
        if (lname.startsWith("islamic_")) {
            lname = lname.replace('_', '-');
        }
        this.lname = lname;
        this.uname = (uname != null) ? uname : lname;
    }

    String lname() {
        return lname;
    }

    String uname() {
        return uname;
    }

    String keyElementName() {
        return (this == GREGORIAN) ? "" : lname + ".";
    }

    int normalizeEraIndex(int index) {
        index -= ERA_DATA[ordinal()][0];
        if (index >= ERA_DATA[ordinal()][1]) {
            index = -1;
        }
        return index;
    }

    int getEraLength(String name) {
        return ERA_DATA[ordinal()][1];
    }

    static CalendarType forName(String name) {
        for (CalendarType type : values()) {
            if (type.lname.equals(name)) {
                return type;
            }
        }
        return null;
    }
}
