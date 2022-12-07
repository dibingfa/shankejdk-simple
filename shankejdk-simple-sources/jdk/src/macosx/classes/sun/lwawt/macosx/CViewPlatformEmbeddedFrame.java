/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.lwawt.macosx;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.Window;
import sun.awt.CausedFocusEvent.Cause;
import sun.java2d.SurfaceData;
import sun.lwawt.LWWindowPeer;
import sun.lwawt.PlatformWindow;

public class CViewPlatformEmbeddedFrame implements PlatformWindow {

    private CPlatformView view;
    private LWWindowPeer peer;
    private CViewEmbeddedFrame target;
    private CPlatformResponder responder;

    @Override // PlatformWindow
    public void initialize(Window target, final LWWindowPeer peer, PlatformWindow owner) {
        this.peer = peer;
        this.target = (CViewEmbeddedFrame) target;
        responder = new CPlatformResponder(peer, false);

        view = new CPlatformView();
        view.initialize(peer, responder);

        CWrapper.NSView.addSubview(this.target.getEmbedderHandle(), view.getAWTView());
        view.setAutoResizable(true);
    }

    public long getNSViewPtr() {
        return view.getAWTView();
    }

    @Override
    public long getLayerPtr() {
        return view.getWindowLayerPtr();
    }

    @Override
    public LWWindowPeer getPeer() {
        return peer;
    }

    @Override
    public void dispose() {
        view.execute(CWrapper.NSView::removeFromSuperview);
        view.dispose();
    }

    @Override
    public void setVisible(boolean visible) {
        view.execute(ptr -> CWrapper.NSView.setHidden(ptr, !visible));
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        view.setBounds(x, y, w, h);
        peer.notifyReshape(x, y, w, h);
    }

    @Override
    public GraphicsDevice getGraphicsDevice() {
        return view.getGraphicsDevice();
    }

    @Override
    public Point getLocationOnScreen() {
        return view.getLocationOnScreen();
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public SurfaceData getScreenSurface() {
        return view.getSurfaceData();
    }

    @Override
    public SurfaceData replaceSurfaceData() {
        return view.replaceSurfaceData();
    }

    @Override
    public void setModalBlocked(boolean blocked) {
    }

    @Override
    public void toFront() {
    }

    @Override
    public void toBack() {
    }

    @Override
    public void setMenuBar(MenuBar mb) {
    }

    @Override
    public void setAlwaysOnTop(boolean value) {
    }

    @Override
    public void updateFocusableWindowState() {
    }

    @Override
    public boolean rejectFocusRequest(Cause cause) {
        return false;
    }

    @Override
    public boolean requestWindowFocus() {
        return true;
    }

    @Override
    public boolean isActive() {
        return target.isParentWindowActive();
    }

    @Override
    public void setResizable(boolean resizable) {
    }

    @Override
    public void setSizeConstraints(int minW, int minH, int maxW, int maxH) {
    }

    @Override
    public Graphics transformGraphics(Graphics g) {
        return g;
    }

    @Override
    public void updateIconImages() {
    }

    @Override
    public void setOpacity(float opacity) {
    }

    @Override
    public void setOpaque(boolean isOpaque) {
    }

    @Override
    public void enterFullScreenMode() {
    }

    @Override
    public void exitFullScreenMode() {
    }

    @Override
    public boolean isFullScreenMode() {
        return false;
    }

    @Override
    public void setWindowState(int windowState) {
    }

    @Override
    public boolean isUnderMouse() {
        return view.isUnderMouse();
    }
}
