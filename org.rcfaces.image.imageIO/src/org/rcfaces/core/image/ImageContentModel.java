/*
 * $Id$
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;

import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;
import org.rcfaces.core.model.BasicContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageContentModel extends BasicContentModel implements
        IImageContentModel {
    private static final String REVISION = "$Revision$";

    public static final String IMAGE_WRITE_PARAM_PROPERTY = "javax.imageio.ImageWriteParam";

    public ImageContentModel() {
    }

    public void setCompressionQuality(
            IGenerationResourceInformation imageInformation, float quality) {

        generationInformation.setAttribute(
                IImageContentModel.COMPRESSION_QUALITY, new Float(quality));
    }

    public void setCompressionMode(
            IGenerationResourceInformation imageInformation, int mode) {

        generationInformation.setAttribute(IImageContentModel.COMPRESSION_MODE,
                new Integer(mode));
    }

    public void setCompressionType(
            IGenerationResourceInformation imageInformation,
            String compressionType) {

        generationInformation.setAttribute(IImageContentModel.COMPRESSION_TYPE,
                compressionType);
    }

    public void setProgressiveMode(
            IGenerationResourceInformation imageInformation,
            boolean progressiveMode) {

        generationInformation.setAttribute(
                IImageContentModel.COMPRESSION_PROGRESSIVE_MODE, Boolean
                        .valueOf(progressiveMode));
    }

    public Object getWrappedData() {
        Object prev = super.getWrappedData();
        if (prev != null) {
            return prev;
        }

        prev = getBufferedImage();
        setWrappedData(prev);

        return prev;
    }

    protected BufferedImage getBufferedImage() {
        return null;
    }

}
