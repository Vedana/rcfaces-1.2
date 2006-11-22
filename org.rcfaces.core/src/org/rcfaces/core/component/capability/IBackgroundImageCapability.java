/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 *
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBackgroundImageCapability {
    /**
     * 
     */
	String getBackgroundImageURL();

    /**
     * 
     */
	void setBackgroundImageURL(String backgroundImageURL);

	/**
	 * 
	 */
	String getBackgroundImageHorizontalPosition();

	/**
	 * 
	 */
	void setBackgroundImageHorizontalPosition(String position);

	/**
	 *
	 */
    String getBackgroundImageVerticalPosition();

	/**
	 *
	 */
	void setBackgroundImageVerticalPosition(String position);

	/**
	 *
	 */
	boolean isBackgroundImageHorizontalRepeat();

	/**
	 * 
	 */
	void setBackgroundImageHorizontalRepeat(boolean repeat);

	/**
	 * 
	 */
	boolean isBackgroundImageVerticalRepeat();

	/**
	 * 
	 */
	void setBackgroundImageVerticalRepeat(boolean repeat);
}