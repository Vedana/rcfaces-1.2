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

    private String forceSuffix;

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

    public final void setForceSuffix(String forceSuffix) {
        this.forceSuffix = forceSuffix;
    }

    public void prepare(IGenerationResourceInformation generationInformation,
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

        if (forceSuffix != null) {
            generatedInformation.setResponseSuffix(forceSuffix);
        }
    }

}
