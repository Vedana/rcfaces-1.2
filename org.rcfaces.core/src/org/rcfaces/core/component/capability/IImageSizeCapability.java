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
    int getImageWidth();

    void setImageWidth(int width);

    int getImageHeight();

    void setImageHeight(int height);
}
