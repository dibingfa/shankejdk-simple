/*
 * Copyright (c) 2002, 2006, Oracle and/or its affiliates. All rights reserved.
 */

#include <sys/types.h>
#include <string.h>
#include "jni.h"
#include "jni_util.h"
#include "jvm.h"
#include "jlong.h"
#include "sun_nio_ch_NativeThread.h"
#include "nio_util.h"

#ifdef __linux__
  #include <pthread.h>
  #include <sys/signal.h>
  /* Also defined in net/linux_close.c */
  #define INTERRUPT_SIGNAL (__SIGRTMAX - 2)
#elif __solaris__
  #include <thread.h>
  #include <signal.h>
  #define INTERRUPT_SIGNAL (SIGRTMAX - 2)
#elif _ALLBSD_SOURCE
  #include <pthread.h>
  #include <signal.h>
  /* Also defined in net/bsd_close.c */
  #define INTERRUPT_SIGNAL SIGIO
#else
  #error "missing platform-specific definition here"
#endif

static void
nullHandler(int sig)
{
}

JNIEXPORT void JNICALL
Java_sun_nio_ch_NativeThread_init(JNIEnv *env, jclass cl)
{
    /* Install the null handler for INTERRUPT_SIGNAL.  This might overwrite the
     * handler previously installed by java/net/linux_close.c, but that's okay
     * since neither handler actually does anything.  We install our own
     * handler here simply out of paranoia; ultimately the two mechanisms
     * should somehow be unified, perhaps within the VM.
     */

    sigset_t ss;
    struct sigaction sa, osa;
    sa.sa_handler = nullHandler;
    sa.sa_flags = 0;
    sigemptyset(&sa.sa_mask);
    if (sigaction(INTERRUPT_SIGNAL, &sa, &osa) < 0)
        JNU_ThrowIOExceptionWithLastError(env, "sigaction");
}

JNIEXPORT jlong JNICALL
Java_sun_nio_ch_NativeThread_current(JNIEnv *env, jclass cl)
{
#ifdef __solaris__
    return (jlong)thr_self();
#else
    return (jlong)pthread_self();
#endif
}

JNIEXPORT void JNICALL
Java_sun_nio_ch_NativeThread_signal(JNIEnv *env, jclass cl, jlong thread)
{
    int ret;
#ifdef __solaris__
    ret = thr_kill((thread_t)thread, INTERRUPT_SIGNAL);
#else
    ret = pthread_kill((pthread_t)thread, INTERRUPT_SIGNAL);
#endif
    if (ret != 0)
        JNU_ThrowIOExceptionWithLastError(env, "Thread signal failed");
}
