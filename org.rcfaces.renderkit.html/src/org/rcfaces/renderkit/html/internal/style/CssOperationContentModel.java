/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.content.AbstractBufferOperationContentModel;
import org.rcfaces.core.internal.content.IBufferOperation;
import org.rcfaces.core.internal.content.IFileBuffer;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.style.IStyleContentAccessorHandler;
import org.rcfaces.core.internal.style.IStyleOperation;
import org.rcfaces.core.internal.util.ApplicationParametersMap;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser.IParserContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssOperationContentModel extends
        AbstractBufferOperationContentModel {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(CssOperationContentModel.class);

    private static final long serialVersionUID = -7274200606212145834L;

    private static final String STYLE_CONTENT_TYPE = "text/css";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String STYLE_SUFFIX = "css";

    private final ICssParser cssParser;

    public CssOperationContentModel(String resourceURL, String contentType,
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

        IResourceLoaderFactory resourceLoaderFactory = getResourceLoaderFactory(facesContext);

        ContentInformation contentInfo[] = new ContentInformation[1];

        String styleSheetContent = null;
        String resourceURL = getResourceURL();
        styleSheetContent = loadContent(facesContext, resourceLoaderFactory,
                resourceURL, getDefaultCharset(), contentInfo);

        if (styleSheetContent == null) {
            return INVALID_BUFFERED_FILE;
        }

        Map applicationParameters = new ApplicationParametersMap(facesContext);

        IStyleSheetFile styleSheetFile = createNewStyleSheetFile(resourceURL);
        try {
            IParserContext parserContext = new ParserContext(contentInfo[0]
                    .getCharSet(), contentInfo[0].getLastModified());

            String newStyleSheetContent = filter(applicationParameters,
                    resourceLoaderFactory, cssParser, resourceURL,
                    styleSheetContent,
                    new IStyleOperation[] { styleOperation },
                    new Map[] { getFilterParameters() }, parserContext);

            newStyleSheetContent = "@charset \"" + parserContext.getCharset()
                    + "\";\n" + newStyleSheetContent;

            String contentType = getContentType() + "; charset="
                    + parserContext.getCharset();

            styleSheetFile.initialize(contentType, newStyleSheetContent
                    .getBytes(parserContext.getCharset()), parserContext
                    .getLastModifiedDate());

        } catch (IOException e) {
            LOG.error("Can not create filtred image '" + getResourceURL()
                    + "'.", e);

            return INVALID_BUFFERED_FILE;
        }

        return styleSheetFile;
    }

    protected String getCharsetFromStream(InputStream inputStream) {

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

        IStyleContentAccessorHandler styleOperationRepository = (IStyleContentAccessorHandler) rcfacesContext
                .getProvidersRegistry().getProvider(
                        IStyleContentAccessorHandler.STYLE_CONTENT_PROVIDER_ID);

        IStyleOperation styleOperation = styleOperationRepository
                .getStyleOperation(getOperationId());

        return styleOperation;
    }

    private IStyleSheetFile createNewStyleSheetFile(String resourceURL) {
        return new StyleSheetFileBuffer(resourceURL);
    }

    protected String filter(Map applicationParameters,
            IResourceLoaderFactory resourceLoaderFactory, ICssParser cssParser,
            String styleSheetURL, String styleSheetContent,
            IStyleOperation styleOperations[], Map parameters[],
            IParserContext parserContext) throws IOException {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Process " + styleOperations.length + " style operation"
                    + ((styleOperations.length > 1) ? "s" : ""));
        }

        for (int i = 0; i < styleOperations.length; i++) {
            ICssOperation styleOperation = (ICssOperation) styleOperations[i];

            if (LOG.isTraceEnabled()) {
                LOG.trace("Process style operation #" + i + " '"
                        + styleOperation.getName() + "'");
            }

            styleSheetURL = styleOperation.filter(applicationParameters,
                    resourceLoaderFactory, cssParser, styleSheetURL,
                    styleSheetContent, parserContext, this);
        }

        return styleSheetURL;
    }

    protected String getDefaultCharset() {
        return DEFAULT_CHARSET;
    }

    protected String getDefaultMimeType() {
        return STYLE_CONTENT_TYPE;
    }

    protected boolean isMimeTypeValid(String contentType) {
        return STYLE_CONTENT_TYPE.equalsIgnoreCase(contentType);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class ParserContext implements IParserContext {
        private String charset;

        private long lastModifiedDate;

        public ParserContext(String charset, long lastModifiedDate) {
            this.charset = charset;
            this.lastModifiedDate = lastModifiedDate;
        }

        public final String getCharset() {
            return charset;
        }

        public final void setCharset(String charset) {
            this.charset = charset;
        }

        public final long getLastModifiedDate() {
            return lastModifiedDate;
        }

        public final void setLastModifiedDate(long lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

    }
}