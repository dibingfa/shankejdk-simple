/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
 */

/*
 */

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 */

package sun.text.resources.ro;

import java.util.ListResourceBundle;

public class CollationData_ro extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                "& A < a\u0306 , A\u0306 "       // a < a-breve
                + "& D < \u0111, \u0110 "        // d < d-stroke
                + "& I < i\u0302 , I\u0302 "     // i < i-circumflex
                + "& S < s\u0327 , S\u0327 "     // s < s-cedilla
                + "& \u00de < t\u0327 , T\u0327" // thorn < t-cedilla
                + "& Z < z\u0307 , Z\u0307 "     // tal : ezh-tail < z-dot-above
            }
        };
    }
}
