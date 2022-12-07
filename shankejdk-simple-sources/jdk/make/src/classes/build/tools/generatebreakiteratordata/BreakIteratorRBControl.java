/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.generatebreakiteratordata;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Locale;

class BreakIteratorRBControl extends ResourceBundle.Control {
    static final BreakIteratorRBControl INSTANCE = new BreakIteratorRBControl();

    private static final String RESOURCES = ".resources.";

    private BreakIteratorRBControl() {
    }

    @Override
    public Locale getFallbackLocale(String baseName, Locale locale) {
        // No fallback
        return null;
    }

    @Override
    public List<Locale> getCandidateLocales(String baseName, Locale locale) {
        // No parents lookup
        return Arrays.asList(locale);
    }

    /**
     * Changes baseName to its per-language package name and
     * calls the super class implementation.
     */
    @Override
    public String toBundleName(String baseName, Locale locale) {
        String newBaseName = baseName;
        String lang = locale.getLanguage();
        if (lang.length() > 0) {
            int index = baseName.indexOf(RESOURCES);
            if (index > 0) {
                index += RESOURCES.length();
                newBaseName = baseName.substring(0, index) + lang + "."
                                  + baseName.substring(index);
            }
        }
        return super.toBundleName(newBaseName, locale);
    }
}
