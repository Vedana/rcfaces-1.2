/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2004/08/06 14:03:56  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
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
	 * @param text
	 *            the new tool tip text (or <code>null</code>)
	 */
	void setToolTipText(String text);
}