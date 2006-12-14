/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRequiredCapability {
	
	/**
	 * Returns a boolean value indicating that the user is required to provide a submitted value for this input component.
	 * @return required boolean property
	 */
	boolean isRequired();
	
	/**
	 * Sets a boolean value indicating that the user is required to provide a submitted value for this input component.
	 * @param required required boolean property
	 */
	void setRequired(boolean required);
}
