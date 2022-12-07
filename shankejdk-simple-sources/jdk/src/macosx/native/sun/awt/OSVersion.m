/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

// Support for detecting Mac OS X Versions

#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#import <JavaRuntimeSupport/JavaRuntimeSupport.h>


// returns 107 for Lion, 106 for SnowLeopard etc.
int getOSXMajorVersion() {
    char *ver = JRSCopyOSVersion();
    if (ver == NULL) { 
        return 0;
    }

    int len = strlen(ver);
    int v = 0;
    
    // Third char must be a '.'    
    if (len >= 3 && ver[2] == '.') {
        int i;
        
        v = (ver[0] - '0') * 10 + (ver[1] - '0');
        for (i = 3; i < len && isdigit(ver[i]); ++i) {
            v = v * 10 + (ver[i] - '0');
        }
    }

    free(ver);
    
    return v;
}

BOOL isSnowLeopardOrLower() {
    return (getOSXMajorVersion() < 107);
}
