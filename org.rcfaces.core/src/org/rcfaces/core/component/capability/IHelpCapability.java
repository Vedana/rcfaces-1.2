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
public interface IHelpCapability extends IToolTipCapability {

	/**
	 * 
	 */
	String getHelpMessage();

	/**
	 * 
	 */
	void setHelpMessage(String message);

	/**
	 * 
	 */
	String getHelpURL();

	/**
	 * 
	 */
	void setHelpURL(String url);
}
