/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 */

package sun.lwawt.macosx;


import java.awt.*;
import java.awt.print.*;

final class CPrinterJobDialog extends CPrinterDialog {
    private Pageable fPageable;
    private boolean fAllowPrintToFile;

    CPrinterJobDialog(Frame parent, CPrinterJob printerJob, Pageable doc, boolean allowPrintToFile) {
        super(parent, printerJob);
        fPageable = doc;
        fAllowPrintToFile = allowPrintToFile;
    }

    @Override
    protected native boolean showDialog();
}
