/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.laf;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import apple.laf.*;
import apple.laf.JRSUIConstants.*;
import apple.laf.JRSUIState.ValueState;

import com.apple.laf.AquaUtilControlSize.*;
import com.apple.laf.AquaUtils.RecyclableSingleton;

public abstract class AquaButtonLabeledUI extends AquaButtonToggleUI implements Sizeable {
    protected static RecyclableSizingIcon regularIcon = new RecyclableSizingIcon(18);
    protected static RecyclableSizingIcon smallIcon = new RecyclableSizingIcon(16);
    protected static RecyclableSizingIcon miniIcon = new RecyclableSizingIcon(14);

    protected static class RecyclableSizingIcon extends RecyclableSingleton<Icon> {
        final int iconSize;
        public RecyclableSizingIcon(final int iconSize) { this.iconSize = iconSize; }

        protected Icon getInstance() {
            return new ImageIcon(new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB_PRE));
        }
    }

    protected AquaButtonBorder widgetBorder;

    public AquaButtonLabeledUI() {
        widgetBorder = getPainter();
    }

    public void applySizeFor(final JComponent c, final Size newSize) {
        super.applySizeFor(c, newSize);
        widgetBorder = (AquaButtonBorder)widgetBorder.deriveBorderForSize(newSize);
    }

    public Icon getDefaultIcon(final JComponent c) {
        final Size componentSize = AquaUtilControlSize.getUserSizeFrom(c);
        if (componentSize == Size.REGULAR) return regularIcon.get();
        if (componentSize == Size.SMALL) return smallIcon.get();
        if (componentSize == Size.MINI) return miniIcon.get();
        return regularIcon.get();
    }

    protected void setThemeBorder(final AbstractButton b) {
        super.setThemeBorder(b);

        Border border = b.getBorder();
        if (border == null || border instanceof UIResource) {
            // Set the correct border
            b.setBorder(AquaButtonBorder.getBevelButtonBorder());
        }
    }

    protected abstract AquaButtonBorder getPainter();

    public synchronized void paint(final Graphics g, final JComponent c) {
        final AbstractButton b = (AbstractButton)c;
        final ButtonModel model = b.getModel();

        final Font f = c.getFont();
        g.setFont(f);
        final FontMetrics fm = g.getFontMetrics();

        Dimension size = b.getSize();

        final Insets i = c.getInsets();

        Rectangle viewRect = new Rectangle(b.getWidth(), b.getHeight());
        Rectangle iconRect = new Rectangle();
        Rectangle textRect = new Rectangle();

        Icon altIcon = b.getIcon();

        final boolean isCellEditor = c.getParent() instanceof CellRendererPane;

        // This was erroneously removed to fix [3155996] but really we wanted the controls to just be
        // opaque. So we put this back in to fix [3179839] (radio buttons not being translucent)
        if (b.isOpaque() || isCellEditor) {
            g.setColor(b.getBackground());
            g.fillRect(0, 0, size.width, size.height);
        }

        // only do this if borders are on!
        if (((AbstractButton)c).isBorderPainted() && !isCellEditor) {
            final Border border = c.getBorder();
            if (border instanceof AquaButtonBorder) {
                ((AquaButtonBorder)border).paintButton(c, g, viewRect.x, viewRect.y, viewRect.width, viewRect.height);
            }
        }

        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = b.getWidth() - (i.right + viewRect.x);
        viewRect.height = b.getHeight() - (i.bottom + viewRect.y);

        // normal size ??
        // at some point we substitute the small icon instead of the normal icon
        // we should base this on height. Use normal unless we are under a certain size
        // see our button code!

        final String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), altIcon != null ? altIcon : getDefaultIcon(b), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getText() == null ? 0 : b.getIconTextGap());

        // fill background

        // draw the native radio button stuff here.
        if (altIcon == null) {
            widgetBorder.paintButton(c, g, iconRect.x, iconRect.y, iconRect.width, iconRect.height);
        } else {
            // Paint the button
            if (!model.isEnabled()) {
                if (model.isSelected()) {
                    altIcon = b.getDisabledSelectedIcon();
                } else {
                    altIcon = b.getDisabledIcon();
                }
            } else if (model.isPressed() && model.isArmed()) {
                altIcon = b.getPressedIcon();
                if (altIcon == null) {
                    // Use selected icon
                    altIcon = b.getSelectedIcon();
                }
            } else if (model.isSelected()) {
                if (b.isRolloverEnabled() && model.isRollover()) {
                    altIcon = b.getRolloverSelectedIcon();
                    if (altIcon == null) {
                        altIcon = b.getSelectedIcon();
                    }
                } else {
                    altIcon = b.getSelectedIcon();
                }
            } else if (b.isRolloverEnabled() && model.isRollover()) {
                altIcon = b.getRolloverIcon();
            }

            if (altIcon == null) {
                altIcon = b.getIcon();
            }

            altIcon.paintIcon(c, g, iconRect.x, iconRect.y);
        }

        // Draw the Text
        if (text != null) {
            final View v = (View)c.getClientProperty(BasicHTML.propertyKey);
            if (v != null) {
                v.paint(g, textRect);
            } else {
                paintText(g, b, textRect, text);
            }
        }
    }

    /**
     * The preferred size of the button
     */
    public Dimension getPreferredSize(final JComponent c) {
        if (c.getComponentCount() > 0) { return null; }

        final AbstractButton b = (AbstractButton)c;

        final String text = b.getText();

        Icon buttonIcon = b.getIcon();
        if (buttonIcon == null) {
            buttonIcon = getDefaultIcon(b);
        }

        final Font font = b.getFont();
        final FontMetrics fm = b.getFontMetrics(font);

        Rectangle prefViewRect = new Rectangle(Short.MAX_VALUE, Short.MAX_VALUE);
        Rectangle prefIconRect = new Rectangle();
        Rectangle prefTextRect = new Rectangle();

        SwingUtilities.layoutCompoundLabel(c, fm, text, buttonIcon, b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), prefViewRect, prefIconRect, prefTextRect, text == null ? 0 : b.getIconTextGap());

        // find the union of the icon and text rects (from Rectangle.java)
        final int x1 = Math.min(prefIconRect.x, prefTextRect.x);
        final int x2 = Math.max(prefIconRect.x + prefIconRect.width, prefTextRect.x + prefTextRect.width);
        final int y1 = Math.min(prefIconRect.y, prefTextRect.y);
        final int y2 = Math.max(prefIconRect.y + prefIconRect.height, prefTextRect.y + prefTextRect.height);
        int width = x2 - x1;
        int height = y2 - y1;

        Insets prefInsets = b.getInsets();
        width += prefInsets.left + prefInsets.right;
        height += prefInsets.top + prefInsets.bottom;
        return new Dimension(width, height);
    }

    public static abstract class LabeledButtonBorder extends AquaButtonBorder {
        public LabeledButtonBorder(final SizeDescriptor sizeDescriptor) {
            super(sizeDescriptor);
        }

        public LabeledButtonBorder(final LabeledButtonBorder other) {
            super(other);
        }

        @Override
        protected AquaPainter<? extends JRSUIState> createPainter() {
            final AquaPainter<ValueState> painter = AquaPainter.create(JRSUIStateFactory.getLabeledButton());
            painter.state.set(AlignmentVertical.CENTER);
            painter.state.set(AlignmentHorizontal.CENTER);
            return painter;
        }

        protected void doButtonPaint(final AbstractButton b, final ButtonModel model, final Graphics g, final int x, final int y, final int width, final int height) {
            painter.state.set(AquaUtilControlSize.getUserSizeFrom(b));
            ((ValueState)painter.state).setValue(model.isSelected() ? isIndeterminate(b) ? 2 : 1 : 0); // 2=mixed, 1=on, 0=off
            super.doButtonPaint(b, model, g, x, y, width, height);
        }

        protected State getButtonState(final AbstractButton b, final ButtonModel model) {
            final State state = super.getButtonState(b, model);

            if (state == State.INACTIVE) return State.INACTIVE;
            if (state == State.DISABLED) return State.DISABLED;
            if (model.isArmed() && model.isPressed()) return State.PRESSED;
            if (model.isSelected()) return State.ACTIVE;

            return state;
        }

        static boolean isIndeterminate(final AbstractButton b) {
            return "indeterminate".equals(b.getClientProperty("JButton.selectedState"));
        }
    }
}
