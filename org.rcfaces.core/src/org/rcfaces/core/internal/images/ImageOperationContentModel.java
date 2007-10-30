package org.rcfaces.core.internal.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.image.IIndexedImageOperation;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.content.AbstractOperationContentModel;
import org.rcfaces.core.internal.content.IBufferOperation;
import org.rcfaces.core.internal.content.IFileBuffer;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory.IResourceLoader;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageOperationContentModel extends AbstractOperationContentModel {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 3641020501370064750L;

    private static final Log LOG = LogFactory
            .getLog(ImageOperationContentModel.class);

    public ImageOperationContentModel(String resourceURL, String contentType,
            String urlSuffix, String versionId, String operationId,
            String filterParametersToParse, Map attributes,
            IBufferOperation bufferOperation) {
        super(resourceURL, contentType, versionId, operationId,
                filterParametersToParse, attributes, bufferOperation);

        if (contentType != null) {
            if (urlSuffix == null) {
                urlSuffix = ImageAdapterFactory
                        .getSuffixByContentType(contentType);
            }
        }

        if (urlSuffix != null) {
            setURLSuffix(urlSuffix);
        }
    }

    protected IBufferOperation createBufferOperation(FacesContext facesContext) {

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        ImageContentAccessorHandler imageOperationRepository = (ImageContentAccessorHandler) rcfacesContext
                .getProvidersRegistry().getProvider(
                        ImageContentAccessorHandler.IMAGE_CONTENT_PROVIDER_ID);

        IImageOperation imageOperation = imageOperationRepository
                .getImageOperation(getOperationId());

        return imageOperation;
    }

    protected IFileBuffer createFileBuffer() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        IImageOperation imageOperation = (IImageOperation) getBufferOperation(facesContext);
        if (imageOperation == null) {
            LOG.error("Can not get image operation associated to id '"
                    + getOperationId() + "'.");
            return INVALID_BUFFERED_FILE;
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext
                .getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext
                .getResponse();
        ServletContext context = (ServletContext) externalContext.getContext();

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        IResourceLoader imageDownloader = getImageLoader(rcfacesContext,
                getResourceURL(), context, request, response);

        String downloadedContentType = imageDownloader.getContentType();
        if (downloadedContentType == null
                || downloadedContentType.equals(getContentType()) == false) {
            LOG.error("Different content types request='" + getContentType()
                    + "' loaded='" + downloadedContentType + "' for path '"
                    + getResourceURL() + "'.");

            return INVALID_BUFFERED_FILE;
        }

        InputStream inputStream = imageDownloader.openStream();

        if (inputStream == null) {
            LOG.error("Can not get image specified by path '"
                    + getResourceURL() + "'.");

            return INVALID_BUFFERED_FILE;
        }

        String internalContentType = getContentType();
        if (imageOperation.getInternalContentType() != null) {
            internalContentType = imageOperation.getInternalContentType();
        }

        String externalContentType = getContentType();
        if (imageOperation.getExternalContentType() != null) {
            externalContentType = imageOperation.getExternalContentType();
        }

        if (externalContentType == null) {
            externalContentType = internalContentType;
        }

        Iterator it = ImageIO.getImageWritersByMIMEType(internalContentType);
        if (it.hasNext() == false) {
            LOG.error("Can not write image format '" + internalContentType
                    + "'.");

            return INVALID_BUFFERED_FILE;
        }

        ImageWriter imageWriter = (ImageWriter) it.next();

        BufferedImage image;
        try {
            it = ImageIO.getImageReadersByMIMEType(getContentType());

            if (it.hasNext() == false) {
                throw new IOException("Can not get codec to read image '"
                        + getResourceURL() + "'.");
            }

            ImageReader imageReader = (ImageReader) it.next();

            try {
                ImageInputStream imageInputStream = ImageIO
                        .createImageInputStream(inputStream);

                try {
                    ImageReadParam param = imageReader.getDefaultReadParam();
                    imageReader.setInput(imageInputStream, true, true);
                    image = imageReader.read(0, param);

                } finally {
                    imageInputStream.close();
                }

            } finally {
                imageReader.dispose();
            }

        } catch (IOException e) {
            LOG.error("Can not load image '" + getResourceURL() + "'.", e);

            return INVALID_BUFFERED_FILE;

        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {
                LOG.error(e);
            }
        }

        int sourceImageType = image.getType();
        if (sourceImageType == BufferedImage.TYPE_BYTE_BINARY) {
            sourceImageType = BufferedImage.TYPE_BYTE_INDEXED;
        }

        if ("image/bmp".equals(internalContentType)) {
            // sourceImageType = BufferedImage.TYPE_4BYTE_ABGR;
        }

        IBufferedImage bufferedImage = createNewBufferedImage(getResourceURL());

        try {
            RenderedImage renderedImage = filter(image,
                    new IImageOperation[] { imageOperation },
                    new Map[] { getFilterParameters() });

            try {
                bufferedImage.initialize(imageDownloader, externalContentType,
                        renderedImage, imageWriter, sourceImageType,
                        imageDownloader.getLastModified());

            } catch (IOException e) {
                LOG.error("Can not create filtred image '" + getResourceURL()
                        + "'.", e);

                return INVALID_BUFFERED_FILE;
            }

        } finally {
            imageWriter.dispose();
        }

        return bufferedImage;
    }

    protected IBufferedImage createNewBufferedImage(String imageName) {
        return new FileRenderedImage(imageName);
    }

    private IResourceLoader getImageLoader(RcfacesContext context, String url,
            ServletContext servletContext, HttpServletRequest request,
            HttpServletResponse response) {

        IResourceLoaderFactory imageLoaderFactory;
        if (context.isDesignerMode()) {
            imageLoaderFactory = Constants.getDesignerImageLoaderFactory();

        } else {
            imageLoaderFactory = Constants.getImageLoaderFactory();
        }

        return imageLoaderFactory.loadResource(servletContext, request,
                response, url);
    }

    protected RenderedImage filter(BufferedImage image,
            IImageOperation imageOperations[], Map parameters[]) {

        BufferedImage workImage = null;

        IndexColorModel workingColorModel = null;

        if (LOG.isTraceEnabled()) {
            LOG.trace("Process " + imageOperations.length + " image operation"
                    + ((imageOperations.length > 1) ? "s" : "")
                    + ", source image type=" + image.getType() + ".");
        }

        for (int i = 0; i < imageOperations.length; i++) {
            IImageOperation imageOperation = imageOperations[i];

            int imageType = image.getType();

            if (LOG.isTraceEnabled()) {
                LOG.trace("Process image operation #" + i + " '"
                        + imageOperation.getName() + "', current image type="
                        + imageType);
            }

            if (imageType == BufferedImage.TYPE_BYTE_INDEXED
                    || imageType == BufferedImage.TYPE_BYTE_BINARY) {
                IndexColorModel indexColorModel = (IndexColorModel) image
                        .getColorModel();

                int support = IIndexedImageOperation.INDEX_COLOR_MODEL_NOT_SUPPORTED;
                if (imageOperation instanceof IIndexedImageOperation) {
                    support = ((IIndexedImageOperation) imageOperation)
                            .indexedColorModelSupport();
                }

                if (support == IIndexedImageOperation.INDEX_COLOR_MODEL_COLORS_MAP) {
                    if (workingColorModel == null) {
                        workingColorModel = indexColorModel;
                    }

                    workingColorModel = ((IIndexedImageOperation) imageOperation)
                            .filter(parameters[i], indexColorModel, image);

                    continue;

                } else if (support == IIndexedImageOperation.INDEX_COLOR_MODEL_NOT_SUPPORTED) {
                    if (LOG.isTraceEnabled()) {
                        LOG
                                .trace("Image operation '"
                                        + imageOperation.getName()
                                        + "' does not support current color model : convert image to ARGB.");
                    }

                    if (workingColorModel != null) {
                        if (LOG.isTraceEnabled()) {
                            LOG
                                    .trace("WorkingColorModel is defined, convert image to model.");
                        }

                        image = new BufferedImage(workingColorModel, image
                                .getRaster(), false, null);
                        workingColorModel = null;
                    }

                    BufferedImage rgbImage = new BufferedImage(
                            image.getWidth(), image.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = rgbImage.createGraphics();
                    try {
                        g.drawImage(image, 0, 0, null);

                    } finally {
                        g.dispose();
                    }

                    image = rgbImage;
                }
            }

            if (workingColorModel != null) {
                if (LOG.isTraceEnabled()) {
                    LOG
                            .trace("WorkingColorModel is defined, convert image to model.");
                }

                image = new BufferedImage(workingColorModel, image.getRaster(),
                        false, null);
                workingColorModel = null;
            }

            if (workImage == null) {
                workImage = new BufferedImage(image.getWidth(), image
                        .getHeight(), image.getType());
            }

            image = imageOperation.filter(parameters[i], image, workImage);
            workImage = null;
        }

        if (workingColorModel != null) {
            if (LOG.isTraceEnabled()) {
                LOG
                        .trace("WorkingColorModel is defined, convert image to model.");
            }

            image = new BufferedImage(workingColorModel, image.getRaster(),
                    false, null);
        }

        return image;
    }
}