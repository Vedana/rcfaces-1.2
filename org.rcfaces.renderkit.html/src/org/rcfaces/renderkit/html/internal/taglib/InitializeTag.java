/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.taglib;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.codec.URLFormCodec;
import org.rcfaces.core.internal.images.ImageOperationsURLRewritingProvider;
import org.rcfaces.core.internal.images.operation.IEFavoriteIconOperation;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.rewriting.AbstractURLRewritingProvider;
import org.rcfaces.core.internal.taglib.AbstractInitializeTag;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IContext;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.core.provider.ImageURLRewritingInformation;
import org.rcfaces.renderkit.html.internal.HtmlProcessContextImpl;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.codec.HTMLCodec;
import org.rcfaces.renderkit.html.internal.css.ICssConfig;
import org.rcfaces.renderkit.html.internal.css.StylesheetsServlet;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;
import org.rcfaces.renderkit.html.internal.taglib.JavaScriptTag.JavaScriptWriterImpl;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class InitializeTag extends AbstractInitializeTag implements Tag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 8729943902620288238L;

    private static final Log LOG = LogFactory.getLog(InitializeTag.class);

    private static final String META_DATA_INITIALIZED_PROPERTY = "org.rcfaces.renderkit.html.internal.taglib.InitializeTag.META_DATA_INITIALIZED";

    private static final String INVALID_BROWSER_URL_PROPERTY = "org.rcfaces.renderkit.html.internal.taglib.InitializeTag.INVALID_BROWSER_URL";

    private static final String DISABLE_IE_IMAGE_BAR_PARAMETER = Constants
            .getPackagePrefix()
            + ".DISABLE_IE_IMAGE_BAR";

    private static final String DISABLE_CONTEXT_MENU_PARAMETER = Constants
            .getPackagePrefix()
            + ".DISABLE_CONTEXT_MENU";

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

    private static final String DISABLE_CONTEXT_MENU_PROPERTY = "org.rcfaces.renderkit.html.internal.taglib.InitializeTag.DISABLE_CONTEXT_MENU";

    private static final String APPLICATION_PARAMETERS_PROPERTY = "org.rcfaces.renderkit.html.internal.taglib.InitializeTag.APPLICATION_PARAMETERS";

    private boolean disableIEImageBar = false;

    private boolean disableContextMenu = false;

    private boolean disableCache = false;

    private boolean renderBaseTag = true;

    private String base;

    private String favoriteImageURL;

    private String title;

    private String disabledCookiesPageURL;

    private String disabledScriptPageURL;

    private String invalidBrowserPageURL;

    private IHtmlProcessContext htmlProcessContext;

    public int doStartTag() throws JspException {

        int ret = super.doStartTag();

        JspWriter writer = pageContext.getOut();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = pageContext.getServletContext();

        htmlProcessContext = (IHtmlProcessContext) getProcessContext();
        ApplicationParameters appParams = getApplicationParameters(htmlProcessContext);

        if ("false".equals(disabledCookiesPageURL)) {
            disabledCookiesPageURL = null;

        } else if (disabledCookiesPageURL == null) {
            disabledCookiesPageURL = appParams.disabledCookiesPageURL;
        }

        if ("false".equals(disabledScriptPageURL)) {
            disabledScriptPageURL = null;

        } else if (disabledScriptPageURL == null) {
            disabledScriptPageURL = appParams.disabledScriptPageURL;
        }

        if ("false".equals(invalidBrowserPageURL)) {
            invalidBrowserPageURL = null;

        } else if (invalidBrowserPageURL == null) {
            invalidBrowserPageURL = appParams.invalidBrowserPageURL;
        }

        if (disableIEImageBar == false) {
            disableIEImageBar = appParams.disableIEImageBar;
        }

        if (disableContextMenu == false) {
            disableContextMenu = appParams.disableContextMenu;
        }

        setScriptType(IHtmlRenderContext.JAVASCRIPT_TYPE);

        try {

            if (disableCache) {
                disableCache(writer);
            }

            if (htmlProcessContext.useMetaContentScriptType()) {
                writer
                        .print("<META http-equiv=\"Content-Script-Type\" content=\"");
                writer.print(IHtmlRenderContext.JAVASCRIPT_TYPE);
                // writer.print("; charset=UTF-8");
                writer.println("\" />");

            }

            if (htmlProcessContext.useMetaContentStyleType()) {
                writer
                        .print("<META http-equiv=\"Content-Style-Type\" content=\"");
                writer.print(IHtmlRenderContext.CSS_TYPE);
                // writer.print("; charset=UTF-8");
                writer.println("\" />");
            }

            if (disableIEImageBar) {
                // Desactive la toolbar Image de IE !
                writer
                        .println("<META http-equiv=\"imagetoolbar\" content=\"no\" />");
            }

            if (disableContextMenu) {
                facesContext.getExternalContext().getRequestMap().put(
                        DISABLE_CONTEXT_MENU_PROPERTY, Boolean.TRUE);
            }

            if (base != null) {
                if (renderBaseTag) {
                    writer.print("<BASE href=\"");
                }

                if ("context".equals(base)) {
                    String contextPath = facesContext.getExternalContext()
                            .getRequestContextPath();

                    contextPath += "/";

                    htmlProcessContext.changeBaseHREF(contextPath);
                    base = contextPath;

                } else {
                    htmlProcessContext.changeBaseHREF(base);
                }

                if (renderBaseTag) {
                    URLFormCodec.writeURL(writer, base);
                    if (base.endsWith("/") == false) {
                        writer.print('/');
                    }
                    writer.println("\" />");
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Set BASE href='" + base + "'.");
                }
            }

            if (favoriteImageURL == null) {
                favoriteImageURL = servletContext
                        .getInitParameter(FAVORITE_IMAGE_URL_PARAMETER);
            }
            if (favoriteImageURL != null) {
                writeFavoriteImageURL(writer, facesContext);
            }

            ICssConfig cssConfig = StylesheetsServlet
                    .getConfig(htmlProcessContext);
            if (cssConfig != null) {
                writer.print("<LINK rel=\"stylesheet\"");
                if (htmlProcessContext.useMetaContentStyleType() == false) {
                    writer.print(" type=\"text/css\"");
                }

                String styleSheetURI = htmlProcessContext
                        .getStyleSheetURI(cssConfig.getStyleSheetFileName());

                writer.print(" href=\"");
                URLFormCodec.writeURL(writer, styleSheetURI);
                writer.println("\" />");
            }

            if (disabledScriptPageURL != null) {
                writeDisabledScriptPageURL(facesContext, writer);
            }

            IJavaScriptRepository repository = JavaScriptRepositoryServlet
                    .getRepository(facesContext);
            IHierarchicalRepository.ISet bootSet = repository.getBootSet();
            if (bootSet == null) {
                throw new JspException("BootSet must be defined !");
            }

            IRepository.IContext repositoryContext = JavaScriptRepositoryServlet
                    .getContextRepository(facesContext);

            if (repositoryContext.add(bootSet)) {
                // Il n'est pas connu du repository !

                String cameliaScriptURI = bootSet.getURI(repositoryContext
                        .getLocale());

                String jsBaseURI = repository.getBaseURI(htmlProcessContext);

                if (appParams.multiWindowScript) {
                    writeScriptTag_multiWindow(facesContext, writer,
                            cameliaScriptURI, jsBaseURI, repository,
                            repositoryContext);

                } else {
                    writeScriptTag(facesContext, writer, cameliaScriptURI,
                            jsBaseURI);
                }
            }

            if (invalidBrowserPageURL != null) {
                initializeJavaScript(facesContext, writer, htmlProcessContext,
                        appParams);
            }

            if (title != null) {
                writeTitle(writer, facesContext);
            }

        } catch (IOException e) {
            throw new JspException(e);
        }

        metaDataInitialized();

        return ret;
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

    private void initializeJavaScript(FacesContext facesContext,
            JspWriter writer, IHtmlProcessContext processContext,
            ApplicationParameters appParams) throws IOException {

        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(facesContext);
        if (repository == null) {
            LOG.error("JavaScript repository is not created yet !");
            return;
        }

        IJavaScriptWriter jsWriter = new JavaScriptWriterImpl(facesContext,
                appParams.symbols, writer);

        pageContext.getRequest().setAttribute(INVALID_BROWSER_URL_PROPERTY,
                invalidBrowserPageURL);

        JavaScriptTag.openScriptTag(writer, htmlProcessContext);

        JavaScriptRenderContext.initializeJavaScript(jsWriter, repository,
                processContext);

        JavaScriptTag.closeScriptTag(writer, htmlProcessContext);
    }

    protected IProcessContext initializeExternalContext(
            FacesContext facesContext) {
        htmlProcessContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        return htmlProcessContext;
    }

    private void disableCache(JspWriter writer) throws IOException {

        try {
            ServletResponse servletResponse = pageContext.getResponse();

            if (servletResponse instanceof HttpServletResponse) {
                ConfiguredHttpServlet
                        .setNoCache((HttpServletResponse) servletResponse);
            }

        } catch (Throwable th) {
            LOG.debug("Too late to specify NO-CACHE into HttpResponse !", th);
        }

        writer
                .println("<META http-equiv=\"cache-control\" content=\"no-cache\" />");
        writer.println("<META http-equiv=\"pragma\" content=\"no-cache\" />");
        writer.println("<META http-equiv=\"expires\" content=\"0\" /> ");

    }

    private void writeFavoriteImageURL(JspWriter writer,
            FacesContext facesContext) throws IOException {

        if (NONE_IMAGE_URL.equals(favoriteImageURL)) {
            return;
        }

        if (UIComponentTag.isValueReference(favoriteImageURL)) {
            Application application = facesContext.getApplication();

            ValueBinding vb = application.createValueBinding(favoriteImageURL);

            Object value = vb.getValue(facesContext);

            if (value == null) {
                return;
            }

            favoriteImageURL = String.valueOf(value);
        }

        if (favoriteImageURL == null || favoriteImageURL.length() < 1) {
            return;
        }

        String original = favoriteImageURL;
        IURLRewritingProvider urlRewritingProvider = null;
        ImageURLRewritingInformation favoriteImageOperation = null;
        if (Constants.URL_REWRITING_SUPPORT) {
            urlRewritingProvider = AbstractURLRewritingProvider
                    .getInstance(facesContext.getExternalContext());

            if (urlRewritingProvider != null) {
                favoriteImageOperation = new ImageURLRewritingInformation();

                favoriteImageURL = urlRewritingProvider.computeURL(
                        facesContext, null,
                        IURLRewritingProvider.IMAGE_URL_TYPE,
                        "favoriteImageURL", original, null,
                        favoriteImageOperation);

                if (favoriteImageURL == null) {
                    return;
                }
            }
        }

        String favoriteIcoImageURL = null;
        ImageURLRewritingInformation favoriteIcoImageInformation = null;
        if (urlRewritingProvider != null) {
            favoriteIcoImageInformation = new ImageURLRewritingInformation(
                    favoriteImageOperation);

            favoriteIcoImageURL = urlRewritingProvider
                    .computeURL(
                            facesContext,
                            null,
                            IURLRewritingProvider.IMAGE_URL_TYPE,
                            "favoriteImageURL",
                            // IURLRewritingProvider.URL_REWRITING_PROVIDER_ID
                            IEFavoriteIconOperation.ID
                                    + ImageOperationsURLRewritingProvider.URL_REWRITING_SEPARATOR,
                            original, favoriteIcoImageInformation);
        }

        if (favoriteIcoImageURL != null) {
            writer.print("<LINK rel=\"SHORTCUT ICON\"");

            if (favoriteIcoImageInformation != null) {
                String favoriteIcoMimeType = favoriteIcoImageInformation
                        .getContentType();
                if (favoriteIcoMimeType != null) {
                    writer.print(" type=\"");
                    writer.print(favoriteIcoMimeType);
                    writer.print('\"');
                }
            }

            writer.print(" href=\"");
            URLFormCodec.writeURL(writer, favoriteIcoImageURL);
            writer.println("\" />");
        }

        if (favoriteImageURL != null) {
            writer.print("<LINK rel=\"ICON\"");
            if (favoriteImageOperation != null) {
                String favoriteMimeType = favoriteImageOperation
                        .getContentType();
                if (favoriteMimeType != null) {
                    writer.print(" type=\"");
                    writer.print(favoriteMimeType);
                    writer.print('\"');
                }
            }
            writer.print(" href=\"");
            URLFormCodec.writeURL(writer, favoriteImageURL);
            writer.println("\" />");
        }

    }

    private void writeTitle(JspWriter writer, FacesContext facesContext)
            throws IOException {

        if (UIComponentTag.isValueReference(title)) {
            Application application = facesContext.getApplication();

            ValueBinding vb = application.createValueBinding(title);

            Object value = vb.getValue(facesContext);

            if (value == null) {
                return;
            }

            title = String.valueOf(value);
        }

        if (title == null || title.length() < 1) {
            return;
        }

        writer.print("<TITLE>");

        title = HTMLCodec.convertUTF8ToHTML(title);
        writer.print(title);

        writer.println("</TITLE>");
    }

    protected void initializeTag() {
    }

    private void writeScriptTag(FacesContext facesContext, JspWriter writer,
            String uri, String jsBaseURI) throws IOException {

        String javascriptCharset = IHtmlRenderContext.JAVASCRIPT_CHARSET;

        if (disabledCookiesPageURL != null) {
            writer.print("<SCRIPT");
            if (htmlProcessContext.useMetaContentScriptType() == false) {
                writer.write(" type=\"");
                writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
                writer.write('"');
            }

            if (javascriptCharset != null) {
                writer.write(" charset=\"");
                writer.write(javascriptCharset);
                writer.write('"');
            }

            writer.write('>');
            if (htmlProcessContext.useScriptCData()) {
                writer.write(IHtmlRenderContext.JAVASCRIPT_CDATA_BEGIN);
            }
            writer.println();

            Map symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);

            IJavaScriptWriter jsWriter = new JavaScriptTag.JavaScriptWriterImpl(
                    facesContext, symbols, writer);

            writeCookieTest(jsWriter);
            jsWriter.end();

            writer.println("</SCRIPT>");
        }

        writer.print("<SCRIPT");
        if (htmlProcessContext.useMetaContentScriptType() == false) {
            writer.write(" type=\"");
            writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            writer.write('"');
        }

        uri = jsBaseURI + "/" + uri;
        writer.write(" src=\"");
        URLFormCodec.writeURL(writer, uri);
        writer.write('\"');

        if (javascriptCharset != null) {
            writer.write(" charset=\"");
            writer.write(javascriptCharset);
            writer.write('"');
        }

        writer.println("></SCRIPT>");
    }

    private void writeDisabledScriptPageURL(FacesContext facesContext,
            JspWriter writer) throws IOException {
        String pageURL = disabledScriptPageURL;

        if (UIComponentTag.isValueReference(pageURL)) {
            Application application = facesContext.getApplication();

            ValueBinding vb = application.createValueBinding(pageURL);

            Object value = vb.getValue(facesContext);

            if (value == null) {
                return;
            }

            pageURL = String.valueOf(value);
        }

        writer.println("<NOSCRIPT>");
        writer.print("<META http-equiv=\"Refresh\" content=\"0; URL=");
        URLFormCodec.writeURL(writer, pageURL);
        writer.println("\" />");
        writer.println("</NOSCRIPT>");
    }

    private void writeCookieTest(IJavaScriptWriter writer) throws IOException {
        String pageURL = disabledCookiesPageURL;

        if (UIComponentTag.isValueReference(pageURL)) {
            FacesContext facesContext = writer.getFacesContext();

            Application application = facesContext.getApplication();

            ValueBinding vb = application.createValueBinding(pageURL);

            Object value = vb.getValue(facesContext);

            if (value == null) {
                return;
            }

            pageURL = String.valueOf(value);
        }

        writer.write("if (!navigator.cookieEnabled) document.location=");
        writer.writeString(pageURL);
        writer.writeln(";");
    }

    private void writeScriptTag_multiWindow(FacesContext facesContext,
            JspWriter writer, String uri, String jsBaseURI,
            IHierarchicalRepository repository, IContext repositoryContext)
            throws IOException {

        String javascriptCharset = IHtmlRenderContext.JAVASCRIPT_CHARSET;

        writer.print("<SCRIPT");
        if (htmlProcessContext.useMetaContentScriptType() == false) {
            writer.write(" type=\"");
            writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            writer.write('\"');
        }
        writer.write('>');
        if (htmlProcessContext.useScriptCData()) {
            writer.write(IHtmlRenderContext.JAVASCRIPT_CDATA_BEGIN);
        }
        writer.println();

        Map symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);

        IJavaScriptWriter jsWriter = new JavaScriptTag.JavaScriptWriterImpl(
                facesContext, symbols, writer);

        if (disabledCookiesPageURL != null) {
            writeCookieTest(jsWriter);
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
        if (htmlProcessContext.useScriptCData()) {
            writer.println(IHtmlRenderContext.JAVASCRIPT_CDATA_END);
        }
        jsWriter.end();

        writer.println("</SCRIPT>");
    }

    public void release() {
        invalidBrowserPageURL = null;
        disabledCookiesPageURL = null;
        disabledScriptPageURL = null;
        disableContextMenu = false;
        disableIEImageBar = false;

        disableCache = false;

        htmlProcessContext = null;

        base = null;

        title = null;
        favoriteImageURL = null;

        renderBaseTag = true;

        super.release();
    }

    /**
     * @return Returns the base.
     */
    public final String isBase() {
        return base;
    }

    /**
     * @param base
     *            The base to set.
     */
    public final void setBase(String base) {
        if ("false".equals(base)) {
            base = null;

        } else if (base != null && (base.length() == 0 || "true".equals(base))) {
            base = "context";
        }

        this.base = base;
    }

    public String getFavoriteImageURL() {
        return favoriteImageURL;
    }

    public void setFavoriteImageURL(String favoriteImageURL) {
        this.favoriteImageURL = favoriteImageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisabledCookiesPageURL() {
        return disabledCookiesPageURL;
    }

    public void setDisabledCookiesPageURL(String disabledCookiesPageURL) {
        this.disabledCookiesPageURL = disabledCookiesPageURL;
    }

    public String getDisabledScriptPageURL() {
        return disabledScriptPageURL;
    }

    public void setDisabledScriptPageURL(String disabledScriptPageURL) {
        this.disabledScriptPageURL = disabledScriptPageURL;
    }

    public boolean isDisableCache() {
        return disableCache;
    }

    public void setDisableCache(boolean disableCache) {
        this.disableCache = disableCache;
    }

    static boolean isMetaDataInitialized(PageContext pageContext) {
        Object obj = pageContext.getRequest().getAttribute(
                META_DATA_INITIALIZED_PROPERTY);
        return (obj != null);
    }

    public static boolean isDisableContextMenu(FacesContext facesContext) {
        Object obj = facesContext.getExternalContext().getRequestMap().get(
                DISABLE_CONTEXT_MENU_PROPERTY);
        return (obj != null);
    }

    private void metaDataInitialized() {
        pageContext.getRequest().setAttribute(META_DATA_INITIALIZED_PROPERTY,
                Boolean.TRUE);
    }

    public final String getInvalidBrowserPageURL() {
        return invalidBrowserPageURL;
    }

    public final void setInvalidBrowserPageURL(String invalidBrowserPageURL) {
        this.invalidBrowserPageURL = invalidBrowserPageURL;
    }

    public final boolean isRenderBaseTag() {
        return renderBaseTag;
    }

    public final void setRenderBaseTag(boolean renderBaseTag) {
        this.renderBaseTag = renderBaseTag;
    }

    public static final String getInvalidBrowserURL(FacesContext facesContext) {
        return (String) facesContext.getExternalContext().getRequestMap().get(
                INVALID_BROWSER_URL_PROPERTY);
    }

    public final boolean isDisableContextMenu() {
        return disableContextMenu;
    }

    public final void setDisableContextMenu(boolean disableContextMenu) {
        this.disableContextMenu = disableContextMenu;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ApplicationParameters implements Serializable {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = 491523571265962718L;

        boolean disableContextMenu;

        boolean disableIEImageBar;

        boolean multiWindowScript;

        Map symbols;

        String disabledCookiesPageURL;

        String disabledScriptPageURL;

        String invalidBrowserPageURL;

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

            multiWindowScript = "true".equalsIgnoreCase((String) initParameters
                    .get(MULTI_WINDOW_PARAMETER));

            symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);

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
            }
        }
    }
}