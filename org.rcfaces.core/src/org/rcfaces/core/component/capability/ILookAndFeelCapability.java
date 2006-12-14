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
public interface ILookAndFeelCapability {

	/**
	 * Sets a string value specifying the choosen look of the component.
	 * @param ID lookId
	 */
	void setLookId(String ID);

	/**
	 * Returns a string value specifying the choosen look of the component.
	 * @return lookId
	 */
	String getLookId();
}
