/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package sun.net.httpserver;

import com.sun.net.httpserver.*;
import com.sun.net.httpserver.spi.*;

class Event {

    ExchangeImpl exchange;

    protected Event (ExchangeImpl t) {
        this.exchange = t;
    }
}
