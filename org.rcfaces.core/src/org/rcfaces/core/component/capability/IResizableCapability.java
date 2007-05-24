/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResizableCapability {

    /**
     * Returns a boolean value indicating wether the component can be resized by
     * the user.
     * 
     * @return resizeable boolean property
     */
    boolean isResizable();

    /**
     * Sets a boolean value indicating wether the component can be resized by
     * the user.
     * 
     * @param resizable
     *            resizeable boolean property
     */
    void setResizable(boolean resizable);
}
