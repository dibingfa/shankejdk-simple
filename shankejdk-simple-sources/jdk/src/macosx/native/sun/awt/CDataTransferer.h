/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <AppKit/AppKit.h>
#import "jni.h"

/*
 * Sets up the dictionary of numbers to NSStrings
 */
extern void initializeMappingTable();

/*
 * Convert from a standard NSPasteboard data type to an index in our mapping table.
 */
extern jlong indexForFormat(NSString *format);

/*
 * Inverse of above -- given a long int index, get the matching data format NSString.
 */
extern NSString* formatForIndex(jlong inFormatCode);

/*
 * Register a non-standard NSPasteboard data type in our mapping table and return its index.
 */
extern jlong registerFormatWithPasteboard(NSString *format);
