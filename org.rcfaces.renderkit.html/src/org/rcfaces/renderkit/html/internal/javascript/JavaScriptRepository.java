/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.javascript;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Services;
import org.rcfaces.core.internal.contentProxy.IResourceProxyHandler;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.repository.BasicHierarchicalRepository;
import org.rcfaces.core.internal.util.FilteredContentProvider;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptRepository extends BasicHierarchicalRepository implements
        IJavaScriptRepository {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 7359720004642078904L;

    private static final Log LOG = LogFactory
            .getLog(JavaScriptRepository.class);

    private static final IClass[] CLASS_EMPTY_ARRAY = new IClass[0];

    private static final String JAVASCRIPT_BASE_URI_PROPERTY = "org.rfaces.core.repository.JAVASCRIPT_BASE_URI";

    private static final boolean KEEP_LANGUAGE_LOCALE = false;

    private final Map classByName = new HashMap();

    private final Map dependenciesById = new HashMap();

    private boolean convertSymbols = false;

    private String resourceBundleBaseName = null;

    private final Map applicationParameters;

    public JavaScriptRepository(String servletURI, String repositoryVersion,
            Map applicationParameters) {
        super(servletURI, repositoryVersion);

        this.applicationParameters = applicationParameters;
    }

    public void loadRepository(InputStream input, Object container) {
        super.loadRepository(input, container);

        String moduleName = AbstractFacesImplementation.get()
                .getJavaScriptModuleName();

        if (moduleName != null) {
            Module coreModule = (Module) getModuleByName("core");
            if (coreModule != null) {
                IModule module = getModuleByName(moduleName);
                if (module != null) {
                    IHierarchicalFile deps[] = module.listDependencies();
                    for (int i = 0; i < deps.length; i++) {
                        coreModule.addFile(deps[i]);
                    }
                }
            }
        }
    }

    protected HierarchicalFile createFile(IModule module, String name,
            String filename, String unlocalizedURI,
            URL unlocalizedContentLocation, IHierarchicalFile dependencies[],
            IContentProvider contentProvider) {

        return new JavaScriptFile(module, name, filename, unlocalizedURI,
                unlocalizedContentLocation, dependencies, contentProvider,
                convertSymbols, resourceBundleBaseName);
    }

    public IClass getClassByName(String className) {
        return (IClass) classByName.get(className);
    }

    protected IHierarchicalFile convertType(Object next, int typeOfCollection) {
        if (typeOfCollection == CLASS_NAME_COLLECTION_TYPE) {
            String className = (String) next;

            IClass clazz = getClassByName(className);
            if (clazz == null) {
                throw new NullPointerException("Unknown class '" + className
                        + "'.");
            }
            return clazz.getFile();
        }

        return super.convertType(next, typeOfCollection);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class JavaScriptFile extends HierarchicalFile implements
            IClassFile {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = 4826077949811747989L;

        private List classes;

        private final boolean remapSymbols;

        private final String resourceBundleBaseName;

        private IContentProvider contentProvider;

        public JavaScriptFile(IModule module, String name, String filename,
                String unlocalizedURI, URL unlocalizedContentLocation,
                IHierarchicalFile[] dependencies,
                IContentProvider contentProvider, boolean remapSymbols,
                String resourceBundleBaseName) {
            super(module, name, filename, unlocalizedURI,
                    unlocalizedContentLocation, dependencies, contentProvider);

            this.remapSymbols = remapSymbols;
            this.resourceBundleBaseName = resourceBundleBaseName;

            if (resourceBundleBaseName != null) {
                this.contentProvider = new LocalizedContentProvider(this);
            }
        }

        public IClass[] listClasses() {
            if (classes == null || classes.isEmpty()) {
                return CLASS_EMPTY_ARRAY;
            }

            return (IClass[]) classes.toArray(new IClass[classes.size()]);
        }

        public void addClass(IClass name) {
            if (classes == null) {
                classes = new ArrayList(4);
            }

            classes.add(name);
        }

        public String convertSymbols(Map symbols, String code) {
            if (remapSymbols == false || symbols == null) {
                return code;
            }

            IJavaScriptSymbolsConverter provider = (IJavaScriptSymbolsConverter) Services
                    .get().getService(IJavaScriptSymbolsConverter.SERVICE_ID);

            if (provider == null) {
                return code;
            }

            return provider.convertSymbols(getId(), code, symbols,
                    applicationParameters);
        }

        protected IContentProvider computeContentProvider() {
            if (contentProvider != null) {
                return contentProvider;
            }
            return super.computeContentProvider();
        }

    }

    protected Locale adaptLocale(Locale locale, IFile file) {
        if (KEEP_LANGUAGE_LOCALE) {
            if (locale != null
                    && (locale.getCountry().length() > 0 || locale.getVariant()
                            .length() > 0)) {
                locale = new Locale(locale.getLanguage());
            }
        }

        return super.adaptLocale(locale, file);
    }

    protected void addRules(Digester digester, Object container) {
        super.addRules(digester, container);

        digester.addRule("repository", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {
                super.begin(namespace, name, attributes);

                String convertSymbolsValue = attributes
                        .getValue("remapSymbols");

                convertSymbols = (convertSymbolsValue == null)
                        || ("true".equalsIgnoreCase(convertSymbolsValue));

                resourceBundleBaseName = attributes
                        .getValue("resourcesBundleBaseName");
            }

            public void end(String namespace, String name) throws Exception {

                convertSymbols = false;
                resourceBundleBaseName = null;

                super.end(namespace, name);
            }

        });

        digester.addRule("repository/module/file/class", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String xname,
                    Attributes attributes) throws Exception {

                String name = attributes.getValue("name");
                if (name == null) {
                    throw new IllegalArgumentException(
                            "No 'name' attribute for <class> element !");
                }

                JavaScriptFile javaScriptFile = (JavaScriptFile) this.digester
                        .peek();

                IClass clazz = new ClassImpl(name, javaScriptFile);

                javaScriptFile.addClass(clazz);

                if (classByName.put(name, clazz) != null) {
                    LOG.error("Class '" + name + "' is already known !");
                }

                this.digester.push(clazz);
            }

            public void end(String namespace, String name) throws Exception {

                ClassImpl clazz = (ClassImpl) this.digester.peek();
                List l = null;

                IClass cls[] = clazz.listRequiredClasses(null);
                for (int i = 0; i < cls.length; i++) {
                    IHierarchicalFile file = cls[i].getFile();

                    if (l == null) {
                        l = new ArrayList();
                    }
                    l.add(file);
                }

                IHierarchicalFile resources[] = clazz
                        .listRequiredResources(null);
                if (resources.length > 0) {
                    if (l == null) {
                        l = new ArrayList();
                    }
                    l.addAll(Arrays.asList(resources));
                }

                if (l != null) {
                    ((HierarchicalFile) clazz.getFile())
                            .addDependencies((IHierarchicalFile[]) l
                                    .toArray(new IHierarchicalFile[l.size()]));
                }

                this.digester.pop();
            }
        });

        digester.addRule("repository/module/file/class/required-class",
                new Rule() {
                    private static final String REVISION = "$Revision$";

                    public void begin(String namespace, String xname,
                            Attributes attributes) throws Exception {

                        String name = attributes.getValue("name");
                        if (name == null) {
                            throw new IllegalArgumentException(
                                    "No 'name' attribute for <class> element !");
                        }

                        String id = attributes.getValue("id");

                        ClassImpl clazz = (ClassImpl) this.digester.peek();

                        IClass requiredClass = getClassByName(name);
                        if (requiredClass == null) {
                            throw new IllegalArgumentException(
                                    "Unknown required class '" + name + "' !");
                        }

                        clazz.addRequiredClass(id, requiredClass);
                    }
                });
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class ClassImpl implements IClass {
        private static final String REVISION = "$Revision$";

        private final String name;

        private final IHierarchicalFile file;

        private List requiredClass;

        private IClass requiredClassArray[];

        private Map requiredClassById = new HashMap();

        public ClassImpl(String name, IHierarchicalFile file) {
            this.file = file;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addRequiredClass(String requiredId, IClass clazz) {
            if (requiredId == null) {
                if (requiredClass == null) {
                    requiredClass = new ArrayList();
                }

                requiredClass.add(clazz);
                return;
            }

            List requiredClass = (List) requiredClassById.get(requiredId);
            if (requiredClass == null) {
                requiredClass = new ArrayList();
                requiredClassById.put(requiredId, requiredClass);
            }

            requiredClass.add(clazz);
        }

        public IClass[] listRequiredClasses(String requiredId) {
            if (requiredId == null) {
                if (requiredClassArray != null) {
                    return requiredClassArray;
                }
                if (requiredClass == null) {
                    requiredClassArray = CLASS_EMPTY_ARRAY;
                    return requiredClassArray;
                }

                listInheritRequiredClasses(requiredClass, null);

                requiredClassArray = (IClass[]) requiredClass
                        .toArray(new IClass[requiredClass.size()]);
                requiredClass = null;
                return requiredClassArray;
            }

            Object obj = requiredClassById.get(requiredId);
            if (obj instanceof IClass[]) {
                return (IClass[]) obj;
            }

            List l = (List) obj;
            if (l == null) {
                l = new ArrayList();
            }
            listInheritRequiredClasses(l, requiredId);

            IClass requiredClassArray[];

            if (l.isEmpty()) {
                requiredClassArray = CLASS_EMPTY_ARRAY;

            } else {
                requiredClassArray = (IClass[]) l.toArray(new IClass[l.size()]);
            }

            requiredClassById.put(requiredId, requiredClassArray);

            return requiredClassArray;
        }

        public void listInheritRequiredClasses(List l, String requiredId) {
            Set l2 = new HashSet(l);
            if (requiredId != null) {
                l2.addAll(Arrays.asList(listRequiredClasses(null)));
            }

            for (Iterator it = l2.iterator(); it.hasNext();) {
                IClass clz = (IClass) it.next();

                IClass rcs[] = clz.listRequiredClasses(requiredId);
                if (rcs.length < 1) {
                    continue;
                }

                l.addAll(Arrays.asList(rcs));
            }
        }

        public IHierarchicalFile[] listRequiredResources(String requiredId) {
            return HIERARCHICAL_FILE_EMPTY_ARRAY;
        }

        public IHierarchicalFile getFile() {
            return file;
        }

        public String getResourceBundleName() {
            return ((JavaScriptFile) getFile()).resourceBundleBaseName;
        }

    }

    public String getBaseURI(IProcessContext processContext) {
        FacesContext facesContext = processContext.getFacesContext();

        ExternalContext ext = facesContext.getExternalContext();

        Map request = ext.getRequestMap();
        String uri = (String) request.get(JAVASCRIPT_BASE_URI_PROPERTY);
        if (uri != null) {
            return uri;
        }

        IResourceProxyHandler resourceProxyHandler = processContext
                .getRcfacesContext().getResourceProxyHandler();
        if (resourceProxyHandler != null && resourceProxyHandler.isEnabled()
                && resourceProxyHandler.isFrameworkResourcesEnabled()) {

            StringAppender sa = new StringAppender(256);

            // Il nous faut une URL en context path type
            sa.append(servletURI);

            if (repositoryVersion != null && repositoryVersion.length() > 0) {
                sa.append('/');
                sa.append(repositoryVersion);
            }

            uri = resourceProxyHandler.computeProxyedURL(facesContext, null,
                    null, sa.toString());
        }

        if (uri == null) {
            StringAppender sa = new StringAppender(256);

            sa.append(ext.getRequestContextPath());
            sa.append(servletURI);

            if (repositoryVersion != null && repositoryVersion.length() > 0) {
                sa.append('/');
                sa.append(repositoryVersion);
            }

            uri = sa.toString();
        }

        request.put(JAVASCRIPT_BASE_URI_PROPERTY, uri);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Set Javascript repository URL to '" + uri + "'.");
        }
        return uri;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public class LocalizedContentProvider extends FilteredContentProvider {

        private final JavaScriptFile javaScriptFile;

        public LocalizedContentProvider(JavaScriptFile javaScriptFile) {
            this.javaScriptFile = javaScriptFile;
        }

        protected String updateBuffer(String buffer, URL url, Locale locale) {
            if (locale == null) {
                return super.updateBuffer(buffer, url, locale);
            }

            IClass cls[] = javaScriptFile.listClasses();
            if (cls.length < 1) {
                return super.updateBuffer(buffer, url, locale);
            }

            ResourceBundle resourceBundle = ResourceBundle.getBundle(
                    javaScriptFile.resourceBundleBaseName, locale);

            StringAppender sa = new StringAppender(buffer, 8000);

            for (int i = 0; i < cls.length; i++) {
                String className = cls[i].getName();

                String key = className + ".";

                boolean first = true;

                Enumeration keys = resourceBundle.getKeys();
                for (; keys.hasMoreElements();) {
                    String resourceKey = (String) keys.nextElement();
                    if (resourceKey.startsWith(key) == false) {
                        continue;
                    }

                    String value = resourceBundle.getString(resourceKey);

                    if (first) {
                        first = false;

                        sa.append("f_resourceBundle.Define2(\"").append(
                                className).append("\",{\n");
                    } else {
                        sa.append(",\n");
                    }

                    sa.append(resourceKey.substring(key.length())).append(':');

                    appendString(sa, value);
                }

                if (first == false) {
                    sa.append("\n});\n");
                }
            }

            return super.updateBuffer(sa.toString(), url, locale);
        }
    }

    private static StringAppender appendString(StringAppender sa, String str) {
        char escape = '\"';
        if (str.indexOf('\"') >= 0 && str.indexOf('\'') < 0) {
            escape = '\'';
        }

        sa.append(escape);

        char chs[] = str.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];

            if (c == '\n') {
                sa.append("\\n");
                continue;
            }
            if (c == '\r') {
                sa.append("\\r");
                continue;
            }
            if (c == escape) {
                sa.append('\\').append(escape);
                continue;
            }
            if (c == '\t') {
                sa.append("\\t");
                continue;
            }
            if (c == '\\') {
                sa.append("\\\\");
                continue;
            }

            sa.append(c);
        }

        sa.append(escape);

        return sa;
    }

}
