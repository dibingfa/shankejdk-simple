/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package javax.jws;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Retention(value=RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface WebResult {

    String name() default "";
    String partName() default "";
    String targetNamespace() default "";
    boolean header() default false;
}
