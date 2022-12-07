/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package build.tools.cldrconverter;

import java.io.File;
import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class MetaZonesParseHandler extends AbstractLDMLHandler<String> {
    private String tzid, metazone;

    MetaZonesParseHandler() {
    }

    @Override
    public InputSource resolveEntity(String publicID, String systemID) throws IOException, SAXException {
        // avoid HTTP traffic to unicode.org
        if (systemID.startsWith(CLDRConverter.SPPL_LDML_DTD_SYSTEM_ID)) {
            return new InputSource((new File(CLDRConverter.LOCAL_SPPL_LDML_DTD)).toURI().toString());
        }
        return null;
    }

    // metaZone: ID -> metazone
    // per locale: ID -> names, metazone -> names
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
        case "timezone":
            tzid = attributes.getValue("type");
            pushContainer(qName, attributes);
            break;

        case "usesMetazone":
            // Ignore any historical zone names (for now)
            if (attributes.getValue("to") == null) {
                metazone = attributes.getValue("mzone");
            }
            pushIgnoredContainer(qName);
            break;

        case "version":
        case "generation":
            pushIgnoredContainer(qName);
            break;

        default:
            // treat anything else as a container
            pushContainer(qName, attributes);
            break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        assert qName.equals(currentContainer.getqName()) : "current=" + currentContainer.getqName() + ", param=" + qName;
        switch (qName) {
        case "timezone":
            if (tzid == null || metazone == null) {
                throw new InternalError();
            }
            put(tzid, metazone);
            break;
        }
        currentContainer = currentContainer.getParent();
    }
}
