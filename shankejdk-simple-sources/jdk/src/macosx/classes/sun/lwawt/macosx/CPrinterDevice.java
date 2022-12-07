/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package sun.lwawt.macosx;

import java.awt.*;

public class CPrinterDevice extends GraphicsDevice {
    GraphicsConfiguration gc;

    public CPrinterDevice(CPrinterGraphicsConfig gc) {
        this.gc = gc;
    }

    /**
     * Returns the type of this <code>GraphicsDevice</code>.
     * @return the type of this <code>GraphicsDevice</code>, which can
     * either be TYPE_RASTER_SCREEN, TYPE_PRINTER or TYPE_IMAGE_BUFFER.
     * @see #TYPE_RASTER_SCREEN
     * @see #TYPE_PRINTER
     * @see #TYPE_IMAGE_BUFFER
     */
    public int getType() {
        return GraphicsDevice.TYPE_PRINTER;
    }

    /**
     * Returns the identification string associated with this
     * <code>GraphicsDevice</code>.
     * @return a <code>String</code> that is the identification
     * of this <code>GraphicsDevice</code>.
     */
    public String getIDstring() {
        return ("Printer");
    }

    /**
     * Returns all of the <code>GraphicsConfiguration</code>
     * objects associated with this <code>GraphicsDevice</code>.
     * @return an array of <code>GraphicsConfiguration</code>
     * objects that are associated with this
     * <code>GraphicsDevice</code>.
     */
    public GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { gc };
    }

    /**
     * Returns the default <code>GraphicsConfiguration</code>
     * associated with this <code>GraphicsDevice</code>.
     * @return the default <code>GraphicsConfiguration</code>
     * of this <code>GraphicsDevice</code>.
     */
    public GraphicsConfiguration getDefaultConfiguration() {
        return gc;
    }
}
