/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.jdi;

import com.sun.jdi.*;
import java.util.EventListener;

interface CommandSender {
    PacketStream send();
}
