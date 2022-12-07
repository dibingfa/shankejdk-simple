/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.sun.tools.internal.ws.processor.modeler.annotation;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

/**
 *
 * @author  dkohlert
 */
public interface TypeMoniker {

    public TypeMirror create(ProcessingEnvironment apEnv);
}
