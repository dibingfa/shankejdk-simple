/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 */

package javax.jws.soap;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

/*
 * @Deprecated
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target({TYPE})
@Deprecated public @interface SOAPMessageHandlers {
    SOAPMessageHandler[] value();
}
