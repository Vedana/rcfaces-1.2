/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import org.rcfaces.core.component.familly.IContentAccessors;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageCapability {

    /**
     * Returns the URL of the image.
     */
    String getImageURL();

    /**
     * Specify the URL of the image.
     */
    void setImageURL(String url);

    /**
     * Returns ImageAccessors associated to the url. (or java.awt.Image binding)
     * 
     * @return IImageAccessors object.
     */
    IContentAccessors getImageAccessors();
}