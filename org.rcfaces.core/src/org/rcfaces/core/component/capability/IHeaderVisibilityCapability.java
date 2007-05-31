/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHeaderVisibilityCapability {

    /**
     * Returns a boolean value indicating wether the header should be visible.
     * 
     * @return true if the header is visible
     */
    boolean isHeaderVisible();

    /**
     * Sets a boolean value indicating wether the header should be visible.
     * 
     * @param headerVisible
     *            true if the header should be visible
     */
    void setHeaderVisible(boolean headerVisible);

}
