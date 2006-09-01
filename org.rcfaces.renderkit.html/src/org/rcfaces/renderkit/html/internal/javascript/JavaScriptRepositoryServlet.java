/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.16  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.15  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.14  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.13  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.12  2006/05/04 13:40:12  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.11  2006/03/15 13:53:02  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.10  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.9  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.8  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.7  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.6  2005/12/22 11:48:07  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.5  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.4  2005/11/08 12:16:27  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.3  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.2  2005/03/18 18:03:41  oeuillot
 * Ameliration du look du TabbedPane !
 *
 * Revision 1.1  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.1  2005/03/07 10:47:02  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 */
package org.rcfaces.renderkit.html.internal.javascript;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.codec.SourceFilter;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.util.AbstractRepository;
import org.rcfaces.core.internal.util.CameliaVersion;
import org.rcfaces.core.internal.util.Delay;
import org.rcfaces.core.internal.util.ServletTools;
import org.rcfaces.core.internal.webapp.ExpirationDate;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.RepositoryServlet;
import org.rcfaces.core.internal.webapp.IRepository.IContent;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository.IClass;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class JavaScriptRepositoryServlet extends RepositoryServlet {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(JavaScriptRepositoryServlet.class);

    private static final String MAIN_REPOSITORY_URI = "rcfaces-js/";

    private static final String CLEAR_VARIABLES[] = { "__static",
            "__prototype", "__resources" };

    private static final String MAIN_REPOSITORY_DIRECTORY_LOCATION = JavaScriptRepository.class
            .getPackage().getName().replace('.', '/');

    private static final String MAIN_REPOSITORY_LOCATION = MAIN_REPOSITORY_DIRECTORY_LOCATION
            + "/repository.xml";

    private static final String PARAMETER_PREFIX = Constants.getPackagePrefix()
            + ".javascript";

    private static final String REPOSITORY_DEV_MODE_PARAMETER = PARAMETER_PREFIX
            + ".REPOSITORY_DEV_MODE";

    private static final String SYMBOLS_FILENAME = "/symbols";

    private static final String SYMBOLSC_FILENAME = "/symbolsc";

    private static final String REPOSITORY_PROPERTY = "org.rcfaces.renderkit.html.javascript.DependenciesRepository";

    private static final String CONTEXT_REPOSITORY_PROPERTY = "org.rcfaces.renderkit.html.javascript.ContextRepository";

    private static final String CAN_SKIP_SPACES_PARAMETER = Constants
            .getPackagePrefix()
            + ".javascript.CAN_FILTER_SPACES";

    private static final String JAVASCRIPT_SYMBOLS_PARAMETER = Constants
            .getPackagePrefix()
            + ".javascript.SYMBOLS";

    private static final boolean APPEND_HEADER = true;

    private static final String VERSIONED_EXPIRE_PARAMETER = Constants
            .getPackagePrefix()
            + ".VERSIONED_EXPIRE";

    private static final DateFormat HEADER_DATE_FORMAT;
    static {
        HEADER_DATE_FORMAT = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.MEDIUM);
    }

    private static final Set SYMBOLS_FILENAMES = new HashSet();
    static {
        SYMBOLS_FILENAMES.add(SYMBOLS_FILENAME);
        SYMBOLS_FILENAMES.add(SYMBOLSC_FILENAME);
    }

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String COMPILED_JS_SUFFIX_PARAMETER = Constants
            .getPackagePrefix()
            + ".COMPILED_JS_SUFFIX";

    private String mainRepositoryURI;

    private Locale defaultLocale;

    private boolean canSkipSpace;

    private boolean debugMode;

    private boolean profilerMode;

    private String rcfacesVersion;

    private boolean enableSymbols = false;

    private Object container;

    private long symbolsLastModified = 0;

    private FacesContextFactory facesContextFactory;

    private Lifecycle javascriptLifeCycle;

    private String repositoryVersion;

    private ExpirationDate versionedExpirationDate;

    // Ne sert que dans la version de developpement
    private String compiledJsSuffix;

    public JavaScriptRepositoryServlet() {
    }

    public JavaScriptRepositoryServlet(String mainRepositoryURI) {
        this.mainRepositoryURI = mainRepositoryURI;
    }

    protected String getParameterPrefix() {
        return PARAMETER_PREFIX;
    }

    protected String getRepositoryDevModeParameterName() {
        return REPOSITORY_DEV_MODE_PARAMETER;
    }

    public void init(ServletConfig config) throws ServletException {

        rcfacesVersion = CameliaVersion.getVersion();

        if (mainRepositoryURI == null) {
            mainRepositoryURI = ServletTools.computeResourceURI(config
                    .getServletContext(), MAIN_REPOSITORY_URI, getClass());
        }

        super.init(config);

        compiledJsSuffix = getParameter(COMPILED_JS_SUFFIX_PARAMETER);

        String expires = getParameter(VERSIONED_EXPIRE_PARAMETER);
        if (expires != null) {
            versionedExpirationDate = ExpirationDate.parse(getServletName(),
                    VERSIONED_EXPIRE_PARAMETER, expires);

            if (LOG.isInfoEnabled()) {
                if (versionedExpirationDate.getExpiresDate() >= 0) {
                    LOG.info("Versioned resources expire date detected: "
                            + versionedExpirationDate.getExpiresDate()
                            + "  for sevlet '" + getServletName() + "'");
                }

                if (versionedExpirationDate.getExpiresDelay() >= 0) {
                    LOG.info("Versioned resources expire delay setted to "
                            + Delay.format(versionedExpirationDate
                                    .getExpiresDelay()) + " for sevlet '"
                            + getServletName() + "'");
                }
            }

        }

        String localeSupportProperty = getParameter("org.rcfaces.core.LOCALE_SUPPORT");
        if ("false".equalsIgnoreCase(localeSupportProperty)) {
            localeSupport = false;

            LOG.info("LOCALE_SUPPORT is disabled for servlet '"
                    + getServletName() + "'.");
        } else {
            localeSupport = true;

            LOG.info("LOCALE_SUPPORT is enabled for servlet '"
                    + getServletName() + "' (DEFAULT VALUE).");
        }

        facesContextFactory = (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

        javascriptLifeCycle = new JavascriptRepositoryLifecycle();
    }

    protected String getDefaultCharset() {
        return DEFAULT_CHARSET;
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        synchronized (this) {
            if (defaultLocale == null) {
                FacesContext context = facesContextFactory.getFacesContext(
                        getServletConfig().getServletContext(), request,
                        response, javascriptLifeCycle);
                try {
                    defaultLocale = context.getApplication().getDefaultLocale();

                } finally {
                    context.release();
                }
            }
        }

        super.doGet(request, response);
    }

    protected String getContentType(Record record) {
        String contentType = IHtmlRenderContext.JAVASCRIPT_TYPE;

        String charset = record.getCharset();
        if (charset == null) {
            return contentType;
        }

        return contentType + "; charset=" + charset;
    }

    protected IRepository initializeRepository(ServletConfig config)
            throws IOException {
        ServletContext servletContext = config.getServletContext();

        if (org.rcfaces.core.internal.Constants.BUILD_ID_URL_SUPPORT) {
            if (rcfacesVersion == null) {
                throw new FacesException(
                        "Can not enable \"Repository version\", rcfaces version is not detected !");
            }
            this.repositoryVersion = rcfacesVersion;

            LOG.info("Repository version '" + rcfacesVersion
                    + "' enabled for servlet '" + getServletName() + "'.");
        }

        AbstractRepository repository = new JavaScriptRepository(
                mainRepositoryURI, repositoryVersion);
        servletContext.setAttribute(REPOSITORY_PROPERTY, repository);

        container = servletContext;
        InputStream in = servletContext
                .getResourceAsStream(MAIN_REPOSITORY_LOCATION);
        if (in == null) {
            in = getClass().getClassLoader().getResourceAsStream(
                    MAIN_REPOSITORY_LOCATION);

            container = getClass().getClassLoader();
        }
        if (in != null) {
            try {
                repository.loadRepository(in,
                        MAIN_REPOSITORY_DIRECTORY_LOCATION, container);

            } finally {
                try {
                    in.close();

                } catch (IOException ex) {
                    LOG.error("Can not close '"
                            + MAIN_REPOSITORY_DIRECTORY_LOCATION + "'.", ex);
                }
            }
        }

        canSkipSpace = false;

        String s_canSkipSpace = config
                .getInitParameter(CAN_SKIP_SPACES_PARAMETER);
        if (s_canSkipSpace != null) {
            if ("true".equalsIgnoreCase(s_canSkipSpace)) {
                canSkipSpace = true;
            }

            LOG.debug("Set CAN_SKIP_SPACES property to " + canSkipSpace + ".");
        }

        IFile file = null;
        try {
            file = repository.declareFile(SYMBOLS_FILENAME,
                    MAIN_REPOSITORY_DIRECTORY_LOCATION, null, null, container,
                    null);

            if (file != null) {
                LOG.info("Javascript symbols detected.");
            }

        } catch (IllegalArgumentException ex) {
        }

        if (file == null) {
            try {
                file = repository.declareFile(SYMBOLSC_FILENAME,
                        MAIN_REPOSITORY_DIRECTORY_LOCATION, null, null,
                        container, null);

                if (file != null) {
                    LOG.info("Javascript compacted symbols detected.");
                }

            } catch (IllegalArgumentException ex) {
            }
        }

        if (file == null) {
            return repository;
        }

        enableSymbols = true;
        reloadSymbols(repository);

        return repository;
    }

    protected String getSetURI(String setName) {
        return "vfs-" + setName + ".js";
    }

    protected Record newRecord(IFile file, Locale locale) {
        return new JavaScriptRecord(file, locale);
    }

    protected boolean canSkipSpace() {
        return canSkipSpace;
    }

    protected String getInputCharset() {
        return "UTF-8";
    }

    private String getOuputCharset() {
        return "UTF-8";
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private class JavaScriptRecord extends Record {
        private static final String REVISION = "$Revision$";

        protected byte prolog[];

        protected byte epilog[];

        public JavaScriptRecord(IFile file, Locale locale) {
            super(file, locale);
        }

        public ExpirationDate getExpirationDate() {
            if (repositoryVersion != null && versionedExpirationDate != null) {
                return versionedExpirationDate;
            }

            return super.getExpirationDate();
        }

        protected URL[] getFileURL(IFile file) {
            // On peut tenter ici de rechercher la version compil�e !

            URL urls[] = file.getContentLocations(locale);

            String surl = urls[0].toString();
            if (surl.endsWith(".js") == false) {
                return urls;
            }

            if (enableSymbols == false) {
                return urls;
            }

            URL urlsc[] = new URL[urls.length];
            System.arraycopy(urls, 0, urlsc, 0, urls.length);

            for (int i = 0; i < urls.length; i++) {
                surl = urls[i].toString(); // + "c";
                if (compiledJsSuffix != null) {
                    surl += compiledJsSuffix;
                }

                URL url;
                try {
                    url = new URL(surl);

                } catch (MalformedURLException e) {
                    LOG.error("Can not get URL of '" + surl + "'.", e);
                    continue;
                }

                try {
                    IContent content = file.getContentProvider().getContent(
                            url, locale);
                    try {
                        InputStream ins = content.getInputStream();
                        if (ins != null) {
                            try {
                                ins.close();
                            } catch (IOException ex2) {
                            }

                            urlsc[i] = url;

                            LOG.debug("Use compiled record of '"
                                    + file.getFilename() + "'.");
                        }
                    } finally {
                        content.release();
                    }

                } catch (IOException ex) {
                    LOG.error("Can not get connection of '"
                            + file.getFilename() + "'.", ex);
                }
            }

            return urlsc;
        }

        protected byte[] updateBuffer(byte[] buffer) throws IOException {

            String fileName = getFile().getFilename();
            if (fileName.endsWith(SYMBOLSC_FILENAME)) {
                return buffer;
            }

            prolog = null;
            if (enableSymbols) {

                if (SYMBOLS_FILENAMES.contains(fileName) == false) {
                    JavaScriptRepositoryServlet.this
                            .reloadSymbols(getRepository());
                }
            }

            String content = new String(buffer, getInputCharset());

            if (canSkipSpace()) {
                content = SourceFilter.filterSkipSpaces(content);
            }

            return content.getBytes(getOuputCharset());
        }

        public final byte[] getProlog() throws IOException {
            if (prolog != null) {
                return prolog;
            }

            StringAppender sb = new StringAppender(128);
            fillProlog(sb);

            prolog = sb.toString().getBytes(getOuputCharset());

            return prolog;
        }

        public final byte[] getEpilog() throws IOException {
            if (epilog != null) {
                return epilog;
            }

            StringAppender sb = new StringAppender(128);
            fillEpilog(sb);

            epilog = sb.toString().getBytes(getOuputCharset());

            return epilog;
        }

        private void fillEpilog(StringAppender sb) {

            // if (isMultiWindowScript(getServletContext())) {
            writeBundles(sb, getFile(), locale);
            // }

            Map map = getSymbols();

            int cnt = 0;
            for (int i = 0; i < CLEAR_VARIABLES.length; i++) {
                String var = CLEAR_VARIABLES[i];

                if (map != null) {
                    String cvar = (String) map.get(var);
                    if (cvar != null) {
                        var = cvar;
                    }
                }

                if (cnt > 0) {
                    sb.append('=');
                }
                sb.append(var);
                cnt++;
            }

            if (cnt > 0) {
                sb.append("=undefined;\n");
            }
        }

        protected void fillProlog(StringAppender sb) throws IOException {
            if (APPEND_HEADER) {
                if (rcfacesVersion != null) {
                    sb.append("var rcfacesVersion=\"");
                    sb.append(rcfacesVersion);
                    sb.append("\";");
                }

                sb.append("// Vedana Faces Components:");

                long date = getLastModificationDate();
                if (date > 0) {
                    sb.append(" lastModification=");
                    sb.append(HEADER_DATE_FORMAT.format(new Date(date)));
                }

                sb.append('\n');
            }
        }

        public String getCharset() {
            return getOuputCharset();
        }
    }

    public static IJavaScriptRepository getRepository(
            ServletContext servletContext) {
        return (IJavaScriptRepository) servletContext
                .getAttribute(REPOSITORY_PROPERTY);
    }

    public void writeBundles(StringAppender sb, IFile file, Locale locale) {
        IFile dependencies[];
        if (file instanceof IRepository.ISet) {
            dependencies = ((IRepository.ISet) file).listDependencies();

        } else if (file instanceof IRepository.IModule) {
            dependencies = ((IRepository.IModule) file).listDependencies();

        } else {
            dependencies = new IFile[] { file };
        }

        List classes = new ArrayList(dependencies.length);
        for (int i = 0; i < dependencies.length; i++) {
            IFile dependency = dependencies[i];
            if ((dependency instanceof IJavaScriptRepository.IClassFile) == false) {
                continue;
            }
            IJavaScriptRepository.IClassFile cf = (IJavaScriptRepository.IClassFile) dependency;

            classes.addAll(Arrays.asList(cf.listClasses()));
        }

        if (classes.isEmpty()) {
            return;
        }

        sb.append("new f_bundle(\"");

        sb.append(file.getURI(locale));
        sb.append('\"');

        Map symbols = null;
        if (enableSymbols) {
            symbols = getSymbols();
        }

        for (Iterator it = classes.iterator(); it.hasNext();) {
            IClass clazz = (IClass) it.next();

            sb.append(',');

            String className = clazz.getName();
            if (symbols != null) {
                String cn = (String) symbols.get(className);

                if (cn != null) {
                    className = cn;
                }
            }
            sb.append(className);
        }

        sb.append(");\n");
    }

    public static IJavaScriptRepository getRepository(FacesContext facesContext) {
        Map map = facesContext.getExternalContext().getApplicationMap();

        return (IJavaScriptRepository) map.get(REPOSITORY_PROPERTY);
    }

    public static IRepository.IContext getContextRepository(
            FacesContext facesContext) {
        Map map = facesContext.getExternalContext().getRequestMap();

        IRepository.IContext context = (IRepository.IContext) map
                .get(CONTEXT_REPOSITORY_PROPERTY);
        if (context != null) {
            return context;
        }

        Locale locale = ContextTools.getUserLocale(facesContext);

        context = getRepository(facesContext).createContext(locale);
        map.put(CONTEXT_REPOSITORY_PROPERTY, context);

        return context;
    }

    public static Map getSymbols(FacesContext facesContext) {
        Map applicationMap = facesContext.getExternalContext()
                .getApplicationMap();

        return (Map) applicationMap.get(JAVASCRIPT_SYMBOLS_PARAMETER);
    }

    public static Map getSymbols(ServletContext servletContext) {
        return (Map) servletContext.getAttribute(JAVASCRIPT_SYMBOLS_PARAMETER);
    }

    protected final Map getSymbols() {
        return getSymbols(getServletContext());
    }

    private void reloadSymbols(IRepository repository) throws IOException {
        boolean crypted = false;

        IFile file = repository.getFileByName(SYMBOLS_FILENAME);
        if (file == null) {
            file = repository.getFileByName(SYMBOLSC_FILENAME);
            crypted = true;
        }

        byte buffer[] = null;

        Record record = getFileRecord(file, null);
        if (record != null) {
            if (symbolsLastModified == record.getLastModificationDate()) {
                return;
            }

            buffer = record.getBuffer();
        }

        if (buffer == null || buffer.length == 0) {
            symbolsLastModified = 0;
            getServletContext().removeAttribute(JAVASCRIPT_SYMBOLS_PARAMETER);
            return;
        }

        if (crypted) {
            int l = (buffer[0] & 0xff);

            byte key[] = new byte[l];
            System.arraycopy(buffer, 1, key, 0, key.length);

            byte nbuf[] = new byte[buffer.length - 1 - key.length];
            System.arraycopy(buffer, 1 + key.length, nbuf, 0, nbuf.length);
            buffer = nbuf;

            try {
                SecretKeySpec skey = new SecretKeySpec(key, "DES");

                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

                cipher.init(Cipher.DECRYPT_MODE, skey);

                buffer = cipher.doFinal(buffer);

            } catch (Throwable th) {
                IOException ex = new IOException(
                        "Can not decrypt symbols table !");
                ex.initCause(th);

                throw ex;
            }
        }

        Map symbols = loadSymbols(buffer);
        symbolsLastModified = record.getLastModificationDate();

        getServletContext().setAttribute(JAVASCRIPT_SYMBOLS_PARAMETER, symbols);
    }

    private Map loadSymbols(byte[] buffer) throws IOException {
        Map symbols = new HashMap(buffer.length / 16);

        ByteArrayInputStream bin = new ByteArrayInputStream(buffer);

        Properties properties = new Properties();
        properties.load(bin);

        // On utilise une version non synchronisée !
        symbols.putAll(properties);

        return symbols;
    }

    protected Locale getDefaultLocale() {
        if (defaultLocale == null) {
            return Locale.getDefault();
        }

        return defaultLocale;
    }
}
