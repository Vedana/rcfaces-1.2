/*
 * $Id$
 *
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * 
 * @author Joel Merlin, Olivier Oeuillot
 * @version $Revision$ $Date$
 */
public interface IOrderCapability {

	/**
	 * Returns a boolean value that indicates the sorting direction for the component.
	 * @return true if ascending, false if descending
	 */
	boolean isAscending();

	/**
	 * Sets a boolean value that indicates the sorting direction for the component.
	 * @param ascending true if ascending, false if descending
	 */
	void setAscending(boolean ascending);

}
