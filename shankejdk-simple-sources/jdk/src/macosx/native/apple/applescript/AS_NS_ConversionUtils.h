/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

#import <Foundation/Foundation.h>


// A 'collection' (responds to -objectEnumerator) is translated to an AS list.
// For any other object obj, this returns [[obj description] aeDescriptorValue], mainly
// intended for debugging purposes.
@interface NSObject (JavaAppleScriptEngineAdditions)
- (NSAppleEventDescriptor *) aeDescriptorValue;
@end

@interface NSAppleEventDescriptor (JavaAppleScriptEngineAdditions)
- (id) objCObjectValue;
@end
