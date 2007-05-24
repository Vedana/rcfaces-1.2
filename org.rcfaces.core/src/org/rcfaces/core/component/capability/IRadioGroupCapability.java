/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRadioGroupCapability {

    /**
     * Returns a string specifying the name of the virtual entity that links
     * different components together.
     * 
     * @return group name
     */
    String getGroupName();

    /**
     * Sets a string specifying the name of the virtual entity that links
     * different components together.
     * 
     * @param groupName
     *            group name
     */
    void setGroupName(String groupName);
}