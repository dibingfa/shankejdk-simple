/*
 * Copyright (c) 1998, 2005, Oracle and/or its affiliates. All rights reserved.
 */

#include "util.h"
#include "InterfaceTypeImpl.h"
#include "inStream.h"
#include "outStream.h"

static jboolean
invokeStatic(PacketInputStream *in, PacketOutputStream *out)
{
    return sharedInvoke(in, out);
}

void *InterfaceType_Cmds[] = { (void *)0x1
    , (void *)invokeStatic
};
