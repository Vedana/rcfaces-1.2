/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Fred (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IShowLoadingMessageCapability {

    /**
     * Returns a string value containing the message to show when the user attempts an action while loading
     * 
     * @return showLoadingMessage string property
     */
    String getShowLoadingMessage();

    /**
     * Sets a string value containing the message to show when the user attempts an action while loading
     * if not set, the default value is shown
     * if set to empty string, no message is shown
     * 
     * @param showLoadingMessage
     *            showLoadingMessage string property
     */
    void setShowLoadingMessage(String showLoadingMessage);
}
