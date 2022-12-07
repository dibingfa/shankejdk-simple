/*
 * Copyright (c) 1999, 2012, Oracle and/or its affiliates. All rights reserved.
 */
#include "jdwpTransport.h"

#ifndef JAVASOFT_SHMEMBASE_H
#define JAVASOFT_SHMEMBASE_H

void exitTransportWithError(char *msg, char *fileName,
                            char *date, int lineNumber);

typedef struct SharedMemoryConnection SharedMemoryConnection;
typedef struct SharedMemoryTransport SharedMemoryTransport;

typedef void * (*SharedMemAllocFunc)(jint);
typedef void  (*SharedMemFreeFunc)(void);

jint shmemBase_initialize(JavaVM *, jdwpTransportCallback *callback);
jint shmemBase_listen(const char *address, SharedMemoryTransport **);
jint shmemBase_accept(SharedMemoryTransport *, long, SharedMemoryConnection **);
jint shmemBase_attach(const char *addressString, long, SharedMemoryConnection **);
void shmemBase_closeConnection(SharedMemoryConnection *);
void shmemBase_closeTransport(SharedMemoryTransport *);
jint shmemBase_sendByte(SharedMemoryConnection *, jbyte data);
jint shmemBase_receiveByte(SharedMemoryConnection *, jbyte *data);
jint shmemBase_sendPacket(SharedMemoryConnection *, const jdwpPacket *packet);
jint shmemBase_receivePacket(SharedMemoryConnection *, jdwpPacket *packet);
jint shmemBase_name(SharedMemoryTransport *, char **name);
jint shmemBase_getlasterror(char *msg, jint size);

/* Use THIS_FILE when it is available. */
#ifndef THIS_FILE
    #define THIS_FILE __FILE__
#endif

#ifdef DEBUG
#define SHMEM_ASSERT(expression)  \
do {                            \
    if (!(expression)) {                \
        exitTransportWithError("assertion failed", THIS_FILE, __DATE__, __LINE__); \
    } \
} while (0)
#else
#define SHMEM_ASSERT(expression) ((void) 0)
#endif

#define SHMEM_GUARANTEE(expression) \
do {                            \
    if (!(expression)) {                \
        exitTransportWithError("assertion failed", THIS_FILE, __DATE__, __LINE__); \
    } \
} while (0)

#endif
