/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef JDWP_STANDARDHANDLERS_H
#define JDWP_STANDARDHANDLERS_H

#include "eventHandler.h"

HandlerFunction standardHandlers_defaultHandler(EventIndex ei);

void standardHandlers_onConnect(void);
void standardHandlers_onDisconnect(void);

#endif
