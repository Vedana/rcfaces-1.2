/*
 * $Id$
 */
package org.rcfaces.core.component.capability;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMaxTextLengthCapability {

    /**
     * Returns an int value specifying the maximum number of characters that the
     * user can enter in the component.
     * 
     * @return maximum number of characters
     */
    int getMaxTextLength();

    /**
     * Sets an int value specifying the maximum number of characters that the
     * user can enter in the component.
     * 
     * @param maxTextLength
     *            maximum number of characters
     */
    void setMaxTextLength(int maxTextLength);
}
