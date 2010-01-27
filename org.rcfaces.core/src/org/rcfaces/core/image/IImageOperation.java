/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

import org.rcfaces.core.internal.content.IBufferOperation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageOperation extends IBufferOperation {

    void setResponseSuffix(String suffix);

    void setResponseMimeType(String responseMimeType);

    void setSourceMimeType(String sourceMimeType);

    void setEncoderMimeType(String encoderMimeType);

    void prepare(IImageOperationContentModel imageOperationContentModel,
            IGenerationResourceInformation generationInformation,
            IGeneratedImageInformation generatedInformation);
}
