/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * A boolean value that indicates if the component should show a border or not.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBorderCapability {
    
	/**
	 * Returns a boolean value that indicates if the component should show a border or not.
	 * 
	 * @return border
	 */
	boolean isBorder();

    /**
     * Sets a boolean value that indicates if the component should show a border or not.
     * 
     * @param border boolean
     */
	void setBorder(boolean border);
}
