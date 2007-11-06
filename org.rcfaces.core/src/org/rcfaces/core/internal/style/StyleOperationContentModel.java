/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.content.AbstractOperationContentModel;
import org.rcfaces.core.internal.content.IBufferOperation;
import org.rcfaces.core.internal.content.IFileBuffer;
import org.rcfaces.core.internal.images.Constants;
import org.rcfaces.core.internal.lang.ByteBufferOutputStream;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory.IResourceLoader;
import org.rcfaces.core.internal.style.CssParserFactory.ICssParser;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyleOperationContentModel extends AbstractOperationContentModel {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -2523152290767529042L;

    private static final Log LOG = LogFactory
            .getLog(StyleOperationContentModel.class);

    private static final int INITIAL_BUFFER_SIZE = 8 * 1024;

    private static final String STYLE_CONTENT_TYPE = "text/css";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String STYLE_SUFFIX = "css";

    private ICssParser cssParser;

    public StyleOperationContentModel(String resourceURL, String contentType,
            String versionId, String operationId,
            String filterParametersToParse, Map attributes,
            IStyleOperation styleOperation, ICssParser cssParser) {
        super(resourceURL, contentType, versionId, operationId,
                filterParametersToParse, attributes, styleOperation);

        setURLSuffix(STYLE_SUFFIX);

        this.cssParser = cssParser;
    }

    protected IFileBuffer createFileBuffer() {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        IStyleOperation styleOperation = (IStyleOperation) getBufferOperation(facesContext);
        if (styleOperation == null) {
            LOG.error("Can not get style operation associated to id '"
                    + getOperationId() + "'.");
            return INVALID_BUFFERED_FILE;
        }

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        IResourceLoaderFactory resourceLoaderFactory = getResourceLoaderFactory(rcfacesContext);

        ContentInfo contentInfo[] = new ContentInfo[1];

        String styleSheetContent = loadContent(facesContext,
                resourceLoaderFactory, getResourceURL(), getDefaultCharset(),
                contentInfo);

        if (styleSheetContent == null) {
            return INVALID_BUFFERED_FILE;
        }

        IStyleSheetFile styleSheetFile = createNewStyleSheetFile(getResourceURL());
        try {
            String newStyleSheetContent = filter(resourceLoaderFactory,
                    cssParser, getResourceURL(), styleSheetContent,
                    new IStyleOperation[] { styleOperation },
                    new Map[] { getFilterParameters() });

            newStyleSheetContent = "@charset \"" + contentInfo[0].charSet
                    + "\";\n" + newStyleSheetContent;

            String contentType = getContentType() + "; charset="
                    + contentInfo[0].charSet;

            styleSheetFile.initialize(contentType, newStyleSheetContent
                    .getBytes(contentInfo[0].getCharSet()), contentInfo[0]
                    .getLastModified());

        } catch (IOException e) {
            LOG.error("Can not create filtred image '" + getResourceURL()
                    + "'.", e);

            return INVALID_BUFFERED_FILE;
        }

        return styleSheetFile;
    }

    protected String getDefaultCharset() {
        return DEFAULT_CHARSET;
    }

    private static String getCharsetFromStream(InputStream inputStream) {

        BufferedInputStream bins = new BufferedInputStream(inputStream, 128);
        inputStream.mark(128);

        try {
            InputStreamReader reader = new InputStreamReader(bins, "ISO8859-1");

            BufferedReader bufReader = new BufferedReader(reader, 64);

            String line = bufReader.readLine();

            if (line == null) {
                return null;
            }

            line = line.trim();

            String cmd = line.toLowerCase();

            if (cmd.startsWith("@charset") == false) {
                return null;
            }
            cmd = cmd.substring(8).trim();

            if (cmd.endsWith(";")) {
                cmd = cmd.substring(0, cmd.length() - 1).trim();
            }

            if ((cmd.startsWith("\"") && cmd.endsWith("\""))
                    || (cmd.startsWith("'") && cmd.endsWith("'"))) {
                cmd = cmd.substring(1, cmd.length() - 1).trim();
            }

            return cmd;

        } catch (IOException e) {
            LOG.error("Can not determine charset !", e);
            return null;

        } finally {
            try {
                inputStream.reset();

            } catch (IOException e) {
                LOG.error("Can not reset position !", e);
            }
        }
    }

    protected synchronized IBufferOperation createBufferOperation(
            FacesContext facesContext) {

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        StyleContentAccessorHandler styleOperationRepository = (StyleContentAccessorHandler) rcfacesContext
                .getProvidersRegistry().getProvider(
                        StyleContentAccessorHandler.STYLE_CONTENT_PROVIDER_ID);

        IStyleOperation styleOperation = styleOperationRepository
                .getStyleOperation(getOperationId());

        return styleOperation;
    }

    private IStyleSheetFile createNewStyleSheetFile(String resourceURL) {
        return new StyleSheetFileBuffer(resourceURL);
    }

    private IResourceLoaderFactory getResourceLoaderFactory(
            RcfacesContext context) {

        IResourceLoaderFactory resourceLoaderFactory;
        if (context.isDesignerMode()) {
            resourceLoaderFactory = Constants.getDesignerImageLoaderFactory();

        } else {
            resourceLoaderFactory = Constants.getImageLoaderFactory();
        }

        return resourceLoaderFactory;
    }

    protected String filter(IResourceLoaderFactory resourceLoaderFactory,
            ICssParser cssParser, String styleSheetURL,
            String styleSheetContent, IStyleOperation styleOperations[],
            Map parameters[]) throws IOException {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Process " + styleOperations.length + " style operation"
                    + ((styleOperations.length > 1) ? "s" : ""));
        }

        for (int i = 0; i < styleOperations.length; i++) {
            IStyleOperation styleOperation = styleOperations[i];

            if (LOG.isTraceEnabled()) {
                LOG.trace("Process style operation #" + i + " '"
                        + styleOperation.getName() + "'");
            }

            styleSheetURL = styleOperation.filter(resourceLoaderFactory,
                    cssParser, styleSheetURL, styleSheetContent,
                    getDefaultCharset());
        }

        return styleSheetURL;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ContentInfo {
        private String charSet;

        private long lastModified;

        private long length;

        public final String getCharSet() {
            return charSet;
        }

        public final long getLastModified() {
            return lastModified;
        }

        public final long getLength() {
            return length;
        }
    }

    public static String loadContent(FacesContext facesContext,
            IResourceLoaderFactory resourceLoaderFactory, String path,
            String defaultCharset, ContentInfo contentInfoRef[]) {

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext
                .getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext
                .getResponse();
        ServletContext context = (ServletContext) externalContext.getContext();

        IResourceLoader resourceLoader = resourceLoaderFactory.loadResource(
                context, request, response, path);

        String downloadedContentType = resourceLoader.getContentType();
        if (downloadedContentType == null
                || downloadedContentType.equals(STYLE_CONTENT_TYPE) == false) {
            LOG.error("Different content types requested='"
                    + STYLE_CONTENT_TYPE + "' loaded='" + downloadedContentType
                    + "' for path '" + path + "'.");

            return null;
        }

        InputStream inputStream = resourceLoader.openStream();

        if (inputStream == null) {
            LOG.error("Can not get resource specified by path '" + path + "'.");

            return null;
        }

        ContentInfo contentInfo = new ContentInfo();

        if (contentInfoRef != null) {
            contentInfoRef[0] = contentInfo;
        }

        try {
            int size = INITIAL_BUFFER_SIZE;
            long originalSize = resourceLoader.getContentLength();
            contentInfo.length = originalSize;

            if (originalSize > 0 && originalSize < INITIAL_BUFFER_SIZE) {
                size = (int) originalSize + 256;
            }

            ByteBufferOutputStream bous = new ByteBufferOutputStream(size);

            byte b[] = new byte[size];
            for (;;) {
                int ret = inputStream.read(b);
                if (ret <= 0) {
                    break;
                }

                bous.write(b, 0, ret);
            }

            bous.close();

            byte buffer[] = bous.toByteArray();

            String charSet = getCharsetFromStream(new ByteArrayInputStream(
                    buffer));

            if (charSet == null) {
                charSet = defaultCharset;
            }

            contentInfo.charSet = charSet;
            contentInfo.lastModified = resourceLoader.getLastModified();

            return new String(buffer, charSet);

        } catch (IOException ex) {
            return null;

        } finally {
            try {
                inputStream.close();

            } catch (IOException ex) {
                LOG.debug("Can not close resource '" + path + "'.", ex);
            }
        }
    }
}