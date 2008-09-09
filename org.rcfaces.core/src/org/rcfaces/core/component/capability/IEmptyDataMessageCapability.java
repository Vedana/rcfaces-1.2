/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A string containing the message shown when there is no result.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IEmptyDataMessageCapability {

    /**
     * Returns a string containing the message shown when there is no result.
     * 
     * @return message
     */
    String getEmptyDataMessage();

    /**
     * Sets the string conatining the message shown when there is no result.
     * 
     * @param emptyDataMessage
     *            the message
     */
    void setEmptyDataMessage(String emptyDataMessage);
}
