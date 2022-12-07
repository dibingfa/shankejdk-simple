/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package javax.jws.soap;

/*
 * @Deprecated
 */
@Deprecated public @interface SOAPMessageHandler {
    String name() default "";
    String className();
    InitParam[] initParams() default {};
    String[] roles() default {};
    String[] headers() default {};
}
