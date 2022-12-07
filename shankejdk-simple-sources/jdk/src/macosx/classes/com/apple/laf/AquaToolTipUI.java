/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.apple.laf;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

import com.apple.laf.AquaUtils.RecyclableSingletonFromDefaultConstructor;

public class AquaToolTipUI extends BasicToolTipUI {
    static final RecyclableSingletonFromDefaultConstructor<AquaToolTipUI> sharedAquaInstance = new RecyclableSingletonFromDefaultConstructor<AquaToolTipUI>(AquaToolTipUI.class);

    public static ComponentUI createUI(final JComponent c) {
        return sharedAquaInstance.get();
    }

    public AquaToolTipUI() {
        super();
    }
}
