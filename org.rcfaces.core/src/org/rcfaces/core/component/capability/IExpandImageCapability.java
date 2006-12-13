/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * An url string pointing to an image used for the expanded state.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IExpandImageCapability extends IStatesImageCapability {

    /**
     * Returns the url string pointing to an image used for the expanded state.
     * 
     * @return url
     */
	String getExpandedImageURL();

	/**
	 * Sets the url string pointing to an image used for the expanded state.
	 * 
	 * @param url url for image
	 */
    void setExpandedImageURL(String url);

}
