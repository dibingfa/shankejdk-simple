/*
 * Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
 */

package jdk.jfr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event field annotation, specifies that the value is a frequency, measured in Hz.
 *
 * @since 8
 */
@MetadataDefinition
@ContentType
@Label("Frequency")
@Description("Measure of how often something occurs, in Hertz")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Frequency {
}
