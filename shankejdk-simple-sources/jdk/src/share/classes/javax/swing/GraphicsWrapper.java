/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
 */
package javax.swing;

/**
 * A private interface to access clip bounds in wrapped Graphics objects.
 *
 * @author Thomas Ball
 */

import java.awt.*;

interface GraphicsWrapper {
    Graphics subGraphics();

    boolean isClipIntersecting(Rectangle r);

    int getClipX();

    int getClipY();

    int getClipWidth();

    int getClipHeight();
}
