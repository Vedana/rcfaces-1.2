/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFontCapability {

	/**
	 * 
	 */
	Boolean getFontBold();

	/**
	 * 
	 */
	Boolean getFontItalic();

	/**
	 * 
	 */
	Boolean getFontUnderline();

	/**
	 * 
	 */
	String getFontName();

	/**
	 * 
	 */
	String getFontSize();

	/**
	 * 
	 */
	void setFontBold(Boolean bold);

	/**
	 * 
	 */
	void setFontItalic(Boolean italic);

	/**
	 * 
	 */
	void setFontUnderline(Boolean underline);

	/**
	 *
	 */
	void setFontName(String name);

	/**
	 * 
	 */
	void setFontSize(String size);
}
