/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */


#import <CoreFoundation/CoreFoundation.h>
#import <ApplicationServices/ApplicationServices.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>

/*
 * Class:     sun_lwawt_macosx_CDesktopPeer
 * Method:    _lsOpenURI
 * Signature: (Ljava/lang/String;)I;
 */
JNIEXPORT jint JNICALL Java_sun_lwawt_macosx_CDesktopPeer__1lsOpenURI
(JNIEnv *env, jclass clz, jstring uri)
{
    OSStatus status = noErr;
JNF_COCOA_ENTER(env);

    // I would love to use NSWorkspace here, but it's not thread safe. Why? I don't know.
    // So we use LaunchServices directly.

    NSURL *url = [NSURL URLWithString:JNFJavaToNSString(env, uri)];

    LSLaunchFlags flags = kLSLaunchDefaults;

    LSApplicationParameters params = {0, flags, NULL, NULL, NULL, NULL, NULL};
    status = LSOpenURLsWithRole((CFArrayRef)[NSArray arrayWithObject:url], kLSRolesAll, NULL, &params, NULL, 0);

JNF_COCOA_EXIT(env);
    return status;
}

/*
 * Class:     sun_lwawt_macosx_CDesktopPeer
 * Method:    _lsOpenFile
 * Signature: (Ljava/lang/String;Z)I;
 */
JNIEXPORT jint JNICALL Java_sun_lwawt_macosx_CDesktopPeer__1lsOpenFile
(JNIEnv *env, jclass clz, jstring jpath, jboolean print)
{
    OSStatus status = noErr;
JNF_COCOA_ENTER(env);

    // I would love to use NSWorkspace here, but it's not thread safe. Why? I don't know.
    // So we use LaunchServices directly.

    NSString *path  = JNFNormalizedNSStringForPath(env, jpath);

    NSURL *url = [NSURL fileURLWithPath:(NSString *)path];

    // This byzantine workaround is necesary, or else directories won't open in Finder
    url = (NSURL *)CFURLCreateWithFileSystemPath(NULL, (CFStringRef)[url path], kCFURLPOSIXPathStyle, false);

    LSLaunchFlags flags = kLSLaunchDefaults;
    if (print) flags |= kLSLaunchAndPrint;

    LSApplicationParameters params = {0, flags, NULL, NULL, NULL, NULL, NULL};
    status = LSOpenURLsWithRole((CFArrayRef)[NSArray arrayWithObject:url], kLSRolesAll, NULL, &params, NULL, 0);
    [url release];

JNF_COCOA_EXIT(env);
    return status;
}

