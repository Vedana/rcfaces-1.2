/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A boolean value indicating the state of the component.
 * 
 * @version $Revision$ $Date$
 */
public interface ICheckedCapability {

	/**
	 * Returns a boolean value indicating the state of the component.
	 * 
	 * @return boolean
	 */
	boolean isChecked();

	/**
	 * Sets a boolean value indicating the state of the component.
	 * 
	 * @param checked boolean
	 */
	void setChecked(boolean checked);

}
