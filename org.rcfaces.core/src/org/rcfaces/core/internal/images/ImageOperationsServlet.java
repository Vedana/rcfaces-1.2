/*
 * $Id$
 */
package org.rcfaces.core.internal.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.image.IIndexedImageOperation;
import org.rcfaces.core.internal.codec.URLFormCodec;
import org.rcfaces.core.internal.images.IImageLoaderFactory.IImageLoader;
import org.rcfaces.core.internal.util.ServletTools;
import org.rcfaces.core.internal.webapp.ExpirationDate;
import org.rcfaces.core.internal.webapp.ParametredHttpServlet;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ImageOperationsServlet extends ParametredHttpServlet {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ImageOperationsServlet.class);

    private static final int MAX_BUFFER_SIZE = 8192;

    private static final String URL_DECODER_CHARSET = "UTF-8";

    private static final String MAX_IMAGE_CACHE_SIZE_PARAMETER = Constants
            .getPackagePrefix()
            + ".images.MAX_IMAGES_CACHE_SIZE";

    private static final String NO_CACHE_PARAMETER = Constants
            .getPackagePrefix()
            + ".NO_CACHE";

    private static final String IMAGE_REPOSITORY_DEFAULT_URL = "/image-filters";

    private static final String IMAGE_REPOSITORY_PROPERTY = "org.rcfaces.core.internal.images.IMAGE_REPOSITORY_PROPERTY";

    private IImageLoaderFactory imageLoaderFactory;

    private ImageOperationsURLRewritingProvider imageOperationRepository;

    private IImagesCache imagesCache;

    private boolean noCache = false;

    /**
     * 
     */
    static final IBufferedImage INVALID_BUFFERED_IMAGE = new IBufferedImage() {
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

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String nc = getParameter(NO_CACHE_PARAMETER);
        if ("true".equalsIgnoreCase(nc)) {
            noCache = true;
            LOG.info("Enable no cache mode.");
        }

        int maxImageCacheSize = Constants.DEFAULT_MAX_IMAGE_CACHE_SIZE;
        String mics = getParameter(MAX_IMAGE_CACHE_SIZE_PARAMETER);
        if (mics != null) {
            maxImageCacheSize = Integer.parseInt(mics);

            LOG.info("Set max image cache size to " + maxImageCacheSize + ".");

        } else {
            LOG.info("Set max image cache size to default value ("
                    + maxImageCacheSize + ").");
        }

        String imageRepositoryURL = ServletTools.computeResourceURI(
                getServletContext(), IMAGE_REPOSITORY_DEFAULT_URL, getClass());
        LOG.info("Set image repository url to '" + imageRepositoryURL + "'.");

        getServletContext().setAttribute(IMAGE_REPOSITORY_PROPERTY,
                imageRepositoryURL);

        imagesCache = new BasicImagesCache(maxImageCacheSize);

        imageLoaderFactory = Constants.getImageLoaderFactory();
    }

    protected void doHead(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        // On gere le fonctionement HEAD dans le traitement du GET !
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String url = request.getRequestURI();

        String contextPath = request.getContextPath();
        if (contextPath != null) {
            url = url.substring(contextPath.length());
        }

        String servletPath = request.getServletPath();
        if (servletPath != null) {
            url = url.substring(servletPath.length());
        }

        // Retire le nom de notre servlet
        int idx = url.indexOf('/');
        if (idx >= 0) {
            url = url.substring(idx + 1);
        }

        // Recherche la commande
        idx = url.indexOf('/');
        if (idx < 0) {
            throw new ServletException("Bad uri. (can not find command into '"
                    + request.getRequestURI() + "')");
        }

        String cmd = url.substring(0, idx);
        String imageURL = url.substring(idx + 1);

        idx = imageURL.indexOf('?');
        if (idx >= 0) {
            imageURL = imageURL.substring(0, idx);
        }
        idx = imageURL.indexOf('#');
        if (idx >= 0) {
            imageURL = imageURL.substring(0, idx);
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Image request: requestURI='" + request.getRequestURI()
                    + "' contextPath='" + contextPath + "' servletPath='"
                    + servletPath + "' imageUrl='" + imageURL + "'.");
        }

        if (imageURL.length() < 1) {
            throw new ServletException("Bad uri. (no image resource into '"
                    + request.getRequestURI() + "')");
        }

        cmd = URLDecoder.decode(cmd, URL_DECODER_CHARSET);
        imageURL = URLFormCodec.decodeURL(imageURL);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Image request: cmd=" + cmd + " imageURL=" + imageURL);
        }

        IBufferedImage bufferedImage;
        synchronized (imagesCache) {
            bufferedImage = imagesCache.searchInCache(cmd, imageURL);
            if (bufferedImage == null) {
                bufferedImage = createNewBufferedImage(url);

                imagesCache.storeIntoCache(cmd, imageURL, bufferedImage);
            }
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Process bufferedImage: " + bufferedImage);
        }

        synchronized (bufferedImage) {
            if (bufferedImage.isInitialized() == false) {
                LOG.debug("Buffered image '" + bufferedImage.getName()
                        + "' is not initialized !");

                try {
                    initializeBufferedImage(bufferedImage, cmd, imageURL,
                            request, response);
                } catch (ServletException ex) {
                    bufferedImage.setErrored();

                    throw ex;
                }
            }
        }

        if (bufferedImage.isErrored()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Buffered image is errored !");
            }

            // XXX Peut etre a revoir pour mettre une date d'expiration
            // autre
            // ....
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Set no cache into response.");
            }
            ParametredHttpServlet.setNoCache(response);

            return;
        }

        String redirection = bufferedImage.getRedirection();
        if (redirection != null) {

            if (contextPath.endsWith("/") == false) {
                redirection = "/" + redirection;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Send temporary redirection to '" + redirection
                        + "'.");
            }

            response.sendRedirect(contextPath + redirection);
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            return;
        }

        boolean different = noCache;

        String etag = bufferedImage.getETag();
        if (different == false && etag != null) {
            String ifETag = request.getHeader(HTTP_IF_NONE_MATCH);
            if (ifETag != null) {
                if (etag.equals(ifETag)) {

                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("Client sent the same ETag, send a NOT MODIFIED response.");
                    }

                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }

                different = true;
            }
        }

        String hash = bufferedImage.getHash();
        if (different == false && hash != null) {
            String isHash = request.getHeader(HTTP_IF_NOT_HASH);
            if (isHash != null) {
                if (hash.equals(isHash)) {

                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("Client sent the same HashCode, send a NOT MODIFIED response.");
                    }

                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }

                different = true;
            }
        }

        long modificationDate = bufferedImage.getModificationDate();
        if (different == false) {
            long ifModifiedSince = request
                    .getDateHeader(HTTP_IF_MODIFIED_SINCE);
            if (ifModifiedSince > 0) {
                if (ifModifiedSince >= modificationDate) {

                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("Client sent a valid date for last modification, send a NOT MODIFIED response.");
                    }

                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }
            }
        }

        String contentType = bufferedImage.getContentType();
        response.setContentType(contentType);

        if (noCache) {
            setNoCache(response);

        } else {
            if (modificationDate > 0) {
                response.setDateHeader(HTTP_LAST_MODIFIED, modificationDate);
            }

            ExpirationDate expirationDate = null;
            if (expirationDate == null) {
                expirationDate = getDefaultExpirationDate();
            }

            expirationDate.sendExpires(response);
        }

        if (etag != null) {
            response.setHeader(HTTP_ETAG, etag);
        }

        if (hash != null) {
            response.setHeader(HTTP_HASH, hash);
        }

        response.setContentLength(bufferedImage.getSize());

        if (request.getMethod().equals(HEAD_HTTP_METHOD)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Head method requested, dont send the data.");
            }
            return;
        }

        OutputStream outputStream = response.getOutputStream();

        int bufferSize = response.getBufferSize();
        if (bufferSize < 1 || bufferSize > MAX_BUFFER_SIZE) {
            bufferSize = MAX_BUFFER_SIZE;
        }

        byte buf[] = new byte[bufferSize];

        InputStream inputStream = bufferedImage.getContent();
        try {
            for (;;) {
                int ret = inputStream.read(buf);
                if (ret < 1) {
                    break;
                }

                outputStream.write(buf, 0, ret);
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }
    }

    protected IBufferedImage createNewBufferedImage(String imageName) {
        return new FileRenderedImage(imageName);
    }

    private void initializeBufferedImage(IBufferedImage bufferedImage,
            String cmd, String url, ServletRequest request,
            ServletResponse response) throws ServletException {

        if (url.indexOf("WEB-INF") >= 0 || url.indexOf("META-INF") >= 0) {
            throw new ServletException(
                    "Can not get image into WEB-INF folder ! (url='" + url
                            + "')");
        }
        if (url.indexOf("../") >= 0 || url.indexOf("//") >= 0) {
            throw new ServletException("Invalid url='" + url + "'.");

        }

        synchronized (this) {
            if (imageOperationRepository == null) {
                imageOperationRepository = ImageOperationsURLRewritingProvider
                        .getInstance(getServletContext(), request, response);
            }
        }

        Map parameters = null;
        int idxParam = cmd.indexOf('(');
        if (idxParam > 0) {
            if (cmd.charAt(cmd.length() - 1) != ')') {
                throw new ServletException("Invalid parameters for command '"
                        + cmd + "'.");

            }
            String params = cmd.substring(idxParam + 1, cmd.length() - 1);
            cmd = cmd.substring(0, idxParam);

            StringTokenizer st = new StringTokenizer(params, ",");
            parameters = new HashMap(st.countTokens());
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

                parameters.put(pName, pValue);
            }
        }

        IImageOperation imageOperation = imageOperationRepository
                .getImageOperation(cmd);
        if (imageOperation == null) {
            throw new ServletException(
                    "Can not get image operation associated to id '" + cmd
                            + "'.");
        }

        String forceSuffix = imageOperation.getForceSuffix();
        if (forceSuffix != null && url.endsWith(forceSuffix)) {
            url = url.substring(0, url.length() - forceSuffix.length() - 1);
        }

        String sourceContentType = imageOperationRepository.getContentType(url);
        if (sourceContentType == null) {
            throw new ServletException("Invalid extension of image (url=" + url
                    + ").");
        }

        if (imageOperationRepository.isValidContenType(sourceContentType) == false) {
            // Support du type d'image inconnu !

            LOG
                    .warn("Image format writer for content type '"
                            + sourceContentType
                            + "' is not implemented ! (Please install JAI_IMAGEIO, https://jai-imageio.dev.java.net )");

            try {
                bufferedImage.initializeRedirection(url);

            } catch (IOException e) {
                throw new ServletException("Can not create filtered image '"
                        + url + "'.", e);
            }

            return;
        }

        IImageLoader imageDownloader = getImageDownloader(url, request,
                response);

        String downloadedContentType = imageDownloader.getContentType();
        if (sourceContentType.equalsIgnoreCase(downloadedContentType) == false) {
            LOG.error("Different content types request='" + sourceContentType
                    + "' loaded='" + downloadedContentType + "' for path '"
                    + url + "'.");

            bufferedImage.setErrored();
            return;
        }

        InputStream inputStream = imageDownloader.openStream();

        if (inputStream == null) {
            LOG.error("Can not get image specified by path '" + url + "'.");

            bufferedImage.setErrored();
            return;
        }

        String internalContentType = sourceContentType;
        if (imageOperation.getInternalContentType() != null) {
            internalContentType = imageOperation.getInternalContentType();
        }

        String externalContentType = sourceContentType;
        if (imageOperation.getExternalContentType() != null) {
            externalContentType = imageOperation.getExternalContentType();
        }

        if (externalContentType == null) {
            externalContentType = internalContentType;
        }

        Iterator it = ImageIO.getImageWritersByMIMEType(internalContentType);
        ImageWriter imageWriter = (ImageWriter) it.next();

        BufferedImage image;
        try {
            it = ImageIO.getImageReadersByMIMEType(sourceContentType);

            if (it.hasNext() == false) {
                throw new ServletException("Can not get codec to read image '"
                        + url + "'.");
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
            throw new ServletException("Can not load image '" + url + "'.", e);

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

        try {
            RenderedImage renderedImage = filter(image,
                    new IImageOperation[] { imageOperation },
                    new Map[] { (parameters == null) ? Collections.EMPTY_MAP
                            : parameters });

            try {
                bufferedImage.initialize(imageDownloader, externalContentType,
                        renderedImage, imageWriter, sourceImageType);

            } catch (IOException e) {
                throw new ServletException("Can not create filtered image '"
                        + url + "'.", e);
            }

        } finally {
            imageWriter.dispose();
        }
    }

    private IImageLoader getImageDownloader(String url, ServletRequest request,
            ServletResponse response) {
        return imageLoaderFactory.loadImage(getServletContext(),
                (HttpServletRequest) request, (HttpServletResponse) response,
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

    static String getImagesRepositoryURI(Map context) {
        return (String) context.get(IMAGE_REPOSITORY_PROPERTY);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */

    public static interface IBufferedImage {
        void initialize(IImageLoader imageDownloader, String contentType,
                RenderedImage renderedImage, ImageWriter imageWriter,
                int imageType) throws IOException;

        String getName();

        void initializeRedirection(String url) throws IOException;

        String getRedirection();

        int getSize();

        boolean isErrored();

        void setErrored();

        boolean isInitialized();

        InputStream getContent() throws IOException;

        String getContentType();

        long getModificationDate();

        String getHash();

        String getETag();
    }
}
