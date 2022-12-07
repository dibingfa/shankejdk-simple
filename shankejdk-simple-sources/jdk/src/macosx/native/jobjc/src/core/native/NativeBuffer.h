/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/*
 *  NativeBuffer.h
 *  Copyright 2007 Apple Inc. All rights reserved.
 *
 */

#define BUFFER_AT(buffer, offset)                (((UInt8 *)buffer) + offset)
#define GET_VALUE(type, buffer, offset)            (*((type *)BUFFER_AT(buffer, offset)))
#define PUT_VALUE(type, buffer, offset, value)    (*((type *)BUFFER_AT(buffer, offset)) = value)

#define GET_INT_AT(buffer, offset)                GET_VALUE(jint, buffer, offset)
#define GET_LONG_AT(buffer, offset)                GET_VALUE(jlong, buffer, offset)

#define PUT_INT_AT(buffer, offset, value)        PUT_VALUE(jint, buffer, offset, value)
#define PUT_LONG_AT(buffer, offset, value)        PUT_VALUE(jlong, buffer, offset, value)
