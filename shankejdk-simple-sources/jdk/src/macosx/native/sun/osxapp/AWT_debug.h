/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#ifndef macosx_port_awt_debug_h
#define macosx_port_awt_debug_h

#import <Cocoa/Cocoa.h>


#define kInternalError "java/lang/InternalError"

#define AWT_DEBUG_LOG(str) \
    NSLog(@"Cocoa AWT: %@ %@", str, [NSThread callStackSymbols])

#define AWT_DEBUG_BUG_REPORT_MESSAGE \
    NSLog(@"\tPlease file a bug report at http://java.net/jira/browse/MACOSX_PORT with this message and a reproducible test case.")

#endif
