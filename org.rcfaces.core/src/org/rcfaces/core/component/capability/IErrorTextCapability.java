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
public interface IErrorTextCapability {

    /**
     * Sets a string value specifying the error text to be shown.
     * 
     * @param text
     *            text
     */
    void setErrorText(String text);

    /**
     * Returns a string value specifying the error text shown.
     * 
     * @return text
     */
    String getErrorText();
}
