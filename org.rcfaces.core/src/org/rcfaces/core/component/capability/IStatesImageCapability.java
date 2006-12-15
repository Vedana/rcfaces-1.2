/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStatesImageCapability extends IImageCapability {
    
	/**
	 * Returns an url string pointing to the image used chen the pointer hover the component.
	 * @return url
	 */
	String getHoverImageURL();

	/**
	 * Sets an url string pointing to the image used chen the pointer hover the component.
	 * @param url url 
	 */
    void setHoverImageURL(String url);

    /**
     * Returns an url string pointing to the image used when the component is selected.
     * @return url
     */
    String getSelectedImageURL();

    /**
     * Sets an url string pointing to the image used when the component is selected.
     * @param url url
     */
    void setSelectedImageURL(String url);

    /**
     * Returns an url string pointing to the image used for the disabled state.
     * @return url
     */
    String getDisabledImageURL();

    /**
     * Sets an url string pointing to the image used for the disabled state.
     * @param url url
     */
    void setDisabledImageURL(String url);
}