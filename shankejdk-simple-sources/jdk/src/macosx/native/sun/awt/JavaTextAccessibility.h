/*
 * Copyright (c) 2011, 2016, Oracle and/or its affiliates. All rights reserved.
 */

#import "JavaComponentAccessibility.h"

#import <AppKit/NSAccessibility.h>


@interface JavaTextAccessibility : JavaComponentAccessibility {

}
// attributes
- (NSArray *)initializeAttributeNamesWithEnv:(JNIEnv *)env;
- (NSString *)accessibilityValueAttribute;
- (BOOL)accessibilityIsValueAttributeSettable;
- (void)accessibilitySetValueAttribute:(id)value;
- (NSString *)accessibilitySelectedTextAttribute;
- (BOOL)accessibilityIsSelectedTextAttributeSettable;
- (NSValue *)accessibilitySelectedTextRangeAttribute;
- (BOOL)accessibilityIsSelectedTextRangeAttributeSettable;
- (NSNumber *)accessibilityNumberOfCharactersAttribute;
- (BOOL)accessibilityIsNumberOfCharactersAttributeSettable;
- (NSValue *)accessibilityVisibleCharacterRangeAttribute;
- (BOOL)accessibilityIsVisibleCharacterRangeAttributeSettable;
- (NSValue *)accessibilityInsertionPointLineNumberAttribute;
- (BOOL)accessibilityIsInsertionPointLineNumberAttributeSettable;
- (void)accessibilitySetSelectedTextAttribute:(id)value;
- (NSValue *)accessibilitySelectedTextRangeAttribute;
- (NSValue *)accessibilityInsertionPointLineNumberAttribute;
- (BOOL)accessibilityIsInsertionPointLineNumberAttributeSettable;

// parameterized attributes
- (NSArray *)accessibilityParameterizedAttributeNames;
- (NSValue *)accessibilityBoundsForRangeAttributeForParameter:(id)parameter;
- (NSNumber *)accessibilityLineForIndexAttributeForParameter:(id)parameter;
- (NSValue *)accessibilityRangeForLineAttributeForParameter:(id)parameter;
- (NSString *)accessibilityStringForRangeAttributeForParameter:(id)parameter;
- (NSValue *)accessibilityRangeForPositionAttributeForParameter:(id)parameter;
- (NSValue *)accessibilityRangeForIndexAttributeForParameter:(id)parameter;

@end
