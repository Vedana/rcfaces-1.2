/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.rcfaces.core.internal.content.IBufferOperation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageOperation extends IBufferOperation {

    void setForceSuffix(String suffix);

    void setResponseMimeType(String responseMimeType);

    void setSourceMimeType(String sourceMimeType);

    void setEncoderMimeType(String encoderMimeType);

    void prepare(IGenerationResourceInformation generationInformation,
            IGeneratedImageInformation generatedInformation);

    BufferedImage filter(Map requestParameter, BufferedImage source,
            BufferedImage destination);
}
