/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A string containing the message shown when there is no result.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IEmptyMessageCapability {
    
	/**
	 * Returns a string containing the message shown when there is no result.
	 * 
	 * @return message
	 */
	String getEmptyMessage();

	/**
	 * Sets the string conatining the message shown when there is no result. 
	 * @param emptyMessage the message
	 */
	void setEmptyMessage(String emptyMessage);
}
