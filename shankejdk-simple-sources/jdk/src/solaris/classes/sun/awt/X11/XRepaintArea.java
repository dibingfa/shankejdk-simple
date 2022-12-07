/*
 * Copyright (c) 2003, 2014, Oracle and/or its affiliates. All rights reserved.
 */


package sun.awt.X11;

import java.awt.Component;
import java.awt.Graphics;

import sun.awt.RepaintArea;

/**
 * The <code>RepaintArea</code> is a geometric construct created for the
 * purpose of holding the geometry of several coalesced paint events.
 * This geometry is accessed synchronously, although it is written such
 * that painting may still be executed asynchronously.
 *
 * @author      Eric Hawkes
 */
final class XRepaintArea extends RepaintArea {

    /**
     * Calls <code>Component.update(Graphics)</code> with given Graphics.
     */
    protected void updateComponent(Component comp, Graphics g) {
        if (comp != null) {
            // We don't call peer.paintPeer() here, because we shouldn't paint
            // native component when processing UPDATE events.
            super.updateComponent(comp, g);
        }
    }

    /**
     * Calls <code>Component.paint(Graphics)</code> with given Graphics.
     */
    protected void paintComponent(Component comp, Graphics g) {
        if (comp != null) {
            final XComponentPeer peer = (XComponentPeer) comp.getPeer();
            if (peer != null) {
                peer.paintPeer(g);
            }
            super.paintComponent(comp, g);
        }
    }
}
