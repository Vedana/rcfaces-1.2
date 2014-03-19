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
public interface IAlertLoadingMessageCapability {

    /**
     * Returns a string value containing the message to show when the user attempts an action while loading
     * 
     * @return alertLoadingMessage string property
     */
    String getAlertLoadingMessage();

    /**
     * Sets a string value containing the message to show when the user attempts an action while loading
     * if not set, the default value is shown
     * if set to empty string, no message is shown
     * 
     * @param alertLoadingMessage
     *            alertLoadingMessage string property
     */
    void setAlertLoadingMessage(String alertLoadingMessage);
}
