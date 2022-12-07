/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
 */

package sun.awt;

/**
 * Listener interface so Java Plug-in can be notified
 * of changes in AWT modality
 */
public interface ModalityListener {
   /**
    * Called by AWT when it enters a new level of modality
    */
    public void modalityPushed(ModalityEvent ev);

   /**
    * Called by AWT when it exits a level of modality
    */
    public void modalityPopped(ModalityEvent ev);
}
