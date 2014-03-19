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
public interface IHelpCapability extends IToolTipTextCapability {

    /**
     * Returns a string value specifying the message to be shown by a <a
     * href="/comps/helpMessageZoneComponent.html">helpMessageZone Component</a>.
     * 
     * @return help message
     */
    String getHelpMessage();

    /**
     * Sets a string value specifying the message to be shown by a <a
     * href="/comps/helpMessageZoneComponent.html">helpMessageZone Component</a>.
     * 
     * @param message
     *            help message
     */
    void setHelpMessage(String message);

    /**
     * Returns an URL that points to a help page for the component. it is used
     * for example by a <a href="/comps/helpButtonComponent.html">helpButton
     * Component</a>.
     * 
     * @return help url
     */
    String getHelpURL();

    /**
     * Sets an URL that points to a help page for the component. it is used for
     * example by a <a href="/comps/helpButtonComponent.html">helpButton
     * Component</a>.
     * 
     * @param url
     *            help url
     */
    void setHelpURL(String url);
}
