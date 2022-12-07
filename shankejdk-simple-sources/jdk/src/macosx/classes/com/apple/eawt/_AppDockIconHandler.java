/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.eawt;

import java.awt.*;
import java.lang.reflect.*;

import sun.lwawt.macosx.*;
import sun.lwawt.macosx.CImage.Creator;

class _AppDockIconHandler {
    private static native void nativeSetDockMenu(final long cmenu);
    private static native void nativeSetDockIconImage(final long image);
    private static native long nativeGetDockIconImage();
    private static native void nativeSetDockIconBadge(final String badge);

    PopupMenu fDockMenu = null;

    _AppDockIconHandler() { }

    @SuppressWarnings("deprecation")
    public void setDockMenu(final PopupMenu menu) {
        fDockMenu = menu;

        // clear the menu if explicitly passed null
        if (menu == null) {
            nativeSetDockMenu(0);
            return;
        }

        // check if the menu needs a parent (8343136)
        final MenuContainer container = menu.getParent();
        if (container == null) {
            final MenuBar newParent = new MenuBar();
            newParent.add(menu);
            newParent.addNotify();
        }

        // instantiate the menu peer and set the native fDockMenu ivar
        menu.addNotify();
        final long nsMenuPtr = ((CMenu)fDockMenu.getPeer()).getNativeMenu();
        nativeSetDockMenu(nsMenuPtr);
    }

    public PopupMenu getDockMenu() {
        return fDockMenu;
    }

    public void setDockIconImage(final Image image) {
        try {
            final CImage cImage = getCImageCreator().createFromImage(image);
            cImage.execute(_AppDockIconHandler::nativeSetDockIconImage);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    Image getDockIconImage() {
        try {
            final long dockNSImage = nativeGetDockIconImage();
            if (dockNSImage == 0) return null;
            return getCImageCreator().createImageUsingNativeSize(dockNSImage);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    void setDockIconBadge(final String badge) {
        nativeSetDockIconBadge(badge);
    }

    static Creator getCImageCreator() {
        try {
            final Method getCreatorMethod = CImage.class.getDeclaredMethod("getCreator", new Class[] {});
            getCreatorMethod.setAccessible(true);
            return (Creator)getCreatorMethod.invoke(null, new Object[] {});
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
