/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.3  2006/09/05 08:57:13  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.14  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.13  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.12  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.11  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.10  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.9  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.8  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.7  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.6  2005/09/16 09:54:40  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.5  2005/03/18 18:03:41  oeuillot
 * Ameliration du look du TabbedPane !
 *
 * Revision 1.4  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.3  2005/02/18 14:46:08  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/30 12:52:48  oeuillot
 * *** empty log message ***
 *
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

    // private static final String DEFAULT_STYLESHEET_URI = "/rcfaces";

    private static final String BASE_DIRECTORY = StylesheetsServlet.class
            .getPackage().getName().replace('.', '/') + '/';

    static final String CAMELIA_CSS_URL = "rcfaces.css";

    private final int MAX_404_RESPONSE = 64;

    private static final String CSS_MIME_TYPE = "text/css";

    private static final String HTML_MIME_TYPE = "text/html";

    private static final String CSS_CONFIG_PROPERTY = "org.rcfaces.renderkit.html.CSS_CONFIG";

    private static Map extensions = new HashMap(10);
    {
        extensions.put("css", CSS_MIME_TYPE);
        extensions.put("html", HTML_MIME_TYPE);
        extensions.put("gif", "image/gif");
        extensions.put("jpg", "image/jpeg");
        extensions.put("jpeg", "image/jpg");
        extensions.put("png", "image/png");
    }

    private static Set useGZIPExtensions = new HashSet();
    static {
        useGZIPExtensions.add(CSS_MIME_TYPE);
        useGZIPExtensions.add(HTML_MIME_TYPE);
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

    private static final String NO_CACHE_PARAMETER = Constants
            .getPackagePrefix()
            + ".NO_CACHE";

    private static final int WORK_BUFFER_SIZE = 32000;

    private static final String CHARSET_PARAMETER = Constants
            .getPackagePrefix()
            + ".CSS_CHARSET";

    private final Map bufferedResponse = new HashMap();

    private String styleSheetURI;

    private int count404Responses;

    private int count200Responses;

    private boolean noCache = false;

    private StyleSheetRepository repository;

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

            if (Constants.FRAMEWORK_VERSIONED_URL_SUPPORT) {
                if (version != null) {
                    uri += "/" + version;
                }
            }

            getServletContext().setAttribute(CSS_CONFIG_PROPERTY,
                    new CssConfig(fileName, uri));
        }

        StyleSheetRepository r = getRepository();
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
            throw new FacesException("No initialized stylesheet config !");
        }

        return cssConfig;
    }

    private StyleSheetRepository getRepository() throws ServletException {
        synchronized (this) {
            if (repository != null) {
                return repository;
            }

            StyleSheetRepository r = createStyleSheetRepository();
            if (r == null) {
                throw new ServletException("Can not load Stylesheet files ...");
            }

            if (noCache == false) {
                this.repository = r;
            }

            return r;
        }
    }

    private StyleSheetRepository createStyleSheetRepository()
            throws ServletException {

        String repositoryVersionSupport = getParameter(REPOSITORY_VERSION_SUPPORT_PARAMETER);
        if ("false".equalsIgnoreCase(repositoryVersionSupport) == false) {
            String cameliaVersion = Constants.getVersion();

            if (cameliaVersion == null) {
                throw new FacesException(
                        "Can not enable \"Repository version\", camelia version is not detected !");
            }
            repositoryVersion = cameliaVersion;

            LOG.info("Set repository version to '" + cameliaVersion
                    + "' for servlet '" + getServletName() + "'.");
        }

        return new StyleSheetRepository(getServletConfig(), getModules(),
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
        String url = request.getRequestURI();

        String contextPath = request.getContextPath();
        if (contextPath != null) {
            url = url.substring(contextPath.length());
        }

        String servletPath = request.getServletPath();
        if (servletPath != null) {
            url = url.substring(servletPath.length());
        }

        int idx = url.indexOf('/');
        if (idx >= 0) {
            url = url.substring(idx + 1);
        }

        if (repositoryVersion != null) {
            String version = null;
            if (Constants.FRAMEWORK_VERSIONED_URL_SUPPORT) {
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
                        response.setStatus(HttpServletResponse.SC_CONFLICT);
                        return;
                    }
                }
            }
        }

        Response res;
        synchronized (bufferedResponse) {
            res = (Response) bufferedResponse.get(url);
            if (noCache == false) {
                if (res != null) {
                    res.send(request, response);

                    return;
                }
            }
        }

        synchronized (this) {
            synchronized (bufferedResponse) {
                res = (Response) bufferedResponse.get(url);
                if (res != null) {
                    if (noCache == false) {
                        res.send(request, response);

                        return;
                    }

                    if (CAMELIA_CSS_URL.equals(url)) {
                        res.send(request, response);
                        return;
                    }
                }

                if (CAMELIA_CSS_URL.equals(url)) {
                    res = new StyleSheetRepositoryResponse(CSS_MIME_TYPE,
                            getRepository());

                    if (noCache == false) {
                        bufferedResponse.put(CAMELIA_CSS_URL, res);
                    }

                    res.send(request, response);
                    return;
                }
            }

            URL resourceURL = getClass().getClassLoader().getResource(
                    BASE_DIRECTORY + url);
            if (resourceURL == null) {
                record404(url, response);
                return;
            }

            URLConnection urlConnection = resourceURL.openConnection();
            if (urlConnection == null) {
                record404(url, response);
                return;
            }

            long lastModified = urlConnection.getLastModified();
            int size = urlConnection.getContentLength();

            InputStream in = urlConnection.getInputStream();
            if (in == null) {
                record404(url, response);
                return;
            }

            try {
                record200(url, in, size, lastModified, request, response);
                return;

            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    log("Can not close pipe '" + in + "'", ex);
                }
            }
        }
    }

    private void record200(String url, InputStream in, int size,
            long lastModified, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

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
            charset = null;
        }

        if (useFilterExtensions.contains(extension)) {
            String filtered = SourceFilter
                    .filter(new String(workBytes, charset));
            workBytes = filtered.getBytes(charset);

        } else if (useFilterSkipSpacesExtensions.contains(extension)) {
            String filtered = SourceFilter.filterSkipSpaces(new String(
                    workBytes, charset));
            workBytes = filtered.getBytes(charset);
        }

        byte bufferGZIP[] = null;
        if (hasGZipSupport()) {
            if (useGZIPExtensions.contains(extension)) {
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
                            "Can not create GZIP buffer of adonis css files.",
                            ex3);
                }
            }
        }

        Response res = new BytesResponse(workBytes, mimeType, bufferGZIP,
                lastModified);
        synchronized (bufferedResponse) {
            bufferedResponse.put(url, res);
        }
        count200Responses++;

        if (response != null) {
            res.send(request, response);
        }
    }

    private void record404(String url, HttpServletResponse response)
            throws IOException {
        if (count404Responses < MAX_404_RESPONSE) {
            count404Responses++;

            synchronized (bufferedResponse) {
                bufferedResponse.put(url, new NotFoundResponse(url));
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND,
                "URL requested not found '" + url + "'");
    }

    private interface Response {

        public void send(HttpServletRequest request,
                HttpServletResponse response) throws IOException,
                ServletException;
    }

    private class NotFoundResponse implements Response {
        private static final String REVISION = "$Revision$";

        private final String message;

        public NotFoundResponse(String url) {
            message = "URL requested not found '" + url + "'";
        }

        public void send(HttpServletRequest request,
                HttpServletResponse response) throws IOException {
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

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.webapp.StylesheetsServlet.AbstractResponse#getBuffer()
         */
        protected byte[] getBuffer() {
            return buffer;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.webapp.StylesheetsServlet.AbstractResponse#getGZipedBuffer()
         */
        protected byte[] getGZipedBuffer() {
            return bufferGZIP;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.webapp.StylesheetsServlet.AbstractResponse#getLastModified()
         */
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

        public AbstractResponse(String mimeType) {
            this.mimeType = mimeType;
        }

        public final void send(HttpServletRequest request,
                HttpServletResponse response) throws IOException,
                ServletException {

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

            long lastModified = getLastModified();
            if (lastModified > 0) {
                long ifModifiedSince = request
                        .getDateHeader(HTTP_IF_MODIFIED_SINCE);
                if (ifModifiedSince > 0) {
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

                    setGzipContentEncoding(response);

                    buf = gzip;
                }
            }

            if (noCache) {

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Set no cache for response.");
                }
                ConfiguredHttpServlet.setNoCache(response);

            } else {
                ExpirationDate expirationDate = getDefaultExpirationDate();
                if (expirationDate != null) {
                    expirationDate.sendExpires(response);
                }

                if (lastModified > 0) {
                    response.setDateHeader(HTTP_LAST_MODIFIED, lastModified);
                }
            }

            response.setContentLength(buf.length);
            if (etag != null) {
                response.setHeader(HTTP_ETAG, etag);
            }
            if (hash != null) {
                response.setHeader(HTTP_HASH, hash);
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

        private final StyleSheetRepository styleSheetRepository;

        public StyleSheetRepositoryResponse(String mimeType,
                StyleSheetRepository styleSheetRepository) {
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