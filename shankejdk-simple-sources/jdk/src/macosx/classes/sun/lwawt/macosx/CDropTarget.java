/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.lwawt.macosx;

import java.awt.Component;
import java.awt.peer.ComponentPeer;
import java.awt.dnd.DropTarget;

import sun.lwawt.LWComponentPeer;
import sun.lwawt.PlatformWindow;


public final class CDropTarget {

    Component        fComponent;
    ComponentPeer    fPeer;
    DropTarget        fDropTarget;
    private long    fNativeDropTarget;

    public static CDropTarget createDropTarget(DropTarget dropTarget, Component component, ComponentPeer peer) {
        return new CDropTarget(dropTarget, component, peer);
    }

    private CDropTarget(DropTarget dropTarget, Component component, ComponentPeer peer) {
        super();

        fDropTarget = dropTarget;
        fComponent = component;
        fPeer = peer;

        long nativePeer = CPlatformWindow.getNativeViewPtr(((LWComponentPeer) peer).getPlatformWindow());
        if (nativePeer == 0L) return; // Unsupported for a window without a native view (plugin)

        // Create native dragging destination:
        fNativeDropTarget = this.createNativeDropTarget(dropTarget, component, peer, nativePeer);
        if (fNativeDropTarget == 0) {
            throw new IllegalStateException("CDropTarget.createNativeDropTarget() failed.");
        }
    }

    public DropTarget getDropTarget() {
        return fDropTarget;
    }

    public void dispose() {
        // Release native dragging destination, if any:
        if (fNativeDropTarget != 0) {
            this.releaseNativeDropTarget(fNativeDropTarget);
            fNativeDropTarget = 0;
        }
    }

    protected native long createNativeDropTarget(DropTarget dropTarget, Component component, ComponentPeer peer, long nativePeer);
    protected native void releaseNativeDropTarget(long nativeDropTarget);
}
