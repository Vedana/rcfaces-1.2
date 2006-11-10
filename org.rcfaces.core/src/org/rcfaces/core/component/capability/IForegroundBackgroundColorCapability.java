/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Foreground et background colors.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IForegroundBackgroundColorCapability {

	/**
	 * Returns the receiver's background color.
	 * 
	 * @return the background color
	 */
	String getBackgroundColor();

	/**
	 * Sets the receiver's background color to the color specified by the argument, 
	 * or to the default system color for the control if the argument is null.
	 */
	void setBackgroundColor(String color);

	/**
	 * Returns the foreground color that the receiver will use to draw.
	 * 
	 * @return the receiver's foreground color
	 */
	String getForegroundColor();

	/**
	 * Sets the receiver's foreground color to the color specified by the argument, 
	 * or to the default system color for the control if the argument is null.
	 */
	void setForegroundColor(String color);
}
