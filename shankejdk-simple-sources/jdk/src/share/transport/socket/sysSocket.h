/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
 */
#ifndef _JAVASOFT_WIN32_SOCKET_MD_H

#include <jni.h>
#include <sys/types.h>
#include "sys.h"
#include "socket_md.h"

#define DBG_POLLIN              1
#define DBG_POLLOUT             2

#define DBG_EINPROGRESS         -150
#define DBG_ETIMEOUT            -200
#ifdef WIN32
typedef int socklen_t;
#endif

int dbgsysSocketClose(int fd);
int dbgsysConnect(int fd, struct sockaddr *him, socklen_t len);
int dbgsysFinishConnect(int fd, int timeout);
int dbgsysAccept(int fd, struct sockaddr *him, socklen_t *len);
int dbgsysSendTo(int fd, char *buf, size_t len, int flags, struct sockaddr *to, socklen_t tolen);
int dbgsysRecvFrom(int fd, char *buf, size_t nBytes, int flags, struct sockaddr *from, socklen_t *fromlen);
int dbgsysListen(int fd, int backlog);
int dbgsysRecv(int fd, char *buf, size_t nBytes, int flags);
int dbgsysSend(int fd, char *buf, size_t nBytes, int flags);
struct hostent *dbgsysGetHostByName(char *hostname);
int dbgsysSocket(int domain, int type, int protocol);
int dbgsysBind(int fd, struct sockaddr *name, socklen_t namelen);
int dbgsysSetSocketOption(int fd, jint cmd, jboolean on, jvalue value);
uint32_t dbgsysInetAddr(const char* cp);
uint32_t dbgsysHostToNetworkLong(uint32_t hostlong);
unsigned short dbgsysHostToNetworkShort(unsigned short hostshort);
uint32_t dbgsysNetworkToHostLong(uint32_t netlong);
unsigned short dbgsysNetworkToHostShort(unsigned short netshort);
int dbgsysGetSocketName(int fd, struct sockaddr *him, socklen_t *len);
int dbgsysConfigureBlocking(int fd, jboolean blocking);
int dbgsysPoll(int fd, jboolean rd, jboolean wr, long timeout);
int dbgsysGetLastIOError(char *buf, jint size);
long dbgsysCurrentTimeMillis();

/*
 * TLS support
 */
int dbgsysTlsAlloc();
void dbgsysTlsFree(int index);
void dbgsysTlsPut(int index, void *value);
void* dbgsysTlsGet(int index);

#endif
