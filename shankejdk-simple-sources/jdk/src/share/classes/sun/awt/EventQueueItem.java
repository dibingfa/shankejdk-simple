/*
 * Copyright (c) 2008, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt;

import java.awt.AWTEvent;

public class EventQueueItem {
    public AWTEvent event;
    public EventQueueItem next;

    public EventQueueItem(AWTEvent evt) {
        event = evt;
    }
}
