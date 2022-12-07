/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.laf;

import java.awt.*;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicEditorPaneUI;
import javax.swing.text.*;

public class AquaEditorPaneUI extends BasicEditorPaneUI {
    public static ComponentUI createUI(final JComponent c){
        return new AquaEditorPaneUI();
    }

    boolean oldDragState = false;
    protected void installDefaults(){
        super.installDefaults();
        if(!GraphicsEnvironment.isHeadless()){
            oldDragState = getComponent().getDragEnabled();
            getComponent().setDragEnabled(true);
        }
    }

    protected void uninstallDefaults(){
        if(!GraphicsEnvironment.isHeadless()){
            getComponent().setDragEnabled(oldDragState);
        }
        super.uninstallDefaults();
    }

    FocusListener focusListener;
    protected void installListeners(){
        super.installListeners();
        focusListener = createFocusListener();
        getComponent().addFocusListener(focusListener);
    }

    protected void installKeyboardActions() {
        super.installKeyboardActions();
        AquaKeyBindings bindings = AquaKeyBindings.instance();
        bindings.setDefaultAction(getKeymapName());
        final JTextComponent c = getComponent();
        bindings.installAquaUpDownActions(c);
    }

    protected void uninstallListeners(){
        getComponent().removeFocusListener(focusListener);
        super.uninstallListeners();
    }

    protected FocusListener createFocusListener(){
        return new AquaFocusHandler();
    }

    protected Caret createCaret(){
        final Window owningWindow = SwingUtilities.getWindowAncestor(getComponent());
        final AquaCaret returnValue = new AquaCaret(owningWindow, getComponent());
        return returnValue;
    }

    protected Highlighter createHighlighter(){
        return new AquaHighlighter();
    }
}
