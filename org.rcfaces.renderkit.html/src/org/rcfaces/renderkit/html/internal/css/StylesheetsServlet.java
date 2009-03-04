/*
 * $Id$
 */

package org.rcfaces.renderkit.html.internal.css;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Globals;
import org.rcfaces.core.internal.codec.SourceFilter;
import org.rcfaces.core.internal.lang.ByteBufferOutputStream;
import org.rcfaces.core.internal.util.ServletTools;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.internal.webapp.ExpirationDate;
import org.rcfaces.core.internal.webapp.URIParameters;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlModulesServlet;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StylesheetsServlet extends HtmlModulesServlet {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 708578720264413327L;

    private static final Log LOG = LogFactory.getLog(StylesheetsServlet.class);

    private static final String BASE_DIRECTORY = StylesheetsServlet.class
            .getPackage().getName().replace('.', '/') + '/';

    static final String CAMELIA_CSS_URL = "rcfaces.css";

    private final int MAX_404_RESPONSE = 64;

    private static final String CSS_CONFIG_PROPERTY = "org.rcfaces.renderkit.html.CSS_CONFIG";

    private static Map extensions = new HashMap(10);
    {
        extensions.put("js", JAVASCRIPT_MIME_TYPE);
        extensions.put("txt", TEXT_PLAIN_MIME_TYPE);
        extensions.put("css", CSS_MIME_TYPE);
        extensions.put("html", HTML_MIME_TYPE);
        extensions.put("gif", "image/gif");
        extensions.put("jpg", "image/jpeg");
        extensions.put("jpeg", "image/jpg");
        extensions.put("png", "image/png");
    }

    private static Set useFilterExtensions = new HashSet();
    static {
        useFilterExtensions.add(CSS_MIME_TYPE);
    }

    private static Set useFilterSkipSpacesExtensions = new HashSet();
    static {
        // useFilterSkipSpacesExtensions.add("css");
    }

    private static final String REPOSITORY_VERSION_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".REPOSITORY_VERSION_SUPPORT";

    private static final String CONFIGURATION_VERSION_PARAMETER = Constants
            .getPackagePrefix()
            + ".CONFIGURATION_VERSION";

    private static final String NO_CACHE_PARAMETER = Constants
            .getPackagePrefix()
            + ".NO_CACHE";

    private static final int WORK_BUFFER_SIZE = 32000;

    private static final String CHARSET_PARAMETER = Constants
            .getPackagePrefix()
            + ".CSS_CHARSET";

    private final Map bufferedResponse = new HashMap(1024);

    private String styleSheetURI;

    private int count404Responses;

    private int count200Responses;

    private boolean noCache = false;

    private StyleSheetSourceContainer repository;

    private Object useMetaContentStyleType;

    private String repositoryVersion;

    private String charset;

    public StylesheetsServlet() {
    }

    public StylesheetsServlet(String styleSheetURI) {
        this.styleSheetURI = styleSheetURI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {

        if (styleSheetURI == null) {
            styleSheetURI = ServletTools.computeResourceURI(config
                    .getServletContext(), null, getClass());
        }

        super.init(config);

        String nc = getParameter(NO_CACHE_PARAMETER);
        if ("true".equalsIgnoreCase(nc)) {
            noCache = true;
        }

        charset = getParameter(CHARSET_PARAMETER);
        if (charset != null) {
            LOG.info("Charset setted to \"" + charset + "\"  for sevlet '"
                    + getServletName() + "'.");
        } else {
            charset = Constants.CSS_DEFAULT_CHARSET;

            LOG.info("Charset setted to DEFAULT value: \"" + charset
                    + "\"  for sevlet '" + getServletName() + "'");
        }

        if (getServletContext().getAttribute(CSS_CONFIG_PROPERTY) == null) {
            String fileName = CAMELIA_CSS_URL;
            String version = getRepository().getVersion();
            if (version != null) {
                URIParameters up = new URIParameters(fileName);

                // up.appendVersion(version);

                fileName = up.computeParametredURI();
            }

            String uri = styleSheetURI;

            if (Constants.VERSIONED_FRAMEWORK_URL_SUPPORT) {
                if (version != null) {
                    uri += "/" + version;
                }
            }

            getServletContext().setAttribute(CSS_CONFIG_PROPERTY,
                    new CssConfig(fileName, uri));
        }

        StyleSheetSourceContainer r = getRepository();
        if (noCache == false) {
            r.getRawBuffer();
        }
    }

    protected final String getCharset() {
        return charset;
    }

    public static ICssConfig getConfig(IHtmlProcessContext htmlExternalContext) {
        ICssConfig cssConfig = (ICssConfig) htmlExternalContext
                .getFacesContext().getExternalContext().getApplicationMap()
                .get(CSS_CONFIG_PROPERTY);

        if (cssConfig == null) {
            throw new FacesException(
                    "No initialized stylesheet config ! (You have forgotten the RCFaces servlet, or its startup has failed !)");
        }

        return cssConfig;
    }

    private StyleSheetSourceContainer getRepository() throws ServletException {
        synchronized (this) {
            if (repository != null) {
                return repository;
            }

            StyleSheetSourceContainer r = createStyleSheetRepository();
            if (r == null) {
                throw new ServletException("Can not load Stylesheet files ...");
            }

            if (noCache == false) {
                this.repository = r;

            } else {
                LOG.debug("'noCache' is enable, ignore new repository !");
            }

            return r;
        }
    }

    private StyleSheetSourceContainer createStyleSheetRepository()
            throws ServletException {

        String repositoryVersionSupport = getParameter(REPOSITORY_VERSION_SUPPORT_PARAMETER);
        if ("false".equalsIgnoreCase(repositoryVersionSupport) == false) {
            String buildId = Constants.getBuildId();

            if (buildId == null) {
                throw new FacesException(
                        "Can not enable \"Repository version\", camelia buildId is not detected !");
            }

            String configurationVersion = getParameter(CONFIGURATION_VERSION_PARAMETER);
            if (configurationVersion != null) {
                buildId += "." + configurationVersion;
            }

            repositoryVersion = buildId;

            LOG.info("Set repository version to buildId='" + buildId
                    + "' for servlet '" + getServletName() + "'.");
        }

        return new StyleSheetSourceContainer(getServletConfig(), getModules(),
                getCharset(), hasGZipSupport(), hasEtagSupport(),
                hasHashSupport(), repositoryVersion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String requestURL = (String) request
                .getAttribute(Globals.INCLUDE_REQUEST_URI_ATTR);
        if (requestURL == null) {
            requestURL = request.getRequestURI();
        }

        String url = requestURL;

        String contextPath = (String) request
                .getAttribute(Globals.INCLUDE_CONTEXT_PATH_ATTR);
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }

        if (contextPath != null) {
            url = url.substring(contextPath.length());
        }

        String servletPath = (String) request
                .getAttribute(Globals.INCLUDE_SERVLET_PATH_ATTR);
        if (servletPath == null) {
            servletPath = request.getServletPath();
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("New request uri='" + requestURL + "' contextPath='"
                    + contextPath + "' servletPath='" + servletPath + "'.");
        }

        if (servletPath != null) {
            url = url.substring(servletPath.length());
        }

        int idx = url.indexOf('/');
        if (idx >= 0) {
            url = url.substring(idx + 1);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Filtred url='" + url + "' for requestURI='" + requestURL
                    + "'.");
        }

        if (repositoryVersion != null) {
            String version = null;
            if (Constants.VERSIONED_FRAMEWORK_URL_SUPPORT) {
                idx = url.indexOf('/');
                if (idx < 0) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                version = url.substring(0, idx);
                url = url.substring(idx + 1);

            } else {
                URIParameters up = URIParameters.parseURI(url);
                if (up != null) {
                    version = up.getVersion();
                    url = up.getURI();
                }
            }

            if (version != null) {
                String repositoryVersion = getRepository().getVersion();
                if (repositoryVersion != null) {
                    if (repositoryVersion.equals(version) == false) {
                        LOG.error("Not same repository version ! (current="
                                + repositoryVersion + " request=" + version
                                + ")");

                        setNoCache(response);
                        response.sendError(HttpServletResponse.SC_CONFLICT,
                                "Invalid RCFaces version !");
                        return;
                    }
                }
            }
        }

        Response res = null;
        ResponseFacade responseFacade = null;
        synchronized (bufferedResponse) {
            Object ret = bufferedResponse.get(url);
            if (ret == null) {
                responseFacade = new ResponseFacade(url);
                if (noCache == false) {
                    bufferedResponse.put(url, responseFacade);
                }

            } else if (ret instanceof Response) {
                res = (Response) ret;

            } else {
                responseFacade = (ResponseFacade) ret;
            }
        }

        if (res != null) {
            res.send(request, response);
            return;
        }

        responseFacade.send(request, response);
    }

    private Response record200(String url, InputStream in, int size,
            long lastModified) throws IOException, ServletException {

        int ex = url.lastIndexOf('.');
        int ex2 = url.lastIndexOf('/');
        if (ex < 0 || ex < ex2) {
            throw new ServletException("Can not find extension of url '" + url
                    + "'");
        }
        String extension = url.substring(ex + 1).toLowerCase();

        String mimeType = (String) extensions.get(extension);
        if (mimeType == null) {
            throw new ServletException("Unknown extension '" + extension + "'");
        }

        if (size < 1) {
            size = WORK_BUFFER_SIZE;
        }

        ByteBufferOutputStream bout = new ByteBufferOutputStream(size);

        byte w[] = new byte[8000];
        for (;;) {
            int ret = in.read(w);

            if (ret <= 0) {
                break;
            }

            bout.write(w, 0, ret);
        }

        byte workBytes[] = bout.toByteArray();

        if (charset == null) {
            charset = getCharset();
        }

        if (useFilterExtensions.contains(extension)) {
            String filtred = SourceFilter
                    .filter(new String(workBytes, charset));
            workBytes = filtred.getBytes(charset);

        } else if (useFilterSkipSpacesExtensions.contains(extension)) {
            String filtred = SourceFilter.filterSkipSpaces(new String(
                    workBytes, charset));
            workBytes = filtred.getBytes(charset);
        }

        byte bufferGZIP[] = null;
        if (hasGZipSupport() && isMimeTypeSupportGZip(mimeType)) {
            try {
                ByteBufferOutputStream bos = new ByteBufferOutputStream(
                        workBytes.length);
                GZIPOutputStream gzos = new GZIPOutputStream(bos,
                        workBytes.length);
                gzos.write(workBytes);
                gzos.close();

                bos.close();

                bufferGZIP = bos.toByteArray();

            } catch (IOException ex3) {
                throw new ServletException(
                        "Can not create GZIP buffer of adonis css files.", ex3);
            }
        }

        Response res = new BytesResponse(workBytes, mimeType, bufferGZIP,
                lastModified);

        return res;
    }

    private Response record404(String url) {

        return new NotFoundResponse(url);
        /*
         * if (count404Responses < MAX_404_RESPONSE) { count404Responses++;
         * 
         * synchronized (bufferedResponse) { bufferedResponse.put(url, new
         * NotFoundResponse(url)); } }
         * 
         * response.sendError(HttpServletResponse.SC_NOT_FOUND, "URL requested
         * not found '" + url + "'");
         */
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private class ResponseFacade {
        private static final String REVISION = "$Revision$";

        private final String url;

        private Response response;

        public ResponseFacade(String url) {
            this.url = url;
        }

        public synchronized void send(HttpServletRequest httpRequest,
                HttpServletResponse httpResponse) throws IOException,
                ServletException {
            if (response != null) {
                response.send(httpRequest, httpResponse);
                return;
            }

            if (CAMELIA_CSS_URL.equals(url)) {
                setResponse(new StyleSheetRepositoryResponse(CSS_MIME_TYPE+"; charset="+getCharset(),
                        getRepository()), httpRequest, httpResponse);
                return;
            }

            URL resourceURL = getClass().getClassLoader().getResource(
                    BASE_DIRECTORY + url);
            if (resourceURL == null) {
                setResponse(record404(url), httpRequest, httpResponse);
                return;
            }

            URLConnection urlConnection = resourceURL.openConnection();
            if (urlConnection == null) {
                setResponse(record404(url), httpRequest, httpResponse);
                return;
            }

            long lastModified = urlConnection.getLastModified();
            int size = urlConnection.getContentLength();

            InputStream in = urlConnection.getInputStream();
            if (in == null) {
                setResponse(record404(url), httpRequest, httpResponse);
                return;
            }

            try {
                setResponse(record200(url, in, size, lastModified),
                        httpRequest, httpResponse);
                return;

            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    log("Can not close pipe '" + in + "'", ex);
                }
            }
        }

        private void setResponse(Response response,
                HttpServletRequest httpRequest, HttpServletResponse httpResponse)
                throws IOException, ServletException {
            this.response = response;

            if (noCache == false) {
                synchronized (bufferedResponse) {
                    bufferedResponse.put(url, response);
                }
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Response for '" + url + "' => " + response);
            }

            response.send(httpRequest, httpResponse);
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private interface Response {

        public void send(HttpServletRequest request,
                HttpServletResponse response) throws IOException,
                ServletException;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private class NotFoundResponse implements Response {
        private static final String REVISION = "$Revision$";

        private final String message;

        public NotFoundResponse(String url) {
            message = "URL requested not found '" + url + "'";
        }

        public void send(HttpServletRequest request,
                HttpServletResponse response) throws IOException {

            if (Constants.STAT_RESOURCES_HTTP_RESPONSE) {
                synchronized (StylesheetsServlet.this) {
                    count404Responses++;
                }
            }

            response.sendError(HttpServletResponse.SC_NOT_FOUND, message);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private class BytesResponse extends AbstractResponse {

        private static final String REVISION = "$Revision$";

        private final byte buffer[];

        private final long lastModified;

        private final byte bufferGZIP[];

        private final String etag;

        private final String hash;

        public BytesResponse(byte[] buffer, String mimeType, byte bufferGZIP[],
                long lastModified) {
            super(mimeType);

            this.buffer = buffer;
            this.bufferGZIP = bufferGZIP;

            this.lastModified = lastModified;

            if (hasEtagSupport()) {
                etag = computeETag(buffer);

            } else {
                etag = null;
            }

            if (hasHashSupport()) {
                hash = computeHash(buffer);

            } else {
                hash = null;
            }
        }

        protected byte[] getBuffer() {
            return buffer;
        }

        protected byte[] getGZipedBuffer() {
            return bufferGZIP;
        }

        protected long getLastModified() {
            return lastModified;
        }

        protected String getETag() {
            return etag;
        }

        protected String getHash() {
            return hash;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private abstract class AbstractResponse implements Response {

        private static final String REVISION = "$Revision$";

        private final String mimeType;

        private String charSet;

        public AbstractResponse(String mimeType) {
            this.mimeType = mimeType;
        }

        public final void send(HttpServletRequest request,
                HttpServletResponse response) throws IOException,
                ServletException {

            long lastModified = getLastModified();
            if (lastModified > 0) {
                lastModified -= (lastModified % 1000);
            }

            if (noCache) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Set no cache for response.");
                }
                ConfiguredHttpServlet.setNoCache(response);

            } else {
                ExpirationDate expirationDate = getDefaultExpirationDate(repositoryVersion != null);
                if (expirationDate != null) {
                    expirationDate.sendExpires(response);
                }

                if (lastModified > 0) {
                    response.setDateHeader(HTTP_LAST_MODIFIED, lastModified);
                }
            }

            String etag = getETag();
            if (etag != null) {
                String ifETag = request.getHeader(HTTP_IF_NONE_MATCH);
                if (etag.equals(ifETag)) {
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }
            }

            String hash = getHash();
            if (hash != null) {
                String ifHash = request.getHeader(HTTP_IF_NOT_HASH);
                if (etag.equals(ifHash)) {
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return;
                }
            }

            if (lastModified > 0) {
                long ifModifiedSince = request
                        .getDateHeader(HTTP_IF_MODIFIED_SINCE);
                if (ifModifiedSince > 0) {
                    ifModifiedSince -= (ifModifiedSince % 1000);

                    if (ifModifiedSince >= lastModified) {
                        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                        return;
                    }
                }
            }

            byte buf[] = getBuffer();
            
            response.setContentType(mimeType);

            byte gzip[] = getGZipedBuffer();
            if (gzip != null) {
                if (hasGzipSupport(request)) {

                    setGzipContentEncoding(response, true);

                    buf = gzip;
                }
            }

            response.setContentLength(buf.length);
            if (etag != null) {
                response.setHeader(HTTP_ETAG, etag);
            }
            if (hash != null) {
                response.setHeader(HTTP_HASH, hash);
            }

            if (Constants.STAT_RESOURCES_HTTP_RESPONSE) {
                synchronized (StylesheetsServlet.this) {
                    count200Responses++;
                }
            }

            OutputStream out = response.getOutputStream();
            out.write(buf);
        }

        protected abstract byte[] getBuffer() throws ServletException;

        protected abstract byte[] getGZipedBuffer() throws ServletException;

        protected abstract long getLastModified() throws ServletException;

        protected abstract String getETag() throws ServletException;

        protected abstract String getHash() throws ServletException;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private class StyleSheetRepositoryResponse extends AbstractResponse {
        private static final String REVISION = "$Revision$";

        private final StyleSheetSourceContainer styleSheetRepository;

        public StyleSheetRepositoryResponse(String mimeType,
                StyleSheetSourceContainer styleSheetRepository) {
            super(mimeType);

            this.styleSheetRepository = styleSheetRepository;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.webapp.StylesheetsServlet.AbstractResponse#getBuffer()
         */
        protected byte[] getBuffer() throws ServletException {
            return styleSheetRepository.getRawBuffer();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.webapp.StylesheetsServlet.AbstractResponse#getGZipedBuffer()
         */
        protected byte[] getGZipedBuffer() throws ServletException {
            return styleSheetRepository.getGZipedBuffer();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.webapp.StylesheetsServlet.AbstractResponse#getLastModified()
         */
        protected long getLastModified() throws ServletException {
            return styleSheetRepository.getLastModified();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.webapp.StylesheetsServlet.AbstractResponse#getLastModified()
         */
        protected String getETag() throws ServletException {
            return styleSheetRepository.getETag();
        }

        protected String getHash() throws ServletException {
            return styleSheetRepository.getHash();
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static final class CssConfig implements ICssConfig {
        private static final String REVISION = "$Revision$";

        private final String styleSheetURI;

        private final String styleSheetFileName;

        public CssConfig(String styleSheetFileName, String styleSheetURI) {
            this.styleSheetFileName = styleSheetFileName;
            this.styleSheetURI = styleSheetURI;
        }

        public String getDefaultStyleSheetURI() {
            return styleSheetURI;
        }

        public String getStyleSheetFileName() {
            return styleSheetFileName;
        }
    }
}