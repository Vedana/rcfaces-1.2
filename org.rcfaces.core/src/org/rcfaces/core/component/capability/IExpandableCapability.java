/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IExpandableCapability {

    /**
     * Returns a boolean value indicating wether the component can receive a
     * user's expand command.
     * 
     * @return expandable boolean property
     */
    boolean isExpandable();

    /**
     * Sets a boolean value indicating wether the component can receive a user's
     * expand command.
     * 
     * @param expandable
     *            expandable boolean property
     */
    void setExpandable(boolean expandable);
}
