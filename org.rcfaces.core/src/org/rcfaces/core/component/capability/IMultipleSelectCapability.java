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
public interface IMultipleSelectCapability {

	/**
	 * Returns a boolean value indicating wether multiple selection is permitted.
	 * @return boolean
	 */
	boolean isMultipleSelect();

	/**
	 * Sets a boolean value indicating wether multiple selection is permitted.
	 * @param multipleSelect boolean
	 */
	void setMultipleSelect(boolean multipleSelect);
}
