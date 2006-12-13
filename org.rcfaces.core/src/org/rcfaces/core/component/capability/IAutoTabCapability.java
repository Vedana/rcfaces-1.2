/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * A boolean value indicating if the focus should move automatically to the next element when the entry is completed.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAutoTabCapability {
    
	/**
	 * Returns a boolean value indicating if the focus should move automatically to the next element when the entry is completed.
	 * 
	 * @return boolean
	 */
	boolean isAutoTab();

    /**
	 * Sets a boolean value indicating if the focus should move automatically to the next element when the entry is completed.
	 * 
	 * @param autoTab boolean
	 */
    void setAutoTab(boolean autoTab);
}
