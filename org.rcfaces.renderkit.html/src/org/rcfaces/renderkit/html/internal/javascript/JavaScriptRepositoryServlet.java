/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.javascript;

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
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.ByteBufferInputStream;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.util.ClassLocator;
import org.rcfaces.core.internal.util.ServletTools;
import org.rcfaces.core.internal.webapp.AbstractHierarchicalRepository;
import org.rcfaces.core.internal.webapp.ExpirationDate;
import org.rcfaces.core.internal.webapp.HierarchicalRepositoryServlet;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository.IHierarchicalFile;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository.IModule;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository.ISet;
import org.rcfaces.core.internal.webapp.IRepository.IContent;
import org.rcfaces.core.internal.webapp.IRepository.IContext;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository.IClass;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptRepositoryServlet extends HierarchicalRepositoryServlet {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -2654621696260702001L;

    private static final Log LOG = LogFactory
            .getLog(JavaScriptRepositoryServlet.class);

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

    private static final String REPOSITORIES_PARAMETER = PARAMETER_PREFIX
            + ".REPOSITORIES";

    private static final String NO_CACHE_PARAMETER = Constants
            .getPackagePrefix()
            + ".NO_CACHE";

    private static final String COMPILED_JS_SUFFIX_PARAMETER = Constants
            .getPackagePrefix()
            + ".COMPILED_JS_SUFFIX";

    private static final String CONFIGURATION_VERSION_PARAMETER = Constants
            .getPackagePrefix()
            + ".CONFIGURATION_VERSION";

    private static final String SYMBOLS_FILENAME = "/symbols";

    private static final String REPOSITORY_PROPERTY = "org.rcfaces.renderkit.html.javascript.DependenciesRepository";

    private static final String CONTEXT_REPOSITORY_PROPERTY = "org.rcfaces.renderkit.html.javascript.ContextRepository";

    private static final String JAVASCRIPT_SYMBOLS_PARAMETER = Constants
            .getPackagePrefix()
            + ".javascript.SYMBOLS";

    private static final DateFormat HEADER_DATE_FORMAT;
    static {
        HEADER_DATE_FORMAT = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.MEDIUM);
    }

    private static final Locale SYMBOL_LOCALE = new Locale("SYMBOLS");

    private static final Set SYMBOLS_FILENAMES = new HashSet(2);
    static {
        SYMBOLS_FILENAMES.add(SYMBOLS_FILENAME);
    }

    private String mainRepositoryURI;

    private String htmlRCFacesBuildId;

    private boolean enableSymbols = false;

    private long symbolsLastModified = 0;

    private String repositoryVersion;

    private ExpirationDate versionedExpirationDate;

    // Ne sert que dans la version de developpement
    private String compiledJsSuffix;

    public JavaScriptRepositoryServlet() {
    }

    public JavaScriptRepositoryServlet(String mainRepositoryURI) {
        this.mainRepositoryURI = mainRepositoryURI;
    }

    public void init(ServletConfig config) throws ServletException {

        htmlRCFacesBuildId = Constants.getBuildId();

        if (mainRepositoryURI == null) {
            mainRepositoryURI = ServletTools.computeResourceURI(config
                    .getServletContext(), null, getClass());
        }

        if (mainRepositoryURI == null) {
            LOG.error("Servlet '" + config.getServletName()
                    + "' is disabled because its URL can not be determined !");
            return;
        }

        super.init(config);

        compiledJsSuffix = getParameter(COMPILED_JS_SUFFIX_PARAMETER);
    }

    protected boolean getVersionSupport() {
        return Constants.VERSIONED_FRAMEWORK_URL_SUPPORT;
    }

    protected String getParameterPrefix() {
        return PARAMETER_PREFIX;
    }

    protected String getNoCacheParameterName() {
        return NO_CACHE_PARAMETER;
    }

    protected String getRepositoryDevModeParameterName() {
        return REPOSITORY_DEV_MODE_PARAMETER;
    }

    protected String getDefaultCharset() {
        return Constants.JAVASCRIPT_DEFAULT_CHARSET;
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

        if (Constants.VERSIONED_FRAMEWORK_URL_SUPPORT) {
            if (htmlRCFacesBuildId == null) {
                throw new FacesException(
                        "Can not enable \"Repository version\", rcfaces buildId is not detected !");
            }

            String buildId = htmlRCFacesBuildId;

            boolean compiledVersion = isCompiledVersion();
            if (compiledVersion) {
                buildId += "c";
            }

            String configurationVersion = getParameter(CONFIGURATION_VERSION_PARAMETER);
            if (configurationVersion != null
                    && configurationVersion.length() > 0) {
                buildId += "." + configurationVersion;
            }

            this.repositoryVersion = buildId;

            LOG.info("Repository version buildId='" + htmlRCFacesBuildId
                    + "' compiledVersion=" + compiledVersion
                    + " setted for servlet '" + getServletName() + "'.");
        }

        AbstractHierarchicalRepository repository = new JavaScriptRepository(
                mainRepositoryURI, repositoryVersion);
        servletContext.setAttribute(REPOSITORY_PROPERTY, repository);

        List repositoriesLocation = new ArrayList();
        repositoriesLocation.add(MAIN_REPOSITORY_LOCATION);

        String repositoryParameter = config
                .getInitParameter(REPOSITORIES_PARAMETER);
        if (repositoryParameter != null) {
            for (StringTokenizer st = new StringTokenizer(repositoryParameter,
                    ","); st.hasMoreTokens();) {
                String repositoryLocation = st.nextToken().trim();

                if (repositoriesLocation.contains(repositoryLocation)) {
                    continue;
                }

                repositoriesLocation.add(repositoryLocation);

                LOG.debug("Add repository location '" + repositoryLocation
                        + "'.");
            }
        }

        Object container = null;

        for (Iterator it = repositoriesLocation.iterator(); it.hasNext();) {
            String repositoryLocation = (String) it.next();

            LOG.debug("Load repository location '" + repositoryLocation
                    + "' ...");

            Object repositoryContainer = servletContext;
            InputStream in = servletContext
                    .getResourceAsStream(repositoryLocation);
            if (in == null) {
                in = getClass().getClassLoader().getResourceAsStream(
                        repositoryLocation);

                repositoryContainer = getClass().getClassLoader();
            }

            if (in == null) {
                LOG.info("Can not find repository '" + repositoryLocation
                        + "'.");
                continue;
            }

            if (container == null) {
                container = repositoryContainer;
            }

            try {
                repository.loadRepository(in, repositoryContainer);

            } finally {
                try {
                    in.close();

                } catch (IOException ex) {
                    LOG
                            .error("Can not close '" + repositoryLocation
                                    + "'.", ex);
                }
            }

            LOG
                    .debug("Repository location '" + repositoryLocation
                            + "' loaded");
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
            LOG.debug("Can not load symbols file", ex);
        }

        if (file == null) {
            return repository;
        }

        enableSymbols = true;
        reloadSymbols(repository);

        return repository;
    }

    protected boolean isCompiledVersion() {
        ServletContext servletContext = getServletContext();

        String symbolURL = MAIN_REPOSITORY_DIRECTORY_LOCATION
                + SYMBOLS_FILENAME;

        URL url = ClassLocator.getResource(symbolURL, this, servletContext);
        if (url == null) {
            return false;
        }

        return true;
    }

    protected String getSetURI(String setName) {
        return "vfs-" + setName + ".js";
    }

    protected Record newRecord(IFile file, Locale locale) {
        return new JavaScriptRecord(file, locale);
    }

    protected String getInputCharset() {
        return "UTF-8";
    }

    private String getOuputCharset() {
        return "UTF-8";
    }

    protected ISet initializeDefaultSet() {

        List mds = new ArrayList(8);

        IModule modules[] = getHierarchicalRepository().listModules();

        for (int i = 0; i < modules.length; i++) {
            IModule module = modules[i];

            if (module.isDefaultCoreModule() == false) {
                continue;
            }

            module.setGroupAllFiles(true);
            mds.add(module);
        }

        IContext context = getHierarchicalRepository().createContext(null);

        IHierarchicalFile ret[] = getHierarchicalRepository().computeFiles(mds,
                IHierarchicalRepository.FILE_COLLECTION_TYPE, context);

        String name = getBootSetDefaultValue();

        String uri = getSetURI(name);

        mds = Arrays.asList(ret);

        ISet set = getHierarchicalRepository().declareSet(name, uri,
                (IModule[]) mds.toArray(new IModule[mds.size()]));

        return set;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private class JavaScriptRecord extends HierarchicalRecord {
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

        protected Object[] getFileContentReferences(IFile file) {
            // On peut tenter ici de rechercher la version compil�e !

            Object urls[] = file.getContentReferences(locale);

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

            prolog = null;
            if (enableSymbols) {

                if (SYMBOLS_FILENAMES.contains(fileName) == false) {
                    JavaScriptRepositoryServlet.this
                            .reloadSymbols(getRepository());
                }
            }

            String content = new String(buffer, getInputCharset());

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
            writeBundles(sb, (IHierarchicalFile) getFile(), locale);
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
            if (Constants.JAVASCRIPPT_APPEND_RCFACES_HEADER) {
                if (htmlRCFacesBuildId != null) {
                    sb.append("var rcfacesBuildId=\"");
                    sb.append(htmlRCFacesBuildId);
                    sb.append("\";");
                }

                sb.append("// RCFaces Components:");

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

    /*
     * public static final IJavaScriptRepository getRepository( ServletContext
     * servletContext) { IJavaScriptRepository javaScriptRepository =
     * (IJavaScriptRepository) servletContext
     * .getAttribute(REPOSITORY_PROPERTY);
     * 
     * if (javaScriptRepository == null) { throw new FacesException( "Javascript
     * repository is not initialized !"); }
     * 
     * return javaScriptRepository; }
     */

    public static final IJavaScriptRepository getRepository(
            FacesContext facesContext) {
        Map map = facesContext.getExternalContext().getApplicationMap();

        IJavaScriptRepository javaScriptRepository = (IJavaScriptRepository) map
                .get(REPOSITORY_PROPERTY);

        if (javaScriptRepository == null) {
            throw new FacesException(
                    "Javascript repository is not initialized !");
        }

        return javaScriptRepository;
    }

    public void writeBundles(StringAppender sb, IHierarchicalFile file,
            Locale locale) {
        IHierarchicalFile dependencies[];
        if (file instanceof IHierarchicalRepository.ISet) {
            dependencies = ((IHierarchicalRepository.ISet) file)
                    .listDependencies();

        } else if (file instanceof IHierarchicalRepository.IModule) {
            dependencies = ((IHierarchicalRepository.IModule) file)
                    .listDependencies();

        } else {
            dependencies = new IHierarchicalFile[] { file };
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
        IFile file = repository.getFileByName(SYMBOLS_FILENAME);

        byte buffer[] = null;

        Record record = getFileRecord(file, SYMBOL_LOCALE);
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

        Map symbols = loadSymbols(buffer);
        symbolsLastModified = record.getLastModificationDate();

        getServletContext().setAttribute(JAVASCRIPT_SYMBOLS_PARAMETER, symbols);
    }

    private Map loadSymbols(byte[] buffer) throws IOException {
        Map symbols = new HashMap(buffer.length / 16);

        InputStream bin = new ByteBufferInputStream(buffer);

        Properties properties = new Properties();
        properties.load(bin);

        bin.close();

        // On utilise une version non synchronisée !
        symbols.putAll(properties);

        return symbols;
    }

    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (mainRepositoryURI == null) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }

        super.service(request, response);
    }

    protected String getBootSetDefaultValue() {
        return Constants.JAVASCRIPT_BOOT_SET_DEFAULT_VALUE;
    }

    protected String getGroupAllDefaultValue() {
        return Constants.JAVASCRIPT_GROUP_ALL_DEFAULT_VALUE;
    }

}
