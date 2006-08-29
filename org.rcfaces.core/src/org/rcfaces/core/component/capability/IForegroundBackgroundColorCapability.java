/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Foreground et background colors.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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