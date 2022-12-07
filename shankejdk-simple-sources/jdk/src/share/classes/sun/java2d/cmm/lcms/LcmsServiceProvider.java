/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 */

package sun.java2d.cmm.lcms;

import sun.java2d.cmm.CMMServiceProvider;
import sun.java2d.cmm.PCMM;

public final class LcmsServiceProvider extends CMMServiceProvider {
    @Override
    protected PCMM getModule() {
        return LCMS.getModule();
    }
}
