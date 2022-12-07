/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.lwawt;

import java.awt.*;

import sun.awt.CausedFocusEvent;
import sun.java2d.SurfaceData;

// TODO Is it worth to generify this interface, like that:
//
// public interface PlatformWindow<WindowType extends Window>
//
// ?

public interface PlatformWindow {

    /*
     * Delegate initialization (create native window and all the
     * related resources).
     */
    public void initialize(Window target, LWWindowPeer peer, PlatformWindow owner);

    /*
     * Delegate shutdown (dispose native window and all the
     * related resources).
     */
    public void dispose();

    /*
     * Shows or hides the window.
     */
    public void setVisible(boolean visible);

    /*
     * Sets the window title
     */
    public void setTitle(String title);

    /*
     * Sets the window bounds. Called when user changes window bounds
     * with setSize/setLocation/setBounds/reshape methods.
     */
    public void setBounds(int x, int y, int w, int h);

    /*
     * Returns the graphics device where the window is.
     */
    public GraphicsDevice getGraphicsDevice();

    /*
     * Returns the location of the window.
     */
    public Point getLocationOnScreen();

    /*
     * Returns the window insets.
     */
    public Insets getInsets();

    /*
     * Returns the metrics for a given font.
     */
    public FontMetrics getFontMetrics(Font f);

    /*
     * Get the SurfaceData for the window.
     */
    public SurfaceData getScreenSurface();

    /*
     * Revalidates the window's current SurfaceData and returns
     * the newly created one.
     */
    public SurfaceData replaceSurfaceData();

    public void setModalBlocked(boolean blocked);

    public void toFront();

    public void toBack();

    public void setMenuBar(MenuBar mb);

    public void setAlwaysOnTop(boolean value);

    public void updateFocusableWindowState();

    public boolean rejectFocusRequest(CausedFocusEvent.Cause cause);

    public boolean requestWindowFocus();

    /*
     * Returns true only when called on a frame/dialog when it's natively focused.
     */
    public boolean isActive();

    public void setResizable(boolean resizable);

    /**
     * Applies the minimum and maximum size to the platform window.
     */
    public void setSizeConstraints(int minW, int minH, int maxW, int maxH);

    /**
     * Transforms the given Graphics object according to the native
     * implementation traits (insets, etc.).
     */
    public Graphics transformGraphics(Graphics g);

    /*
     * Installs the images for particular window.
     */
    public void updateIconImages();

    public void setOpacity(float opacity);

    public void setOpaque(boolean isOpaque);

    public void enterFullScreenMode();

    public void exitFullScreenMode();

    public boolean isFullScreenMode();

    public void setWindowState(int windowState);

    public long getLayerPtr();

    public LWWindowPeer getPeer();

    public boolean isUnderMouse();
}
