/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.21  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.20  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.19  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.18  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.17  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.16  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.15  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.14  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.13  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.12  2005/12/22 11:48:06  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.11  2005/11/08 12:16:27  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.10  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.9  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.8  2005/03/18 14:42:49  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.7  2005/03/07 10:47:02  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.6  2005/02/21 17:33:05  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.5  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.4  2004/12/30 17:24:19  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.3  2004/12/24 15:10:03  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.2  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.1  2004/09/24 14:01:34  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal.taglib;

import java.io.IOException;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.codec.URLFormCodec;
import org.rcfaces.core.internal.config.AbstractURLRewritingProvider;
import org.rcfaces.core.internal.renderkit.IExternalContext;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.core.webapp.ExpirationHttpServlet;
import org.rcfaces.core.webapp.IRepository;
import org.rcfaces.core.webapp.IRepository.IContext;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlRenderKit;
import org.rcfaces.renderkit.html.internal.IHtmlExternalContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.codec.HTMLCodec;
import org.rcfaces.renderkit.html.internal.css.ICssConfig;
import org.rcfaces.renderkit.html.internal.css.StylesheetsServlet;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class InitializeTag extends
        org.rcfaces.core.internal.taglib.AbstractInitializeTag {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(InitializeTag.class);

    private static final String META_DATA_INITIALIZED_PROPERTY = "org.rcfaces.renderkit.html.internal.taglib.InitializeTag.META_DATA_INITIALIZED";

    private static final String DISABLE_IE_IMAGE_BAR_ATTRIBUTE = Constants
            .getPackagePrefix()
            + ".DISABLE_IE_IMAGE_BAR";

    private static final String DISABLED_COOKIES_PAGE_URL_PROPERTY = Constants
            .getPackagePrefix()
            + ".DISABLED_COOKIES_PAGE_URL";

    private static final String DISABLED_SCRIPT_PAGE_URL_PROPERTY = Constants
            .getPackagePrefix()
            + ".DISABLED_SCRIPT_PAGE_URL";

    private static final String FAVORITE_IMAGE_URL_PARAMETER = Constants
            .getPackagePrefix()
            + ".FAVORITE_IMAGE_URL";

    private static final String MULTI_WINDOW_FILENAME = "f_multiWindow.js";

    public static final String MULTI_WINDOW_ATTRIBUTE = "org.rcfaces.core.MULTI_WINDOW_MODE";

    private static final String NONE_IMAGE_URL = "none";

    private boolean symbolsInitialized = false;

    private boolean disableIEImageBar = false;

    private boolean multiWindowScript = false;

    private boolean disableCache = false;

    private String base;

    private String favoriteImageURL;

    private String title;

    private String disabledCookiesPageURL;

    private String disabledScriptPageURL;

    private Map symbols = null;

    private String defaultDisabledCookiesPageURL;

    private String defaultDisabledScriptPageURL;

    private IHtmlExternalContext htmlExternalContext;

    public int doStartTag() throws JspException {

        int ret = super.doStartTag();

        JspWriter writer = pageContext.getOut();

        if (disabledCookiesPageURL == null) {
            disabledCookiesPageURL = defaultDisabledCookiesPageURL;
        }
        if ("false".equals(disabledCookiesPageURL)) {
            disabledCookiesPageURL = null;
        }

        if (disabledScriptPageURL == null) {
            disabledScriptPageURL = defaultDisabledScriptPageURL;
        }
        if ("false".equals(disabledScriptPageURL)) {
            disabledScriptPageURL = null;
        }

        setScriptType(IHtmlRenderContext.JAVASCRIPT_TYPE);

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = pageContext.getServletContext();

            if (disableCache) {
                disableCache(writer);
            }

            if (htmlExternalContext.useMetaContentScriptType()) {
                writer
                        .print("<META http-equiv=\"Content-Script-Type\" content=\"");
                writer.print(IHtmlRenderContext.JAVASCRIPT_TYPE);
                // writer.print("; charset=UTF-8");
                writer.println("\" />");

            }

            if (htmlExternalContext.useMetaContentStyleType()) {
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

            if (base != null) {
                writer.print("<BASE href=\"");

                if ("context".equals(base)) {
                    HttpServletRequest hr = (HttpServletRequest) pageContext
                            .getRequest();
                    String contextPath = facesContext.getExternalContext()
                            .getRequestContextPath();

                    base = hr.getRequestURL().toString();
                    int idx = base.indexOf(contextPath);
                    if (idx >= 0) {
                        base = base.substring(0, idx + contextPath.length());
                    }

                    htmlExternalContext.changeBaseHREF(contextPath);

                } else {
                    htmlExternalContext.changeBaseHREF(base);
                }

                URLFormCodec.writeURL(writer, base);
                if (base.endsWith("/") == false) {
                    writer.print('/');
                }
                writer.println("\" />");

            }

            if (favoriteImageURL == null) {
                favoriteImageURL = servletContext
                        .getInitParameter(FAVORITE_IMAGE_URL_PARAMETER);
            }
            if (favoriteImageURL != null) {
                writeFavoriteImageURL(writer, facesContext);
            }

            ICssConfig cssConfig = StylesheetsServlet
                    .getConfig(htmlExternalContext);
            if (cssConfig != null) {
                writer.print("<LINK rel=\"stylesheet\"");
                if (htmlExternalContext.useMetaContentStyleType() == false) {
                    writer.print(" type=\"text/css\"");
                }

                String styleSheetURI = htmlExternalContext
                        .getStyleSheetURI(cssConfig.getStyleSheetFileName());

                writer.print(" href=\"");
                URLFormCodec.writeURL(writer, styleSheetURI);
                writer.println("\" />");
            }

            if (disabledScriptPageURL != null) {
                writer.println("<NOSCRIPT>");
                writer.print("<META http-equiv=\"Refresh\" content=\"0; URL=");
                URLFormCodec.writeURL(writer, disabledScriptPageURL);
                writer.println("\" />");
                writer.println("</NOSCRIPT>");
            }

            IRepository repository = JavaScriptRepositoryServlet
                    .getRepository(servletContext);
            if (repository != null) {
                IRepository.ISet bootSet = repository.getBootSet();

                if (bootSet != null) {
                    IRepository.IContext repositoryContext = JavaScriptRepositoryServlet
                            .getContextRepository(facesContext);

                    if (repositoryContext.add(bootSet)) {
                        // Il n'est pas connu du repository !

                        String cameliaScriptURI = bootSet
                                .getURI(repositoryContext.getLocale());

                        String jsBaseURI = repository
                                .getBaseURI(htmlExternalContext);

                        if (multiWindowScript) {
                            writeScriptTag_multiWindow(facesContext, writer,
                                    cameliaScriptURI, jsBaseURI, repository,
                                    repositoryContext);

                        } else {
                            writeScriptTag(facesContext, writer,
                                    cameliaScriptURI, jsBaseURI);
                        }
                    }
                }
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

    protected IExternalContext initializeExternalContext(
            ExternalContext externalContext) {
        htmlExternalContext = HtmlRenderKit.getExternalContext(externalContext);

        return htmlExternalContext;
    }

    private void disableCache(JspWriter writer) throws IOException {

        try {
            ServletResponse servletResponse = pageContext.getResponse();

            if (servletResponse instanceof HttpServletResponse) {
                ExpirationHttpServlet
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
        IURLRewritingProvider.ImageInformation favoriteImageOperation = null;
        if (AbstractURLRewritingProvider.URL_REWRITING_SUPPORT) {
            urlRewritingProvider = AbstractURLRewritingProvider
                    .getInstance(facesContext.getExternalContext());

            if (urlRewritingProvider != null) {
                favoriteImageOperation = new IURLRewritingProvider.ImageInformation();

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

        String favoriteIco = null;
        IURLRewritingProvider.ImageInformation favoriteIcoImageOperation = null;
        if (urlRewritingProvider != null) {
            favoriteIcoImageOperation = new IURLRewritingProvider.ImageInformation(
                    favoriteImageOperation);

            favoriteIco = urlRewritingProvider.computeURL(facesContext, null,
                    IURLRewritingProvider.IMAGE_URL_TYPE, "favoriteImageURL",
                    // IURLRewritingProvider.URL_REWRITING_PROVIDER_ID
                    favoriteImageURL, null, favoriteIcoImageOperation);
        }

        if (favoriteIco != null) {
            writer.print("<LINK rel=\"SHORTCUT ICON\"");

            String favoriteIcoMimeType = favoriteIcoImageOperation
                    .getMimeType();
            if (favoriteIcoMimeType != null) {
                writer.print(" type=\"");
                writer.print(favoriteIcoMimeType);
                writer.print('\"');
            }

            writer.print(" href=\"");
            URLFormCodec.writeURL(writer, favoriteIco);
            writer.println("\" />");
        }

        writer.print("<LINK rel=\"ICON\"");
        if (favoriteImageOperation != null) {
            String favoriteMimeType = favoriteImageOperation.getMimeType();
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
        ExternalContext externalContext = getExternalContext()
                .getExternalContext();

        Map application = externalContext.getInitParameterMap();

        disableIEImageBar = "false".equals(application
                .get(DISABLE_IE_IMAGE_BAR_ATTRIBUTE)) == false;

        defaultDisabledCookiesPageURL = (String) application
                .get(DISABLED_COOKIES_PAGE_URL_PROPERTY);
        if (defaultDisabledCookiesPageURL != null
                && defaultDisabledCookiesPageURL.trim().length() < 1) {
            defaultDisabledCookiesPageURL = null;
        }

        defaultDisabledScriptPageURL = (String) application
                .get(DISABLED_SCRIPT_PAGE_URL_PROPERTY);
        if (defaultDisabledScriptPageURL != null
                && defaultDisabledScriptPageURL.trim().length() < 1) {
            defaultDisabledScriptPageURL = null;
        }

        multiWindowScript = "true".equalsIgnoreCase((String) application
                .get(MULTI_WINDOW_ATTRIBUTE));

        if (LOG.isInfoEnabled()) {
            if (htmlExternalContext.useMetaContentScriptType()) {
                LOG.info("UseMetaContentScriptType is enabled for context.");
            }

            if (htmlExternalContext.useMetaContentStyleType()) {
                LOG.info("UseMetaContentStyleType is enabled for context.");
            }

            if (disableIEImageBar) {
                LOG.info("DisableIEImageBar is enabled for context.");
            }

            if (multiWindowScript) {
                LOG.info("MultiWindowScript is enabled for context.");
            }

            if (multiWindowScript) {
                LOG.info("MULTI_WINDOW_ATTRIBUTE is enabled for context.");
            }

            if (htmlExternalContext.getDebugMode()) {
                LOG.info("DEBUG_MODE is enabled for context.");
            }

            if (htmlExternalContext.getProfilerMode()) {
                LOG.info("PROFILER_MODE is enabled for context.");
            }
        }
    }

    private void writeScriptTag(FacesContext facesContext, JspWriter writer,
            String uri, String jsBaseURI) throws IOException {

        String javascriptCharset = IHtmlRenderContext.JAVASCRIPT_CHARSET;

        if (disabledCookiesPageURL != null) {
            writer.print("<SCRIPT");
            if (htmlExternalContext.useMetaContentScriptType() == false) {
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
            if (htmlExternalContext.useScriptCData()) {
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
        if (htmlExternalContext.useMetaContentScriptType() == false) {
            writer.write(" type=\"");
            writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            writer.write('"');
        }

        uri = jsBaseURI + uri;
        uri = htmlExternalContext.getBaseURI(uri);
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

    private void writeCookieTest(IJavaScriptWriter writer) throws IOException {
        writer.write("if (!navigator.cookieEnabled) document.location=");
        writer.writeString(disabledCookiesPageURL);
        writer.writeln(";");
    }

    private void writeScriptTag_multiWindow(FacesContext facesContext,
            JspWriter writer, String uri, String jsBaseURI,
            IRepository repository, IContext repositoryContext)
            throws IOException {

        String javascriptCharset = IHtmlRenderContext.JAVASCRIPT_CHARSET;

        writer.print("<SCRIPT");
        if (htmlExternalContext.useMetaContentScriptType() == false) {
            writer.write(" type=\"");
            writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            writer.write('\"');
        }
        writer.write('>');
        if (htmlExternalContext.useScriptCData()) {
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
        if (htmlExternalContext.useMetaContentScriptType() == false) {
            jsWriter.write(" type=\\\"");
            jsWriter.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            jsWriter.write("\\\"");
        }
        jsWriter.write(" src=\\\"");
        String u = jsBaseURI + uri;
        u = htmlExternalContext.getBaseURI(u);
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
                IRepository.FILENAME_COLLECTION_TYPE, repositoryContext);
        if (files != null && files.length > 0) {
            Locale locale = repositoryContext.getLocale();
            for (int i = 0; i < files.length; i++) {
                jsWriter.write("<SCRIPT");
                if (htmlExternalContext.useMetaContentScriptType() == false) {
                    jsWriter.write(" type=\\\"");
                    jsWriter.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
                    jsWriter.write("\\\"");
                }
                jsWriter.write(" src=\\\"");

                u = jsBaseURI + files[i].getURI(locale);
                u = htmlExternalContext.getBaseURI(u);

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
        if (htmlExternalContext.useScriptCData()) {
            writer.println(IHtmlRenderContext.JAVASCRIPT_CDATA_END);
        }
        jsWriter.end();

        writer.println("</SCRIPT>");
    }

    public void release() {
        htmlExternalContext = null;

        base = null;
        symbols = null;
        symbolsInitialized = false;

        title = null;
        favoriteImageURL = null;

        disableCache = false;

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

    private String convertSymbol(String symbol) {
        if (symbolsInitialized == false) {
            symbolsInitialized = true;

        }

        if (symbols == null) {
            return symbol;
        }

        String s = (String) symbols.get(symbol);
        if (s != null) {
            return s;
        }

        return symbol;
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

    private void metaDataInitialized() {
        pageContext.getRequest().setAttribute(META_DATA_INITIALIZED_PROPERTY,
                Boolean.TRUE);
    }

}