/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IUnlockedClientAttributesCapability {
	
	/**
	 * Returns a string value holding a comma separated list of the client modifiable properties.
	 * @return list of modifiable attributes
	 */
	String getUnlockedClientAttributeNames();

	/**
	 * Sets a string value holding a comma separated list of the client modifiable properties.
	 * @param lock list of modifiable attributes
	 */
	void setUnlockedClientAttributeNames(String lock);

}
