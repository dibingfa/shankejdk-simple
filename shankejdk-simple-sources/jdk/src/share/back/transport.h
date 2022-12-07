/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef JDWP_TRANSPORT_H
#define JDWP_TRANSPORT_H

#include "jdwpTransport.h"

void transport_initialize(void);
void transport_reset(void);
jdwpError transport_startTransport(jboolean isServer, char *name, char *address, long timeout);

jint transport_receivePacket(jdwpPacket *);
jint transport_sendPacket(jdwpPacket *);
jboolean transport_is_open(void);
void transport_waitForConnection(void);
void transport_close(void);

#endif
