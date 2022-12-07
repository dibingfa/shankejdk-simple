/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package sun.net.httpserver;

class WriteFinishedEvent extends Event {
    WriteFinishedEvent (ExchangeImpl t) {
        super (t);
        assert !t.writefinished;
        t.writefinished = true;
    }
}
