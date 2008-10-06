/*
 * $Id$
 */
package org.rcfaces.core.image;

import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IGenerationImageInformation extends
        IGenerationResourceInformation {

    void setImageHeight(int imageHeight);

    int getImageHeight();

    void setImageWidth(int imageWidth);

    int getImageWidth();
}
