/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Horizontal alignment.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITextAlignmentCapability {

	/**
	 * Returns horizontal alignment.
	 */
	String getTextAlignment();

	/**
	 * Specifies horizontal alignment.
	 */
	void setTextAlignment(String textAlignment);
}
