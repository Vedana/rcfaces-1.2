/*
 * $Id$
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractConversionImageOperation extends
        AbstractImageOperation implements IImageIOOperation {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractConversionImageOperation.class);

    protected AbstractConversionImageOperation(String responseMimeType,
            String responseSuffix) {

        setResponseMimeType(responseMimeType);
        setEncoderMimeType(responseMimeType);
        setResponseSuffix(responseSuffix);
    }

    public BufferedImage filter(Map requestParameter, BufferedImage source,
            BufferedImage destination) {
        return source;
    }

    public void setCompressionQuality(
            IGenerationResourceInformation imageInformation, float quality) {

        imageInformation.setAttribute(
                IGenerationImageInformation.COMPRESSION_QUALITY, new Float(
                        quality));
    }

    public void setCompressionMode(
            IGenerationResourceInformation imageInformation, int mode) {

        imageInformation
                .setAttribute(IGenerationImageInformation.COMPRESSION_MODE,
                        new Integer(mode));
    }

    public void setCompressionType(
            IGenerationResourceInformation imageInformation,
            String compressionType) {

        imageInformation.setAttribute(
                IGenerationImageInformation.COMPRESSION_TYPE, compressionType);
    }

    public void setProgressiveMode(
            IGenerationResourceInformation imageInformation,
            boolean progressiveMode) {

        imageInformation.setAttribute(
                IGenerationImageInformation.COMPRESSION_PROGRESSIVE_MODE,
                Boolean.valueOf(progressiveMode));
    }

    public void prepare(IImageOperationContentModel imageOperationContentModel,
            IGenerationResourceInformation generationInformation,
            IGeneratedImageInformation generatedInformation) {
        super.prepare(imageOperationContentModel, generationInformation,
                generatedInformation);

        Map filterParameters = imageOperationContentModel.getFilterParameters();
        String qualityValue = (String) filterParameters.get("quality");

        if (qualityValue != null) {
            try {
                float quality = Float.parseFloat(qualityValue);

                setCompressionQuality(generationInformation, quality);

            } catch (NumberFormatException ex) {
                LOG.error(
                        "Can not change qualityValue '" + qualityValue + "'.",
                        ex);
            }
        }
    }

}
