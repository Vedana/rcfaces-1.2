/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Enable/Disabled state.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDisabledCapability {

    /**
     * Returns <code>false</code> if the receiver is enabled and all ancestors
     * up to and including the receiver's nearest ancestor shell are enabled.
     * <br>
     * Otherwise, false is returned. A disabled control is typically not
     * selectable from the user interface and draws with an inactive or "grayed"
     * look.
     * 
     * @return the receiver's enabled state
     */
    boolean isDisabled();

    /**
     * Disabled the receiver if the argument is true, and enables it otherwise.
     * A disabled control is typically not selectable from the user interface
     * and draws with an inactive or "grayed" look.
     * 
     * @param disabled
     *            the new enabled state
     */
    void setDisabled(boolean disabled);
}
