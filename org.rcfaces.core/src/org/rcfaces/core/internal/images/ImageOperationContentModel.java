package org.rcfaces.core.internal.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

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
import org.rcfaces.core.internal.contentStorage.IResolvedContent;
import org.rcfaces.core.internal.images.IImageLoaderFactory.IImageLoader;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.model.BasicContentModel;
import org.rcfaces.core.model.IAdaptable;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class ImageOperationContentModel extends BasicContentModel implements
        Serializable, IAdaptable, IResolvedContent {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8586152237093654819L;

    private static final Log LOG = LogFactory
            .getLog(ImageOperationContentModel.class);

    private static final String IMAGE_CONTENT_PROVIDER_ID = "org.rcfaces.core.IMAGE_CONTENT_PROVIDER";

    /**
     * 
     */
    private static final IBufferedImage INVALID_BUFFERED_IMAGE = new IBufferedImage() {
        private static final String REVISION = "$Revision$";

        public int getSize() {
            return 0;
        }

        public String getName() {
            return "*** Invalid image ***";
        }

        public InputStream getContent() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public long getModificationDate() {
            return 0;
        }

        public String getHash() {
            return null;
        }

        public String getETag() {
            return null;
        }

        public boolean isInitialized() {
            return true;
        }

        public void setErrored() {
        }

        public boolean isErrored() {
            return true;
        }

        public void initialize(IImageLoader imageDownloader,
                String contentType, RenderedImage renderedImage,
                ImageWriter imageWriter, int imageType) {
        }

        public String getRedirection() {
            return null;
        }

        public void initializeRedirection(String url) {
        }

    };

    private final String resourceURL;

    private final String operationId;

    private String filterParametersToParse;

    private String resourceKey;

    private transient Map filterParameters;

    private transient IBufferedImage bufferedImage;

    private transient IImageOperation imageOperation;

    private boolean versioned;

    public ImageOperationContentModel(String resourceURL, String contentType,
            String urlSuffix, String versionId, String operationId,
            String filterParametersToParse, Map attributes,
            IImageOperation imageOperation) {
        this.resourceURL = resourceURL;
        this.operationId = operationId;
        this.filterParametersToParse = filterParametersToParse;
        this.imageOperation = imageOperation;

        StringAppender sa = new StringAppender(operationId, 128);

        if (filterParametersToParse != null) {
            sa.append(filterParametersToParse);
        }

        sa.append("::");

        sa.append(resourceURL);

        if (versionId != null) {
            sa.append(":$:");
            sa.append(versionId);
        }

        this.resourceKey = sa.toString();

        setProcessDataAtRequest(true);

        if (contentType != null) {
            setContentType(contentType);

            if (urlSuffix == null) {
                urlSuffix = ImageAdapterFactory
                        .getSuffixByContentType(contentType);
            }
        }

        if (urlSuffix != null) {
            setURLSuffix(urlSuffix);
        }

        if (attributes != null && attributes.isEmpty() == false) {
            putAllAttributes(attributes);
        }

        setWrappedData(this);
    }

    public final synchronized Map getFilterParameters() {
        if (filterParameters != null) {
            return filterParameters;
        }

        if (filterParametersToParse == null
                || filterParametersToParse.length() < 1) {
            filterParameters = Collections.EMPTY_MAP;
            return filterParameters;
        }

        StringTokenizer st = new StringTokenizer(filterParametersToParse, ",");

        filterParameters = new HashMap(st.countTokens());
        int idx = 0;
        for (; st.hasMoreTokens();) {
            String token = st.nextToken().trim();

            String pName;
            String pValue;

            int idxEq = token.indexOf('=');
            if (idxEq >= 0) {
                pName = token.substring(0, idxEq).trim();
                pValue = token.substring(idxEq + 1).trim();

            } else {
                pName = "#" + idx;
                pValue = token.trim();

                idx++;
            }

            filterParameters.put(pName, pValue);
        }

        return filterParameters;
    }

    public Object getAdapter(Class adapter, Map parameters) {
        if (IResolvedContent.class.equals(adapter)) {
            return this;
        }

        return null;
    }

    public boolean isProcessAtRequest() {
        return false;
    }

    public boolean isErrored() {
        return getBufferedImage().isErrored();
    }

    private synchronized IBufferedImage getBufferedImage() {
        if (bufferedImage != null) {
            return bufferedImage;
        }

        bufferedImage = createBufferedImage();

        return bufferedImage;
    }

    public InputStream getInputStream() throws IOException {
        return getBufferedImage().getContent();
    }

    public long getModificationDate() {
        return getBufferedImage().getModificationDate();
    }

    public int getLength() {
        return getBufferedImage().getSize();
    }

    public synchronized IImageOperation getImageOperation(
            FacesContext facesContext) {
        if (imageOperation != null) {
            return imageOperation;
        }

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        ImageContentAccessorHandler imageOperationRepository = (ImageContentAccessorHandler) rcfacesContext
                .getProvidersRegistry().getProvider(IMAGE_CONTENT_PROVIDER_ID);

        imageOperation = imageOperationRepository
                .getImageOperation(operationId);

        return imageOperation;
    }

    private IBufferedImage createBufferedImage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        IImageOperation imageOperation = getImageOperation(facesContext);
        if (imageOperation == null) {
            LOG.error("Can not get image operation associated to id '"
                    + operationId + "'.");
            return INVALID_BUFFERED_IMAGE;
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext
                .getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext
                .getResponse();
        ServletContext context = (ServletContext) externalContext.getContext();

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        IImageLoader imageDownloader = getImageDownloader(rcfacesContext,
                resourceURL, context, request, response);

        String downloadedContentType = imageDownloader.getContentType();
        if (downloadedContentType == null
                || downloadedContentType.equals(getContentType()) == false) {
            LOG.error("Different content types request='" + getContentType()
                    + "' loaded='" + downloadedContentType + "' for path '"
                    + resourceURL + "'.");

            return INVALID_BUFFERED_IMAGE;
        }

        InputStream inputStream = imageDownloader.openStream();

        if (inputStream == null) {
            LOG.error("Can not get image specified by path '" + resourceURL
                    + "'.");

            return INVALID_BUFFERED_IMAGE;
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

            return INVALID_BUFFERED_IMAGE;
        }

        ImageWriter imageWriter = (ImageWriter) it.next();

        BufferedImage image;
        try {
            it = ImageIO.getImageReadersByMIMEType(getContentType());

            if (it.hasNext() == false) {
                throw new IOException("Can not get codec to read image '"
                        + resourceURL + "'.");
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
            LOG.error("Can not load image '" + resourceURL + "'.", e);

            return INVALID_BUFFERED_IMAGE;

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

        IBufferedImage bufferedImage = createNewBufferedImage(resourceURL);

        try {
            RenderedImage renderedImage = filter(image,
                    new IImageOperation[] { imageOperation },
                    new Map[] { getFilterParameters() });

            try {
                bufferedImage.initialize(imageDownloader, externalContentType,
                        renderedImage, imageWriter, sourceImageType);

            } catch (IOException e) {
                LOG.error(
                        "Can not create filtred image '" + resourceURL + "'.",
                        e);

                return INVALID_BUFFERED_IMAGE;
            }

        } finally {
            imageWriter.dispose();
        }

        return bufferedImage;
    }

    protected IBufferedImage createNewBufferedImage(String imageName) {
        return new FileRenderedImage(imageName);
    }

    private IImageLoader getImageDownloader(RcfacesContext context, String url,
            ServletContext servletContext, HttpServletRequest request,
            HttpServletResponse response) {

        IImageLoaderFactory imageLoaderFactory;
        if (context.isDesignerMode()) {
            imageLoaderFactory = Constants.getDesignerImageLoaderFactory();

        } else {
            imageLoaderFactory = Constants.getImageLoaderFactory();
        }

        return imageLoaderFactory.loadImage(servletContext, request, response,
                url);
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

    public String getETag() {
        return getBufferedImage().getETag();
    }

    public String getHash() {
        return getBufferedImage().getHash();
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public boolean isVersioned() {
        return versioned;
    }

    public void setVersioned(boolean versioned) {
        this.versioned = versioned;
    }

}