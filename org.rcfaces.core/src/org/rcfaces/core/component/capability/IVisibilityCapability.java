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
public interface IVisibilityCapability {

    /**
     * 
     */
    int SERVER_HIDDEN_MODE = 1;

    /**
     * 
     */
    int PHANTOM_HIDDEN_MODE = 2;

    /**
     * 
     */
    int IGNORE_HIDDEN_MODE = 4;

    /**
     * 
     */
    int DEFAULT_HIDDEN_MODE = IGNORE_HIDDEN_MODE;

    /**
     * 
     */
    int CLIENT_HIDDEN_MODE = IGNORE_HIDDEN_MODE;

    /**
     * Marks the receiver as visible if the argument is
     * <code>{@link Boolean#TRUE TRUE}</code>, and marks it invisible if
     * argument is <code>{@link Boolean#FALSE FALSE}</code>. <br>
     * If one of the receiver's ancestors is not visible or some other condition
     * makes the receiver not visible, marking it visible may not actually cause
     * it to be displayed.
     * 
     * @param visible
     *            the new visibility state.
     */
    void setVisible(boolean visible);

     boolean isVisible();

     /**
      * Returns <code>{@link Boolean#TRUE TRUE}</code> if the receiver is
      * visible, <code>{@link Boolean#FALSE FALSE}</code> if the receiver is
      * specified "not visible", and <code>null</code> otherwise. <br>
      * If one of the receiver's ancestors is not visible or some other condition
      * makes the receiver not visible, this method may still indicate that it is
      * considered visible even though it may not actually be showing.
      * 
      * @return the receiver's visibility state
      */
    Boolean getVisibleState();
    
    /*
     * Returns <code>true</code> if the receiver is visible and all ancestors
     * up to and including the receiver's nearest ancestor view are visible.
     * Otherwise, <code>false</code> is returned.
     * 
     * @return the receiver's visibility state
     * 
     * @see #getVisible()
     * 
     * boolean isVisible();
     */

    /**
     * Set the visibility mode in client side.
     */
    void setHiddenMode(int hiddenMode);

    /**
     * Returns the visibility mode in client side.
     */
    int getHiddenMode();
}
