/*
 * Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights reserved.
 */

#include "java_net_InetAddressImplFactory.h"

#include "net_util.h"

/************************************************************************
 * InetAddressImplFactory
 */

/*
 * Class:     java_net_InetAddressImplFactory
 * Method:    isIPv6Supported
 * Signature: ()I
 */
JNIEXPORT jboolean JNICALL
Java_java_net_InetAddressImplFactory_isIPv6Supported(JNIEnv *env, jclass cls)
{
#ifdef AF_INET6
    if (ipv6_available()) {
        return JNI_TRUE;
    } else
#endif /* AF_INET6 */
        {
            return JNI_FALSE;
        }
}
