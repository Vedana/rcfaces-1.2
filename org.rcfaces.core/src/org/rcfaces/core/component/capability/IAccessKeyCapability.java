/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * A string that indicates the alphanumeric key used to execute an action from the keyboard (in conjunction with a alteration key ex : Alt).
 * <p>
 * Alt + &lt;Any alphanumeric key&gt;
 * <BR>The letter|number will be highlighted when the alteration key is pressed.
 * </p>
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAccessKeyCapability {
	/**
	 * Returns a string that indicates the alphanumeric key used to execute an action from the keyboard (in conjunction with a alteration key ex : Alt).
	 * 
	 * @return &lt;Any alphanumeric key&gt;
	 */
	String getAccessKey();

	/**
	 * Sets a string that indicates the alphanumeric key used to execute an action from the keyboard (in conjunction with a alteration key ex : Alt).
	 * 
	 * @param key &lt;Any alphanumeric key&gt;
	 */
	void setAccessKey(String key);
}