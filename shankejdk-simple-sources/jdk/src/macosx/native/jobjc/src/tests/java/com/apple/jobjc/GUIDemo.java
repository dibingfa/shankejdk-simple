/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */
package com.apple.jobjc;

import com.apple.jobjc.Utils.Strings;
import com.apple.jobjc.appkit.NSApplication;
import com.apple.jobjc.appkit.NSButton;
import com.apple.jobjc.appkit.NSDrawer;
import com.apple.jobjc.appkit.NSMenu;
import com.apple.jobjc.appkit.NSWindow;
import com.apple.jobjc.foundation.NSAutoreleasePool;
import com.apple.jobjc.foundation.NSObject;
import com.apple.jobjc.foundation.NSObjectClass;
import com.apple.jobjc.foundation.NSRect;
import com.apple.jobjc.foundation.NSSize;

class MyDelegate extends NSObject{
    static final JObjC objc = JObjC.getInstance();
    static final Strings str = Utils.get().strings();

    public MyDelegate(long ptr, JObjCRuntime r) { super(ptr, r); }

    private NSWindow myWindow;
    private NSDrawer myDrawer;

    public void printHello(ID sender){
        System.out.println("Hello!");
        myDrawer.toggle(this);
    }

    public void createMenu(){
        NSMenu menu = objc.AppKit().NSMenu().newID();
        menu.addItemWithTitle_action_keyEquivalent(
                str.nsString("Quit"),
                new SEL("terminate:"),
                str.nsString("q"));
        NSApplication app = objc.AppKit().NSApp();
        app.setMainMenu(menu);
    }

    public void createWindow(){
        NSRect rect;
        int styleMask = objc.AppKit().NSTitledWindowMask() | objc.AppKit().NSMiniaturizableWindowMask();
        NSButton myButton;
        NSSize buttonSize;
        myButton = objc.AppKit().NSButton().newID();
        myButton.setTitle(str.nsString("Print Hello!"));
        myButton.sizeToFit();
        myButton.setTarget(this);
        myButton.setAction(new SEL("printHello:"));
        buttonSize = myButton.frame().size();
        rect = objc.Foundation().NSMakeRect(100, 100, 2*buttonSize.width(), 2*buttonSize.height());
        myWindow = objc.AppKit().NSWindow().alloc();
        myWindow = myWindow.initWithContentRect_styleMask_backing_defer(
                rect, styleMask, objc.AppKit().NSBackingStoreBuffered(), false);
        myWindow.setTitle(str.nsString("This is a test window."));
        myWindow.setContentView(myButton);

        myDrawer = objc.AppKit().NSDrawer().alloc();
        myDrawer = myDrawer.initWithContentSize_preferredEdge(
                  objc.Foundation().NSMakeSize(100, 40), objc.Foundation().NSMinYEdge());
        myDrawer.setParentWindow(myWindow);
    }

    public void applicationWillFinishLaunching(ID not){
        createMenu();
        createWindow();
    }

    public void applicationDidFinishLaunching(ID not){
        myWindow.makeKeyAndOrderFront(null);
    }
}

class MyDelegateClass extends NSObjectClass{
    public MyDelegateClass(JObjCRuntime r){ super(r); }
}

public class GUIDemo{
    static final JObjC objc = JObjC.getInstance();

    public static void main(String[] args){
        JObjCRuntime.getInstance().registerUserClass(MyDelegate.class, MyDelegateClass.class);

        objc.AppKit().NSApplication().sharedApplication();
        NSApplication app = objc.AppKit().NSApp();

        NSAutoreleasePool pool = objc.Foundation().NSAutoreleasePool().alloc();
        pool = pool.init();
        app.setDelegate(new MyDelegateClass(JObjCRuntime.getInstance()).newID());
        app.run();
        pool.drain();
    }
}
