/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.IImageContentModel;
import org.rcfaces.core.image.ImageContentModel;
import org.rcfaces.core.internal.content.ContentAdapterFactory;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;
import org.rcfaces.core.internal.contentStorage.AdaptationParameters;
import org.rcfaces.core.internal.contentStorage.IResolvedContent;
import org.rcfaces.core.internal.images.operation.GIFConversionImageOperation;
import org.rcfaces.core.internal.images.operation.ICOConversionImageOperation;
import org.rcfaces.core.internal.images.operation.JPEGConversionImageOperation;
import org.rcfaces.core.internal.images.operation.PNGConversionImageOperation;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageAdapterFactory extends ContentAdapterFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ImageAdapterFactory.class);

    private static final String RGB_DEFAULT_MIME_TYPE = JPEGConversionImageOperation.MIME_TYPES[0];

    private static final String INDEX_DEFAULT_MIME_TYPE = GIFConversionImageOperation.MIME_TYPE;

    static {
        GIFConversionImageOperation.fillSuffixByMimeType(suffixByMimeType);

        JPEGConversionImageOperation.fillSuffixByMimeType(suffixByMimeType);

        PNGConversionImageOperation.fillSuffixByMimeType(suffixByMimeType);

        ICOConversionImageOperation.fillSuffixByMimeType(suffixByMimeType);
    }

    public Object getAdapter(Object adaptableObject, Class adapterType,
            Object parameter) {
        if (adaptableObject instanceof RenderedImage) {
            return adaptBufferedImage(adaptableObject, parameter);
        }

        if (adaptableObject instanceof RenderedImage[]) {
            RenderedImage renderedImages[] = (RenderedImage[]) adaptableObject;

            if (renderedImages.length < 1) {
                return null;
            }

            return adaptBufferedImage(renderedImages, parameter);
        }

        return super.getAdapter(adaptableObject, adapterType, parameter);
    }

    private IResolvedContent adaptBufferedImage(Object image,
            Object adapterParameters) {

        IGenerationResourceInformation generationResourceInformation = null;
        if (adapterParameters instanceof AdaptationParameters) {
            generationResourceInformation = ((AdaptationParameters) adapterParameters)
                    .getGenerationResourceInformation();
        }

        Map adapterParametersMap = null;
        if (adapterParameters instanceof Map) {
            adapterParametersMap = (Map) adapterParameters;

        } else if (adapterParametersMap instanceof IAdaptable) {
            adapterParametersMap = (Map) ((IAdaptable) adapterParameters)
                    .getAdapter(Map.class, null);
        }

        String defaultMimeType = RGB_DEFAULT_MIME_TYPE;
        if (image instanceof RenderedImage) {
            defaultMimeType = getDefaultContentType((RenderedImage) image);

        } else if (image instanceof RenderedImage[]) {
            defaultMimeType = getDefaultContentType(((RenderedImage[]) image)[0]);
        }

        String mimeType = null;

        if (mimeType == null && generationResourceInformation != null) {
            mimeType = (String) generationResourceInformation
                    .getAttribute(IImageContentModel.ENCODER_MIME_TYPE_PROPERTY);
        }

        if (mimeType == null && adapterParametersMap != null) {
            mimeType = (String) adapterParametersMap
                    .get(IImageContentModel.ENCODER_MIME_TYPE_PROPERTY);
        }

        if (mimeType == null) {
            String suffix = null;

            if (suffix == null && generationResourceInformation != null) {
                suffix = (String) generationResourceInformation
                        .getAttribute(IImageContentModel.ENCODER_SUFFIX_PROPERTY);
            }

            if (suffix == null && adapterParametersMap != null) {
                suffix = (String) adapterParametersMap
                        .get(IImageContentModel.ENCODER_SUFFIX_PROPERTY);
            }

            if (suffix != null) {
                mimeType = fileNameMap.getContentTypeFor("x." + suffix);
            }
        }

        if (mimeType == null) {
            mimeType = defaultMimeType;
        }

        ImageWriteParam imageWriteParam = null;
        if (imageWriteParam == null && generationResourceInformation != null) {
            imageWriteParam = (ImageWriteParam) generationResourceInformation
                    .getAttribute(ImageContentModel.IMAGE_WRITE_PARAM_PROPERTY);
        }

        if (imageWriteParam == null && adapterParametersMap != null) {
            imageWriteParam = (ImageWriteParam) adapterParametersMap
                    .get(ImageContentModel.IMAGE_WRITE_PARAM_PROPERTY);
        }

        String responseSuffix = null;
        if (responseSuffix == null && generationResourceInformation != null) {
            responseSuffix = (String) generationResourceInformation
                    .getAttribute(IContentModel.RESPONSE_URL_SUFFIX_PROPERTY);
        }

        if (responseSuffix == null && adapterParametersMap != null) {
            responseSuffix = (String) adapterParametersMap
                    .get(IContentModel.RESPONSE_URL_SUFFIX_PROPERTY);
        }

        boolean useEtagAsResourceKey = false;
        if (generationResourceInformation != null) {
            Object b = generationResourceInformation
                    .getAttribute(IContentModel.AUTO_GENERATE_RESOURCE_KEY_PROPERTY);
            if (b != null) {
                useEtagAsResourceKey = Boolean.TRUE.equals(b);
            }
        }

        IOException ex = null;

        Iterator it = ImageIO.getImageWritersByMIMEType(mimeType);
        if (it.hasNext()) {
            ImageWriter imageWriter = (ImageWriter) it.next();

            try {
                return writeBufferedImage(imageWriter, image, imageWriteParam,
                        mimeType, responseSuffix, useEtagAsResourceKey);

            } catch (IOException e) {
                ex = e;

            } finally {
                imageWriter.dispose();
            }
        }

        if (mimeType.equals(defaultMimeType) == false) {
            it = ImageIO.getImageWritersByMIMEType(defaultMimeType);
            if (it.hasNext()) {
                ImageWriter imageWriter = (ImageWriter) it.next();

                try {
                    return writeBufferedImage(imageWriter, image,
                            imageWriteParam, mimeType, responseSuffix,
                            useEtagAsResourceKey);

                } catch (IOException e) {
                    if (ex == null) {
                        ex = e;
                    }

                } finally {
                    imageWriter.dispose();
                }
            }
        }

        throw new FacesException("Unsupported image mime type '" + mimeType
                + "'.", ex);
    }

    private String getDefaultContentType(RenderedImage image) {
        ColorModel colorModel = image.getColorModel();
        if (colorModel instanceof IndexColorModel) {
            return INDEX_DEFAULT_MIME_TYPE;
        }

        return RGB_DEFAULT_MIME_TYPE;
    }

    private IResolvedContent writeBufferedImage(ImageWriter imageWriter,
            Object image, ImageWriteParam imageWriteParam, String contentType,
            String suffix, boolean useEtagAsResourceKey) throws IOException {

        if (suffix == null) {
            suffix = getSuffixByMimeType(contentType);
        }

        File file = createTempFile(contentType, (suffix != null) ? suffix
                : "unknown");

        FileOutputStream fout = new FileOutputStream(file);
        try {
            ImageOutputStream imageOutputStream = ImageIO
                    .createImageOutputStream(fout);

            imageWriter.setOutput(imageOutputStream);

            if (image instanceof RenderedImage) {
                imageWriter.write(null, new IIOImage((RenderedImage) image,
                        null, null), imageWriteParam);

            } else if (image instanceof RenderedImage[]) {
                RenderedImage renderedImages[] = (RenderedImage[]) image;

                if (imageWriter.canWriteSequence() == false) {
                    imageWriter.write(null, new IIOImage(renderedImages[0],
                            null, null), imageWriteParam);
                } else {
                    for (int i = 0; i < renderedImages.length; i++) {
                        imageWriter.write(null, new IIOImage(renderedImages[i],
                                null, null), imageWriteParam);
                    }
                }
            }

            imageWriter.dispose();

            imageOutputStream.close();

        } finally {
            fout.close();
        }

        return new FileResolvedContent(contentType, suffix, file,
                useEtagAsResourceKey);
    }
}
