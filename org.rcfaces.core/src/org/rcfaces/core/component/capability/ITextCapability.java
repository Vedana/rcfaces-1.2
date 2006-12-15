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
public interface ITextCapability {
    
	/**
	 * Sets a string value specifying the text to be shown.
	 * @param text text
	 */
    void setText(String text);

    /**
     * Returns a string value specifying the text shown.
     * @return text
     */
    String getText();
}
