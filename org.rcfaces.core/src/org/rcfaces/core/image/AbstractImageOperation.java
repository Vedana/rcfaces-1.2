/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

import org.rcfaces.core.internal.content.AbstractBufferOperation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractImageOperation extends AbstractBufferOperation
        implements IImageOperation {
    private static final String REVISION = "$Revision$";

    private String responseSuffix;

    private String responseMimeType;

    private String sourceMimeType;

    private String encoderMimeType;

    public final void setResponseMimeType(String responseMimeType) {
        this.responseMimeType = responseMimeType;
    }

    public final void setSourceMimeType(String sourceMimeType) {
        this.sourceMimeType = sourceMimeType;
    }

    public final void setEncoderMimeType(String encoderMimeType) {
        this.encoderMimeType = encoderMimeType;
    }

    public final void setResponseSuffix(String responseSuffix) {
        this.responseSuffix = responseSuffix;
    }

    public void prepare(IImageOperationContentModel imageOperationContentModel,
            IGenerationResourceInformation generationInformation,
            IGeneratedImageInformation generatedInformation) {
        if (sourceMimeType != null) {
            generatedInformation.setSourceMimeType(sourceMimeType);
        }

        if (responseMimeType != null) {
            generatedInformation.setResponseMimeType(responseMimeType);
        }

        if (encoderMimeType != null) {
            generatedInformation.setEncoderMimeType(encoderMimeType);
        }

        if (responseSuffix != null) {
            generatedInformation.setResponseSuffix(responseSuffix);
        }
    }
}
