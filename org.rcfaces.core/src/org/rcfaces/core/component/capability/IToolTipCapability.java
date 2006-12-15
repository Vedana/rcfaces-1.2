/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * ToolTip capability.
 * 
 * @version $Revision$ $Date$
 * @author Olivier Oeuillot (latest modification by $Author$)
 */
public interface IToolTipCapability {

	/**
	 * Returns the receiver's tool tip text, or <code>null</code> if it has
	 * not been set.
	 * 
	 * @return the receiver's tool tip text
	 */
	String getToolTipText();

	/**
	 * Sets the receiver's tool tip text to the argument, which may be
	 * <code>null</code> indicating that no tool tip text should be shown.
	 * 
	 * @param text the new tool tip text (or <code>null</code>)
	 */
	void setToolTipText(String text);
}
