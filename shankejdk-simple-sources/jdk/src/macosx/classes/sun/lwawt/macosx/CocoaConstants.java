/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.lwawt.macosx;


public final class CocoaConstants {
    private CocoaConstants(){}

    //from the NSEvent class reference:
    public final static int NSLeftMouseDown      = 1;
    public final static int NSLeftMouseUp        = 2;
    public final static int NSRightMouseDown     = 3;
    public final static int NSRightMouseUp       = 4;
    public final static int NSMouseMoved         = 5;
    public final static int NSLeftMouseDragged   = 6;
    public final static int NSRightMouseDragged  = 7;
    public final static int NSMouseEntered       = 8;
    public final static int NSMouseExited        = 9;
    public final static int NSKeyDown            = 10;
    public final static int NSKeyUp              = 11;
    public final static int NSFlagsChanged       = 12;

    public final static int NSScrollWheel        = 22;
    public final static int NSOtherMouseDown     = 25;
    public final static int NSOtherMouseUp       = 26;
    public final static int NSOtherMouseDragged  = 27;

    public final static int AllLeftMouseEventsMask =
        1 << NSLeftMouseDown |
        1 << NSLeftMouseUp |
        1 << NSLeftMouseDragged;

    public final static int AllRightMouseEventsMask =
        1 << NSRightMouseDown |
        1 << NSRightMouseUp |
        1 << NSRightMouseDragged;

    public final static int AllOtherMouseEventsMask =
        1 << NSOtherMouseDown |
        1 << NSOtherMouseUp |
        1 << NSOtherMouseDragged;

    /*
    NSAppKitDefined      = 13,
    NSSystemDefined      = 14,
    NSApplicationDefined = 15,
    NSPeriodic           = 16,
    NSCursorUpdate       = 17,
    NSScrollWheel        = 22,
    NSTabletPoint        = 23,
    NSTabletProximity    = 24,
    NSEventTypeGesture   = 29,
    NSEventTypeMagnify   = 30,
    NSEventTypeSwipe     = 31,
    NSEventTypeRotate    = 18,
    NSEventTypeBeginGesture = 19,
    NSEventTypeEndGesture   = 20
    */

    // See http://developer.apple.com/library/mac/#documentation/Carbon/Reference/QuartzEventServicesRef/Reference/reference.html

    public final static int kCGMouseButtonLeft   = 0;
    public final static int kCGMouseButtonRight  = 1;
    public final static int kCGMouseButtonCenter = 2;

    // See https://wiki.mozilla.org/NPAPI:CocoaEventModel

    public final static int NPCocoaEventDrawRect           = 1;
    public final static int NPCocoaEventMouseDown          = 2;
    public final static int NPCocoaEventMouseUp            = 3;
    public final static int NPCocoaEventMouseMoved         = 4;
    public final static int NPCocoaEventMouseEntered       = 5;
    public final static int NPCocoaEventMouseExited        = 6;
    public final static int NPCocoaEventMouseDragged       = 7;
    public final static int NPCocoaEventKeyDown            = 8;
    public final static int NPCocoaEventKeyUp              = 9;
    public final static int NPCocoaEventFlagsChanged       = 10;
    public final static int NPCocoaEventFocusChanged       = 11;
    public final static int NPCocoaEventWindowFocusChanged = 12;
    public final static int NPCocoaEventScrollWheel        = 13;
    public final static int NPCocoaEventTextInput          = 14;
}
