/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectableCapability {

	/**
	 * Returns a boolean value indicating wether the component can receive a user's selection
	 * @return selectable boolean property
	 */
	boolean isSelectable();

	/**
	 * Sets a boolean value indicating wether the component can receive a user's selection
	 * @param selectable selectable boolean property
	 */
	void setSelectable(boolean selectable);
}
