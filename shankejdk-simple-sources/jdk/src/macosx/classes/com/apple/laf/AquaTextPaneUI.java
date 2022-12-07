/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.laf;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextPaneUI;
import javax.swing.text.*;

//[3663467] moved it to sublcass from BasicEditorPaneUI to BasicTextPaneUI. (vm)
public class AquaTextPaneUI extends BasicTextPaneUI {
    public static ComponentUI createUI(final JComponent c) {
        return new AquaTextPaneUI();
    }

    public AquaTextPaneUI() {
        super();
    }

    AquaFocusHandler handler;
    protected void installListeners() {
        super.installListeners();
        final JComponent c = getComponent();
        handler = new AquaFocusHandler();
        c.addFocusListener(handler);
        c.addPropertyChangeListener(handler);
        AquaUtilControlSize.addSizePropertyListener(c);
    }

    protected void uninstallListeners() {
        final JComponent c = getComponent();
        AquaUtilControlSize.removeSizePropertyListener(c);
        c.removeFocusListener(handler);
        c.removePropertyChangeListener(handler);
        handler = null;
        super.uninstallListeners();
    }

    boolean oldDragState = false;
    protected void installDefaults() {
        final JTextComponent c = getComponent();
        if (!GraphicsEnvironment.isHeadless()) {
            oldDragState = c.getDragEnabled();
            c.setDragEnabled(true);
        }
        super.installDefaults();
    }

    protected void uninstallDefaults() {
        if (!GraphicsEnvironment.isHeadless()) {
            getComponent().setDragEnabled(oldDragState);
        }
        super.uninstallDefaults();
    }

    // Install a default keypress action which handles Cmd and Option keys properly
    protected void installKeyboardActions() {
        super.installKeyboardActions();
        AquaKeyBindings bindings = AquaKeyBindings.instance();
        bindings.setDefaultAction(getKeymapName());

        final JTextComponent c = getComponent();
        bindings.installAquaUpDownActions(c);
    }

    protected Caret createCaret() {
        final JTextComponent c = getComponent();
        final Window owningWindow = SwingUtilities.getWindowAncestor(c);
        return new AquaCaret(owningWindow, c);
    }

    protected Highlighter createHighlighter() {
        return new AquaHighlighter();
    }
}
