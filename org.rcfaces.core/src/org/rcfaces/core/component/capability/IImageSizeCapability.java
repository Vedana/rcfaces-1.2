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
public interface IImageSizeCapability {
    
	/**
	 * Returns an int value specifying the width (in pixels) to use for the image shown.
	 * @return image width in pixels
	 */
	int getImageWidth();

	/**
	 * Sets an int value specifying the width (in pixels) to use for the image shown.
	 * @param width image width in pixels
	 */
    void setImageWidth(int width);

    /**
     * Returns an int value specifying the height (in pixels) to use for the image shown.
     * @return image height in pixels
     */
    int getImageHeight();

    /**
     * Sets an int value specifying the height (in pixels) to use for the image shown.
     * @param height image height in pixels
     */
    void setImageHeight(int height);
}
