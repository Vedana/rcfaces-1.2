/*
 * $Id$
 */
package org.rcfaces.core.image;

import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IGeneratedImageInformation extends
        IGeneratedResourceInformation {

    void setImageHeight(int imageHeight);

    void setImageWidth(int imageWidth);

    String getEncoderMimeType();

    void setEncoderMimeType(String mimeType);
}
