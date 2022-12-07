/*
 * Copyright (c) 2003, 2019, Oracle and/or its affiliates. All rights reserved.
 */

#include <jni.h>
#include "jvm.h"
#include "management.h"
#include "sun_management_ThreadImpl.h"

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_setThreadContentionMonitoringEnabled0
  (JNIEnv *env, jclass cls, jboolean flag)
{
    jmm_interface->SetBoolAttribute(env, JMM_THREAD_CONTENTION_MONITORING, flag);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_setThreadCpuTimeEnabled0
  (JNIEnv *env, jclass cls, jboolean flag)
{
    jmm_interface->SetBoolAttribute(env, JMM_THREAD_CPU_TIME, flag);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_setThreadAllocatedMemoryEnabled0
  (JNIEnv *env, jclass cls, jboolean flag)
{
    jmm_interface->SetBoolAttribute(env, JMM_THREAD_ALLOCATED_MEMORY, flag);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_getThreadInfo1
  (JNIEnv *env, jclass cls, jlongArray ids, jint maxDepth,
   jobjectArray infoArray)
{
    jmm_interface->GetThreadInfo(env, ids, maxDepth, infoArray);
}

JNIEXPORT jobjectArray JNICALL
Java_sun_management_ThreadImpl_getThreads
  (JNIEnv *env, jclass cls)
{
    return JVM_GetAllThreads(env, cls);
}

JNIEXPORT jlong JNICALL
Java_sun_management_ThreadImpl_getThreadTotalCpuTime0
  (JNIEnv *env, jclass cls, jlong tid)
{
    return jmm_interface->GetThreadCpuTimeWithKind(env, tid, JNI_TRUE /* user+sys */);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_getThreadTotalCpuTime1
  (JNIEnv *env, jclass cls, jlongArray ids, jlongArray timeArray)
{
    jmm_interface->GetThreadCpuTimesWithKind(env, ids, timeArray,
                                             JNI_TRUE /* user+sys */);
}

JNIEXPORT jlong JNICALL
Java_sun_management_ThreadImpl_getThreadUserCpuTime0
  (JNIEnv *env, jclass cls, jlong tid)
{
    return jmm_interface->GetThreadCpuTimeWithKind(env, tid, JNI_FALSE /* user */);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_getThreadUserCpuTime1
  (JNIEnv *env, jclass cls, jlongArray ids, jlongArray timeArray)
{
    jmm_interface->GetThreadCpuTimesWithKind(env, ids, timeArray,
                                             JNI_FALSE /* user */);
}

JNIEXPORT jlong JNICALL
Java_sun_management_ThreadImpl_getThreadAllocatedMemory0
  (JNIEnv *env, jclass cls, jlong tid)
{
  return jmm_interface->GetOneThreadAllocatedMemory(env, tid);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_getThreadAllocatedMemory1
  (JNIEnv *env, jclass cls, jlongArray ids, jlongArray sizeArray)
{
    jmm_interface->GetThreadAllocatedMemory(env, ids, sizeArray);
}

JNIEXPORT jobjectArray JNICALL
Java_sun_management_ThreadImpl_findMonitorDeadlockedThreads0
  (JNIEnv *env, jclass cls)
{
    return jmm_interface->FindCircularBlockedThreads(env);
}

JNIEXPORT jobjectArray JNICALL
Java_sun_management_ThreadImpl_findDeadlockedThreads0
  (JNIEnv *env, jclass cls)
{
    return jmm_interface->FindDeadlocks(env, JNI_FALSE /* !object_monitors_only */);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_resetPeakThreadCount0
  (JNIEnv *env, jclass cls)
{
    jvalue unused;
    unused.i = 0;
    jmm_interface->ResetStatistic(env, unused, JMM_STAT_PEAK_THREAD_COUNT);
}

JNIEXPORT void JNICALL
Java_sun_management_ThreadImpl_resetContentionTimes0
  (JNIEnv *env, jobject dummy, jlong tid)
{
    jvalue value;
    value.j = tid;
    jmm_interface->ResetStatistic(env, value, JMM_STAT_THREAD_CONTENTION_TIME);
}

JNIEXPORT jobjectArray JNICALL
Java_sun_management_ThreadImpl_dumpThreads0
  (JNIEnv *env, jclass cls, jlongArray ids, jboolean lockedMonitors,
  jboolean lockedSynchronizers, jint maxDepth)
{
    return jmm_interface->DumpThreadsMaxDepth(env, ids, lockedMonitors,
                                              lockedSynchronizers, maxDepth);
}
