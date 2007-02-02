/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.ImageContentInformation;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.images.operation.IEFavoriteIconOperation;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.PageConfiguration;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IContext;
import org.rcfaces.renderkit.html.component.InitComponent;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.css.ICssConfig;
import org.rcfaces.renderkit.html.internal.css.StylesheetsServlet;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class InitRenderer extends AbstractHtmlRenderer {

    private static final Log LOG = LogFactory.getLog(InitRenderer.class);

    private static final String DISABLE_IE_IMAGE_BAR_PARAMETER = Constants
            .getPackagePrefix()
            + ".DISABLE_IE_IMAGE_BAR";

    private static final String DISABLE_CONTEXT_MENU_PARAMETER = Constants
            .getPackagePrefix()
            + ".DISABLE_CONTEXT_MENU";

    private static final String DISABLE_CACHE_PARAMETER = Constants
            .getPackagePrefix()
            + ".DISABLE_CACHE";

    private static final String DISABLED_COOKIES_PAGE_URL_PARAMETER = Constants
            .getPackagePrefix()
            + ".DISABLED_COOKIES_PAGE_URL";

    private static final String DISABLED_SCRIPT_PAGE_URL_PARAMETER = Constants
            .getPackagePrefix()
            + ".DISABLED_SCRIPT_PAGE_URL";

    private static final String FAVORITE_IMAGE_URL_PARAMETER = Constants
            .getPackagePrefix()
            + ".FAVORITE_IMAGE_URL";

    private static final String MULTI_WINDOW_FILENAME = "f_multiWindow.js";

    public static final String MULTI_WINDOW_PARAMETER = Constants
            .getPackagePrefix()
            + ".MULTI_WINDOW_MODE";

    private static final String NONE_IMAGE_URL = "none";

    private static final String INVALID_BROWSER_PAGE_URL_PARAMETER = Constants
            .getPackagePrefix()
            + ".INVALID_BROWSER_PAGE_URL";

    public static final Object META_CONTENT_TYPE_PARAMETER = Constants
            .getPackagePrefix()
            + ".META_CONTENT_TYPE_PARAMETER";

    public static final Object CLIENT_MESSAGE_ID_FILTER_PARAMETER = Constants
            .getPackagePrefix()
            + ".CLIENT_MESSAGE_ID_FILTER";

    private static final String APPLICATION_PARAMETERS_PROPERTY = "org.rcfaces.renderkit.html.internal.taglib.InitializeTag.APPLICATION_PARAMETERS";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        InitComponent initComponent = (InitComponent) writer
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        IHtmlProcessContext htmlProcessContext = (IHtmlProcessContext) writer
                .getComponentRenderContext().getRenderContext()
                .getProcessContext();
        ApplicationParameters appParams = getApplicationParameters(htmlProcessContext);

        String invalidBrowserPageURL = initComponent
                .getInvalidBrowserPageURL(facesContext);
        if ("false".equals(invalidBrowserPageURL)) {
            invalidBrowserPageURL = null;

        } else if (invalidBrowserPageURL == null) {
            invalidBrowserPageURL = appParams.invalidBrowserPageURL;
        }

        boolean disableIEImageBar = initComponent
                .isDisabledIEImageBar(facesContext);
        if (disableIEImageBar == false) {
            disableIEImageBar = appParams.disableIEImageBar;
        }

        boolean disableContextMenu = initComponent
                .isDisableContextMenu(facesContext);
        if (disableContextMenu == false) {
            disableContextMenu = appParams.disableContextMenu;
        }

        boolean disableCache = initComponent.isDisableCache(facesContext);
        if (disableCache == false) {
            disableCache = appParams.disableCache;
        }

        boolean lockedClientAttributesSetted = false;
        if (lockedClientAttributesSetted) {
            // AbstractRequestContext.setLockedAttributes(facesContext,lockedClientAttributes);
        }

        // Pour optimiser ....
        PageConfiguration.setPageConfigurator(facesContext, initComponent);

        if (disableCache) {
            disableCache(htmlWriter);
        }

        if (appParams.metaContentType) {
            ServletResponse response = (ServletResponse) facesContext
                    .getExternalContext().getResponse();
            String contentType = null;

            try {
                // getContentType appears into 2.4 spec
                Method getContentTypeMethod = response.getClass().getMethod(
                        "getContentType", null);

                contentType = (String) getContentTypeMethod.invoke(response,
                        null);

            } catch (Throwable ex) {
                LOG.debug("Can not get contentType of response object !", ex);
            }

            if (contentType != null) {
                htmlWriter.startElement("META");
                htmlWriter.writeHttpEquiv("Content-Type", contentType);
                htmlWriter.endElement("META");
            }
        }

        if (htmlProcessContext.useMetaContentScriptType()) {
            htmlWriter.startElement("META");
            htmlWriter.writeHttpEquiv("Content-Script-Type",
                    IHtmlRenderContext.JAVASCRIPT_TYPE);
            htmlWriter.endElement("META");
        }

        if (htmlProcessContext.useMetaContentStyleType()) {
            htmlWriter.startElement("META");
            htmlWriter.writeHttpEquiv("Content-Style-Type",
                    IHtmlRenderContext.CSS_TYPE);
            htmlWriter.endElement("META");
        }

        if (disableIEImageBar) {
            // Desactive la toolbar Image de IE !
            htmlWriter.startElement("META");
            htmlWriter.writeHttpEquiv("imagetoolbar", "no");
            htmlWriter.endElement("META");
        }

        HtmlRenderContext htmlRenderContext = (HtmlRenderContext) htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();
        if (disableContextMenu) {
            htmlRenderContext.setDisabledContextMenu(true);
        }

        if (appParams.clientMessageIdFilter != null) {
            htmlRenderContext
                    .setClientMessageId(appParams.clientMessageIdFilter);
        }
        String clientMessageIdFilter = initComponent
                .getClientMessageIdFilter(facesContext);
        if (clientMessageIdFilter != null
                && clientMessageIdFilter.trim().length() > 0) {
            Set clientMessageIds = parseClientMessageIdFilter(clientMessageIdFilter);

            htmlRenderContext.addClientMessageIds(clientMessageIds);
        }

        String base = initComponent.getBase(facesContext);
        if (base != null) {
            boolean renderBaseTag = initComponent.isRenderBaseTag(facesContext);
            if (renderBaseTag) {
                htmlWriter.startElement("BASE"); // ("<BASE href=\"");
            }

            if (base.startsWith("$context")) {
                StringAppender sa = new StringAppender(facesContext
                        .getExternalContext().getRequestContextPath(), base
                        .length());

                if (base.length() >= 8) {
                    sa.append(base.substring(8));

                } else {
                    sa.append('/');
                }

                base = sa.toString();
            }
            htmlProcessContext.changeBaseHREF(base);

            if (renderBaseTag) {
                StringAppender sa = new StringAppender(256);

                ServletRequest request = (ServletRequest) facesContext
                        .getExternalContext().getRequest();
                String scheme = request.getScheme();
                if (scheme != null) {
                    sa.append(scheme).append(":\\").append(
                            request.getServerName());

                    int port = request.getServerPort();
                    if (port == 80 && "http".equals(scheme)) {
                        port = -1;

                    } else if (port == 443 && "https".equals(scheme)) {
                        port = -1;
                    }

                    if (port > 0) {
                        sa.append(':').append(port);
                    }
                }

                sa.append(base);

                if (base.endsWith("/") == false) {
                    sa.append('/');
                }

                htmlWriter.writeSrc(sa.toString());

                htmlWriter.endElement("BASE");
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Set BASE href='" + base + "'.");
            }
        }

        String favoriteImageURL = initComponent
                .getFavoriteImageURL(facesContext);
        if (favoriteImageURL == null) {
            favoriteImageURL = appParams.favoriteImageURL;
        }
        if (favoriteImageURL != null) {
            writeFavoriteImageURL(htmlWriter, favoriteImageURL);
        }

        ICssConfig cssConfig = StylesheetsServlet.getConfig(htmlProcessContext);
        if (cssConfig != null) {
            htmlWriter.startElement("LINK");
            htmlWriter.writeRel("stylesheet");
            if (htmlProcessContext.useMetaContentStyleType() == false) {
                htmlWriter.writeType("text/css");
            }

            String styleSheetURI = htmlProcessContext.getStyleSheetURI(
                    cssConfig.getStyleSheetFileName(), true);

            htmlWriter.writeHRef(styleSheetURI);

            htmlWriter.endElement("LINK");
        }

        String disabledScriptPageURL = initComponent
                .getDisabledScriptPageURL(facesContext);
        if ("false".equals(disabledScriptPageURL)) {
            disabledScriptPageURL = null;

        } else if (disabledScriptPageURL == null) {
            disabledScriptPageURL = appParams.disabledScriptPageURL;
        }
        if (disabledScriptPageURL != null) {
            writeDisabledScriptPageURL(htmlWriter, disabledScriptPageURL);
        }

        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(facesContext);
        IHierarchicalRepository.ISet bootSet = repository.getBootSet();
        if (bootSet == null) {
            throw new WriterException("BootSet must be defined !", null,
                    initComponent);
        }

        IRepository.IContext repositoryContext = JavaScriptRepositoryServlet
                .getContextRepository(facesContext);

        if (repositoryContext.add(bootSet)) {
            // Il n'est pas connu du repository !

            String cameliaScriptURI = bootSet.getURI(repositoryContext
                    .getLocale());

            String jsBaseURI = repository.getBaseURI(htmlProcessContext);

            String disabledCookiesPageURL = initComponent
                    .getDisabledCookiesPageURL(facesContext);
            if ("false".equals(disabledCookiesPageURL)) {
                disabledCookiesPageURL = null;

            } else if (disabledCookiesPageURL == null) {
                disabledCookiesPageURL = appParams.disabledCookiesPageURL;
            }

            if (appParams.multiWindowScript) {
                writeScriptTag_multiWindow(htmlWriter, cameliaScriptURI,
                        jsBaseURI, repository, repositoryContext,
                        disabledCookiesPageURL);

            } else {
                writeScriptTag(htmlWriter, cameliaScriptURI, jsBaseURI,
                        disabledCookiesPageURL);
            }
        }

        if (invalidBrowserPageURL != null) {
            initializeJavaScript(htmlWriter, appParams, invalidBrowserPageURL);
        }

        String title = initComponent.getTitle(facesContext);
        if (title != null) {
            writeTitle(htmlWriter, title);
        }

        HtmlRenderContext.setMetaDataInitialized(facesContext);

        super.encodeEnd(writer);
    }

    private void initializeJavaScript(IHtmlWriter writer,
            ApplicationParameters appParams, String invalidBrowserPageURL)
            throws WriterException {

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(facesContext);
        if (repository == null) {
            LOG.error("JavaScript repository is not created yet !");
            return;
        }

        HtmlRenderContext htmlRenderContext = (HtmlRenderContext) writer
                .getHtmlComponentRenderContext().getHtmlRenderContext();
        if (invalidBrowserPageURL != null) {
            htmlRenderContext.setInvalidBrowserURL(invalidBrowserPageURL);
        }

        // IJavaScriptWriter jsWriter = new
        // JavaScriptWriterImpl(facesContext,appParams.symbols, writer);

        IJavaScriptWriter jsWriter = openScriptTag(writer);

        JavaScriptRenderContext.initializeJavaScript(jsWriter, repository);

        jsWriter.end();
    }

    private static synchronized ApplicationParameters getApplicationParameters(
            IHtmlProcessContext htmlProcessContext) {

        Map applicationMap = htmlProcessContext.getFacesContext()
                .getExternalContext().getApplicationMap();
        ApplicationParameters appParams = (ApplicationParameters) applicationMap
                .get(APPLICATION_PARAMETERS_PROPERTY);
        if (appParams != null) {
            return appParams;
        }

        appParams = new ApplicationParameters(htmlProcessContext);

        applicationMap.put(APPLICATION_PARAMETERS_PROPERTY, appParams);

        return appParams;
    }

    private void disableCache(IHtmlWriter writer) throws WriterException {

        try {
            ServletResponse servletResponse = (ServletResponse) writer
                    .getComponentRenderContext().getFacesContext()
                    .getExternalContext().getResponse();

            if (servletResponse instanceof HttpServletResponse) {
                ConfiguredHttpServlet
                        .setNoCache((HttpServletResponse) servletResponse);
            }

        } catch (Throwable th) {
            LOG.debug("Too late to specify NO-CACHE into HttpResponse !", th);
        }

        writer.startElement("META");
        writer.writeHttpEquiv("cache-control", "no-cache");
        writer.endElement("META");

        writer.startElement("META");
        writer.writeHttpEquiv("pragma", "no-cache");
        writer.endElement("META");

        writer.startElement("META");
        writer.writeHttpEquiv("expires", "0");
        writer.endElement("META");
    }

    private void writeFavoriteImageURL(IHtmlWriter writer,
            String favoriteImageURL) throws WriterException {

        if (NONE_IMAGE_URL.equals(favoriteImageURL)) {
            return;
        }

        if (favoriteImageURL == null || favoriteImageURL.length() < 1) {
            return;
        }

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        ImageContentInformation favoriteImageOperation = new ImageContentInformation();

        IContentAccessor favoriteContentAccessor = ContentAccessorFactory
                .createFromWebResource(facesContext, favoriteImageURL,
                        IContentType.IMAGE);

        favoriteImageURL = favoriteContentAccessor.resolveURL(facesContext,
                favoriteImageOperation, null);

        if (favoriteImageURL == null) {
            return;
        }

        ImageContentInformation favoriteIcoImageInformation = new ImageContentInformation();

        IContentAccessor favoriteIcoContentAccessor = ContentAccessorFactory
                .createFromWebResource(null, IEFavoriteIconOperation.ID
                        + IContentAccessor.FILTER_SEPARATOR,
                        favoriteContentAccessor);

        String favoriteIcoImageURL = favoriteIcoContentAccessor.resolveURL(
                facesContext, favoriteIcoImageInformation, null);

        if (favoriteIcoImageURL != null) {
            writer.startElement("LINK");
            writer.writeRel("SHORTCUT ICON");

            if (favoriteIcoImageInformation != null) {
                String favoriteIcoMimeType = favoriteIcoImageInformation
                        .getContentType();
                if (favoriteIcoMimeType != null) {
                    writer.writeType(favoriteIcoMimeType);
                }
            }

            writer.writeHRef(favoriteIcoImageURL);

            writer.endElement("LINK");
        }

        if (favoriteImageURL != null) {
            writer.startElement("LINK");
            writer.writeRel("ICON");
            if (favoriteImageOperation != null) {
                String favoriteMimeType = favoriteImageOperation
                        .getContentType();
                if (favoriteMimeType != null) {
                    writer.writeType(favoriteMimeType);
                }
            }
            writer.writeHRef(favoriteImageURL);

            writer.endElement("LINK");
        }

    }

    private void writeTitle(IHtmlWriter writer, String title)
            throws WriterException {

        if (title == null || title.length() < 1) {
            return;
        }

        writer.startElement("TITLE");

        writer.write(title);

        writer.endElement("TITLE");
    }

    private void writeDisabledScriptPageURL(IHtmlWriter writer,
            String disabledScriptPageURL) throws WriterException {

        if (disabledScriptPageURL == null || disabledScriptPageURL.length() < 1) {
            return;
        }

        writer.startElement("NOSCRIPT");

        writer.startElement("META");
        writer.writeHttpEquiv("Refresh", "0; URL=" + disabledScriptPageURL);
        writer.endElement("META");

        writer.endElement("NOSCRIPT");
    }

    private void writeScriptTag(IHtmlWriter writer, String uri,
            String jsBaseURI, String disabledCookiesPageURL)
            throws WriterException {

        if (disabledCookiesPageURL != null) {
            IJavaScriptWriter jsWriter = openScriptTag(writer);
            writeCookieTest(jsWriter, disabledCookiesPageURL);
            jsWriter.end();
        }

        includeScript(writer, jsBaseURI + "/" + uri,
                IHtmlRenderContext.JAVASCRIPT_CHARSET);
    }

    private void writeCookieTest(IJavaScriptWriter writer,
            String disabledCookiesPageURL) throws WriterException {
        writer.write("if (!navigator.cookieEnabled) document.location=");
        writer.write(disabledCookiesPageURL);
        writer.writeln(";");
    }

    private void writeScriptTag_multiWindow(IHtmlWriter writer, String uri,
            String jsBaseURI, IHierarchicalRepository repository,
            IContext repositoryContext, String disabledCookiesPageURL)
            throws WriterException {

        IHtmlProcessContext htmlProcessContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getHtmlProcessContext();

        String javascriptCharset = IHtmlRenderContext.JAVASCRIPT_CHARSET;

        IJavaScriptWriter jsWriter = openScriptTag(writer);

        // new JavaScriptTag.JavaScriptWriterImpl( facesContext, symbols,
        // writer);

        if (disabledCookiesPageURL != null) {
            writeCookieTest(jsWriter, disabledCookiesPageURL);
        }

        jsWriter.write("for(var cl,v=window;v && !cl;v=v.opener)try{ cl=v.");
        jsWriter.writeSymbol("_classLoader");
        jsWriter.writeln("}catch(x){}");
        jsWriter.write("if (!cl && top) cl=top.");
        jsWriter.writeSymbol("_classLoader");
        jsWriter.writeln(";");
        jsWriter.write("if (cl) cl.");
        jsWriter.writeSymbol("_newWindow");
        jsWriter.write(".call(window, cl, function(x){return eval(x)});");
        jsWriter.write(" else document.write(\"<SCRIPT");
        if (htmlProcessContext.useMetaContentScriptType() == false) {
            jsWriter.write(" type=\\\"");
            jsWriter.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            jsWriter.write("\\\"");
        }
        jsWriter.write(" src=\\\"");
        String u = jsBaseURI + "/" + uri;
        jsWriter.write(u);
        jsWriter.write("\\\"");

        if (javascriptCharset != null) {
            jsWriter.write(" charset=\\\"");
            jsWriter.write(javascriptCharset);
            jsWriter.write("\\\"");
        }
        jsWriter.write("></\"+\"SCRIPT>");

        List l = Collections.singletonList(MULTI_WINDOW_FILENAME);

        IRepository.IFile files[] = repository.computeFiles(l,
                IHierarchicalRepository.FILENAME_COLLECTION_TYPE,
                repositoryContext);
        if (files != null && files.length > 0) {
            Locale locale = repositoryContext.getLocale();
            for (int i = 0; i < files.length; i++) {
                jsWriter.write("<SCRIPT");
                if (htmlProcessContext.useMetaContentScriptType() == false) {
                    jsWriter.write(" type=\\\"");
                    jsWriter.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
                    jsWriter.write("\\\"");
                }
                jsWriter.write(" src=\\\"");

                u = jsBaseURI + "/" + files[i].getURI(locale);

                jsWriter.write(u);
                jsWriter.write("\\\"");
                if (javascriptCharset != null) {
                    jsWriter.write(" charset=\\\"");
                    jsWriter.write(javascriptCharset);
                    jsWriter.write("\\\"");
                }
                jsWriter.write("></\"+\"SCRIPT>");
            }
        }

        jsWriter.writeln("\");");
        jsWriter.end();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ApplicationParameters implements Serializable {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = 491523571265962718L;

        boolean metaContentType;

        boolean disableContextMenu;

        boolean disableIEImageBar;

        boolean multiWindowScript;

        boolean disableCache;

        Map symbols;

        String disabledCookiesPageURL;

        String disabledScriptPageURL;

        String invalidBrowserPageURL;

        String favoriteImageURL;

        Set clientMessageIdFilter;

        private boolean symbolsInitialized;

        public ApplicationParameters() {
        }

        private ApplicationParameters(IHtmlProcessContext htmlProcessContext) {
            initialize(htmlProcessContext);
        }

        private void initialize(IHtmlProcessContext htmlProcessContext) {
            FacesContext facesContext = htmlProcessContext.getFacesContext();

            ExternalContext externalContext = facesContext.getExternalContext();

            Map initParameters = externalContext.getInitParameterMap();

            String param = (String) initParameters
                    .get(DISABLE_IE_IMAGE_BAR_PARAMETER);
            if ("false".equalsIgnoreCase(param)) {
                disableIEImageBar = false;

            } else if ("true".equalsIgnoreCase(param)) {
                disableIEImageBar = true;

            } else {
                disableIEImageBar = org.rcfaces.renderkit.html.internal.Constants.DISABLE_IE_IMAGEBAR_DEFAULT_VALUE;
            }

            disableContextMenu = "true".equals(initParameters
                    .get(DISABLE_CONTEXT_MENU_PARAMETER));

            disableCache = "true".equals(initParameters
                    .get(DISABLE_CACHE_PARAMETER));

            disabledCookiesPageURL = (String) initParameters
                    .get(DISABLED_COOKIES_PAGE_URL_PARAMETER);
            if (disabledCookiesPageURL != null
                    && disabledCookiesPageURL.trim().length() < 1) {
                disabledCookiesPageURL = null;
            }

            disabledScriptPageURL = (String) initParameters
                    .get(DISABLED_SCRIPT_PAGE_URL_PARAMETER);
            if (disabledScriptPageURL != null
                    && disabledScriptPageURL.trim().length() < 1) {
                disabledScriptPageURL = null;
            }

            invalidBrowserPageURL = (String) initParameters
                    .get(INVALID_BROWSER_PAGE_URL_PARAMETER);
            if (invalidBrowserPageURL != null
                    && invalidBrowserPageURL.trim().length() < 1) {
                invalidBrowserPageURL = null;
            }

            favoriteImageURL = (String) initParameters
                    .get(FAVORITE_IMAGE_URL_PARAMETER);
            if (favoriteImageURL != null
                    && favoriteImageURL.trim().length() < 1) {
                favoriteImageURL = null;
            }

            metaContentType = true;
            if ("false".equalsIgnoreCase((String) initParameters
                    .get(META_CONTENT_TYPE_PARAMETER))) {
                metaContentType = false;
            }

            multiWindowScript = "true".equalsIgnoreCase((String) initParameters
                    .get(MULTI_WINDOW_PARAMETER));

            symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);

            String clientMessageIdFilterParam = (String) initParameters
                    .get(CLIENT_MESSAGE_ID_FILTER_PARAMETER);

            if (clientMessageIdFilterParam != null) {
                clientMessageIdFilter = parseClientMessageIdFilter(clientMessageIdFilterParam);
            }

            if (LOG.isInfoEnabled()) {

                if (disableIEImageBar) {
                    LOG.info("DisableIEImageBar is enabled for context.");
                }

                if (disableContextMenu) {
                    LOG.info("DisableContextMenu is enabled for context.");
                }

                if (multiWindowScript) {
                    LOG.info("MultiWindowScript is enabled for context.");
                }

                if (metaContentType) {
                    LOG.info("MetaContentType is enabled for context.");
                }

                if (htmlProcessContext.useMetaContentScriptType()) {
                    LOG
                            .info("UseMetaContentScriptType is enabled for context.");
                }

                if (htmlProcessContext.useMetaContentStyleType()) {
                    LOG.info("UseMetaContentStyleType is enabled for context.");
                }

                if (htmlProcessContext.getDebugMode()) {
                    LOG.info("DEBUG_MODE is enabled for context.");
                }

                if (htmlProcessContext.getProfilerMode()) {
                    LOG.info("PROFILER_MODE is enabled for context.");
                }

                if (htmlProcessContext.isDesignerMode()) {
                    LOG.info("DESIGNER_MODE is enabled for context.");
                }
            }
        }
    }

    static IJavaScriptWriter openScriptTag(IHtmlWriter writer)
            throws WriterException {

        IHtmlProcessContext htmlProcessContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getHtmlProcessContext();

        writer.startElement("SCRIPT");
        if (htmlProcessContext.useMetaContentScriptType() == false) {
            writer.writeType(IHtmlRenderContext.JAVASCRIPT_TYPE);
        }

        return new JavaScriptWriterImpl(writer);
    }

    static void includeScript(IHtmlWriter writer, String src,
            String javascriptCharset) throws WriterException {

        IHtmlProcessContext htmlProcessContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getHtmlProcessContext();

        writer.startElement("SCRIPT");

        if (htmlProcessContext.useMetaContentScriptType() == false) {
            writer.writeType(IHtmlRenderContext.JAVASCRIPT_TYPE);
        }

        writer.writeSrc(src);

        if (javascriptCharset != null) {
            writer.writeCharset(javascriptCharset);
        }

        writer.endElement("SCRIPT");
    }

    static Set parseClientMessageIdFilter(String filter) {
        Set set = null;

        StringTokenizer st = new StringTokenizer(filter, ", ");
        for (; st.hasMoreTokens();) {
            if (set == null) {
                set = new HashSet(st.countTokens());
            }

            String clientId = st.nextToken();

            set.add(clientId);
        }

        return set;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    static class JavaScriptWriterImpl extends AbstractJavaScriptWriter {
        private static final String REVISION = "$Revision$";

        private final IHtmlWriter writer;

        private boolean initialized = false;

        private boolean rawText = false;

        private Map symbols;

        public JavaScriptWriterImpl(IHtmlWriter writer) {
            this.writer = writer;
        }

        protected String convertSymbol(String symbol) {
            if (symbols == null) {
                symbols = JavaScriptRepositoryServlet.getSymbols(writer
                        .getHtmlComponentRenderContext().getFacesContext());
                if (symbols == null) {
                    symbols = Collections.EMPTY_MAP;
                }
            }

            String compacted = (String) symbols.get(symbol);
            if (compacted != null) {
                return compacted;
            }

            return symbol;
        }

        public IJavaScriptRenderContext getJavaScriptRenderContext() {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public String getComponentVarName() {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public void setComponentVarName(String varName) {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public IHtmlComponentRenderContext getHtmlComponentRenderContext() {
            return writer.getHtmlComponentRenderContext();
        }

        public IHtmlWriter getWriter() {
            return writer;
        }

        public FacesContext getFacesContext() {
            return writer.getComponentRenderContext().getFacesContext();
        }

        public IJavaScriptWriter ensureInitialization() {
            return this;
        }

        public IJavaScriptWriter write(String string) throws WriterException {

            if (initialized == false) {
                initializeRaw();
            }

            try {
                writer.write(string);

            } catch (IOException e) {
                throw new WriterException("Can not write '" + string + "'.", e,
                        null);
            }
            return this;
        }

        public IJavaScriptWriter write(char c) throws WriterException {

            if (initialized == false) {
                initializeRaw();
            }

            try {
                writer.write(c);
                return this;

            } catch (IOException e) {
                throw new WriterException("Can not write char '" + c + "'.", e,
                        null);
            }
        }

        public IJavaScriptWriter writeRaw(char[] dst, int pos, int length)
                throws WriterException {

            if (initialized == false) {
                initializeRaw();
            }

            try {
                writer.write(dst, pos, length);

            } catch (IOException e) {
                throw new WriterException("Can not write buffer.", e, null);
            }
            return null;
        }

        public void addRequestedModule(String moduleName) {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public String allocateString(String text) {
            throw new UnsupportedOperationException("Not implemented !");
        }

        protected void initializeRaw() throws WriterException {
            initialized = true;

            IHtmlProcessContext htmlProcessContext = writer
                    .getHtmlComponentRenderContext().getHtmlRenderContext()
                    .getHtmlProcessContext();

            if (htmlProcessContext.useScriptCData() == false) {
                return;
            }

            rawText = true;

            writer.write(IHtmlRenderContext.JAVASCRIPT_CDATA_BEGIN);

            writer.writeln();
        }

        public void end() throws WriterException {
            if (rawText) {
                writer.write(IHtmlRenderContext.JAVASCRIPT_CDATA_END);
            }

            writer.endElement("SCRIPT");
        }

        public boolean isOpened() {
            return true;
        }

        protected void isInitialized() {
        }

    }

}
