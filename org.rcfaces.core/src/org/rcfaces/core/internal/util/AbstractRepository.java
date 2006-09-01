/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.8  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.7  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.6  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.5  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.4  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.3  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.2  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.1  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.5  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.4  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.3  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.2  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.1  2005/03/07 10:47:02  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 */
package org.rcfaces.core.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.URIParameters;
import org.xml.sax.Attributes;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractRepository implements IRepository {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

    protected static final IFile[] FILE_EMPTY_ARRAY = new IFile[0];

    private static final IModule[] MODULE_EMPTY_ARRAY = new IModule[0];

    private static final Integer FILE_TYPE = new Integer(0);

    private static final Integer MODULE_TYPE = new Integer(1);

    private static final Integer SET_TYPE = new Integer(2);

    private final Map filesByName = new HashMap();

    private final Map filesByURI = new HashMap();

    private final Map modulesByName = new HashMap();

    private final Map setsByName = new HashMap();

    private final Map resourcesByName = new HashMap();

    protected final String repositoryVersion;

    private final String fileVersion;

    protected final String servletURI;

    private ISet bootSet;

    public AbstractRepository(String servletURI, String repositoryVersion,
            String fileVersion) {
        if (servletURI.length() > 0) {
            if (servletURI.endsWith("/") && servletURI.length() > 1) {
                servletURI = servletURI.substring(0, servletURI.length() - 1);

            } else if (servletURI.startsWith("/") == false) {
                servletURI = "/" + servletURI;
            }
        }

        this.servletURI = servletURI;
        this.repositoryVersion = repositoryVersion;
        this.fileVersion = fileVersion;
    }

    public String getVersion() {
        return repositoryVersion;
    }

    public ISet getBootSet() {
        return bootSet;
    }

    public void setBootSet(ISet set) {
        this.bootSet = set;
    }

    protected IContentProvider getDefaultContentProvider() {
        return BasicContentProvider.SINGLETON;
    }

    public void loadRepository(InputStream input,
            String contentLocationDirectory, Object container) {

        if (contentLocationDirectory.length() > 0
                && contentLocationDirectory.endsWith("/") == false) {
            contentLocationDirectory += "/";
        }

        Digester digester = new Digester();

        addRules(digester, contentLocationDirectory, container);

        try {
            digester.parse(input);

        } catch (Exception ex) {
            LOG.error("Can not parse '" + container + "' ", ex);
        }
    }

    protected void addRules(Digester digester,
            final String contentLocationDirectory, final Object container) {

        digester.addRule("repository/module", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {
                String id = attributes.getValue("id");
                if (id == null) {
                    throw new IllegalArgumentException(
                            "No 'id' attribute for <module> element !");
                }

                String filename = getModuleFilename(id);
                String uri = getURI(filename);
                boolean groupAllFiles = "true".equalsIgnoreCase(attributes
                        .getValue("groupAll"));

                String rid = "m:" + id;

                IModule m = new Module(rid, filename, uri, groupAllFiles);
                filesByURI.put(uri, m);
                modulesByName.put(id, m);
                resourcesByName.put(rid, m);

                this.digester.push(m);
            }

            public void end(String namespace, String name) throws Exception {
                this.digester.pop();
            }

        });

        digester.addRule("repository/module/file", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String xname,
                    Attributes attributes) throws Exception {

                String name = attributes.getValue("name");
                if (name == null) {
                    throw new IllegalArgumentException(
                            "No 'name' attribute for <file> element !");
                }

                IFile ds[] = FILE_EMPTY_ARRAY;

                String depends = attributes.getValue("depends");
                if (depends != null) {
                    List l = null;
                    for (StringTokenizer st = new StringTokenizer(depends, ", "); st
                            .hasMoreTokens();) {
                        String dname = st.nextToken();

                        IFile fd = getFileByName(dname);
                        if (fd == null) {
                            throw new IllegalArgumentException("Can not find '"
                                    + dname + "' referenced by file '" + name
                                    + "' !");
                        }

                        if (l == null) {
                            l = new ArrayList();
                        }

                        l.add(fd);
                    }

                    if (l != null) {
                        ds = (IFile[]) l.toArray(new IFile[l.size()]);
                    }
                }

                IContentProvider contentProvider = null;
                String contentProviderClassName = attributes
                        .getValue("contentProvider");

                if (contentProviderClassName != null) {
                    try {
                        Class clazz = ClassLocator.load(
                                contentProviderClassName, null, container);

                        contentProvider = (IContentProvider) clazz
                                .newInstance();

                    } catch (Exception ex) {
                        LOG.error("Can not find contentProvider class '"
                                + contentProviderClassName + "'.", ex);

                        throw ex;
                    }
                }

                IModule module = (IModule) this.digester.peek();

                IFile f = declareFile(name, contentLocationDirectory, module,
                        ds, container, contentProvider);

                String uri = getURI(name);
                filesByURI.put(uri, f);

                this.digester.push(f);
            }

            public void end(String namespace, String name) throws Exception {
                this.digester.pop();
            }
        });
    }

    public final IFile declareFile(String name, String directory,
            IModule module, IFile depends[], Object container,
            IContentProvider contentProvider) {
        String contentLocation = getContentLocation(name, directory);

        URL url = null;
        if (container instanceof ClassLoader) {
            url = ((ClassLoader) container).getResource(contentLocation);
        }

        if (url == null && (container instanceof ServletContext)) {
            try {
                url = ((ServletContext) container).getResource(contentLocation);

            } catch (MalformedURLException e) {
                IllegalArgumentException ex = new IllegalArgumentException(
                        "Bad url for location '" + contentLocation + "' (file="
                                + name + ").");
                ex.initCause(e);

                throw ex;
            }
        }

        if (url == null) {
            throw new IllegalArgumentException(
                    "Can not find location of file '" + name + "'  (location='"
                            + contentLocation + "')");
        }

        String rname = "f:" + name;
        File f = createFile(module, rname, name, name, url, depends,
                contentProvider);

        filesByName.put(name, f);
        resourcesByName.put(rname, f);

        return f;
    }

    protected File createFile(IModule module, String name, String filename,
            String unlocalizedURI, URL unlocalizedContentLocation,
            IFile dependencies[], IContentProvider contentProvider) {

        return new File(module, name, filename, unlocalizedURI,
                unlocalizedContentLocation, dependencies, contentProvider);
    }

    private String getContentLocation(String name, String directory) {
        return directory + name;
    }

    public IFile getFileByName(String name) {
        return (IFile) filesByName.get(name);
    }

    public IModule getModuleByName(String name) {
        return (IModule) modulesByName.get(name);
    }

    public IFile getFileById(String id) {
        return (IFile) resourcesByName.get(id);
    }

    public ISet getSetByName(String name) {
        return (ISet) setsByName.get(name);
    }

    public IFile getFileByURI(String uri) {
        return (IFile) filesByURI.get(uri);
    }

    private String getModuleFilename(String id) {
        return "vfm-" + id + ".js";
    }

    private String getURI(String name) {
        return name;
    }

    public IFile[] computeFiles(Collection files, int typeOfCollection,
            IContext context) {
        List dependencies = null;
        List deps = null;

        for (Iterator it = files.iterator(); it.hasNext();) {
            Object next = it.next();

            IFile file = convertType(next, typeOfCollection);

            if (file == null) {
                throw new IllegalArgumentException("Object '" + next
                        + "' can not be converted to file !");
            }

            if (context.contains(file)) {
                continue;
            }

            if (deps == null && dependencies != null) {
                deps = new ArrayList(dependencies);
            }

            deps = computeFile(file, context, deps);

            if (deps == null
                    || deps.isEmpty()
                    || (dependencies != null && deps.size() == dependencies
                            .size())) {
                // La liste n'a pas chang�e !
                continue;
            }

            // La liste a chang�e !
            if (dependencies == null) {
                dependencies = new ArrayList(files.size() * 2);

            } else {
                deps.removeAll(dependencies); // Retire les doublons
            }

            dependencies.addAll(deps); // Ajoute les nouvelles dependances

            deps = null; // Reset la liste !
        }

        if (dependencies == null || dependencies.isEmpty()) {
            return FILE_EMPTY_ARRAY;
        }

        return (IFile[]) dependencies.toArray(new IFile[dependencies.size()]);
    }

    protected IFile convertType(Object next, int typeOfCollection) {
        if (typeOfCollection == FILENAME_COLLECTION_TYPE) {
            String filename = (String) next;

            // On recherches les fichiers associ�s
            IFile file = (IFile) filesByName.get(filename);
            if (file == null) {
                throw new IllegalArgumentException("File '" + filename
                        + "' is not known into repository !");
            }
            return file;
        }

        if (typeOfCollection == FILE_COLLECTION_TYPE) {
            return (IFile) next;
        }

        return null;
    }

    private List computeFile(IFile file, IContext context, List newFiles) {
        if (context.contains(file)) {
            return newFiles;
        }

        ISet set = null;
        IModule module = null;

        if (file instanceof ISet) {
            set = (ISet) file;
            file = null;

        } else if (file instanceof IModule) {
            module = (IModule) file;
            set = module.getSet();
            file = null;

        } else {
            module = file.getModule();
            set = module.getSet();
        }

        if (module != null && module.getGroupAllFiles()) {
            if (context.contains(module)) {
                // Il fait parti d'un module qui a �t� envoy� !
                return newFiles;
            }
            file = module;
        }

        if (set != null) {
            if (context.contains(set)) {
                // Il fait parti d'un set de modules qui a �t� envoy� !
                return newFiles;
            }
            file = set;
        }

        IFile ds[];

        if (file == set) {
            ds = set.listExternalDependencies();

        } else if (file == module) {
            ds = module.listExternalDependencies();

        } else {
            ds = file.listDependencies();
        }

        if (ds.length > 0) {
            for (int i = 0; i < ds.length; i++) {
                newFiles = computeFile(ds[i], context, newFiles);
            }

            if (set != null && context.contains(set)) {
                return newFiles;
            }

            // On retente, car il fait parti d'un module qui a �t� envoy� !
            if (context.contains(module)) {
                return newFiles;
            }
        }

        if (context.contains(file)) {
            return newFiles;
        }

        context.add(file);

        if (newFiles == null) {
            newFiles = new ArrayList();
        }

        newFiles.add(file);

        return newFiles;
    }

    public IModule[] listModules() {
        Collection c = modulesByName.values();

        return (IModule[]) c.toArray(new IModule[c.size()]);
    }

    public ISet declareSet(String name, String uri, String[] modules) {
        List l = new ArrayList(modules.length);

        for (int i = 0; i < modules.length; i++) {
            String moduleName = modules[i];

            IModule module = getModuleByName(moduleName);
            if (module == null) {
                throw new IllegalArgumentException("Can not find module '"
                        + moduleName + "'.");
            }

            addModules(l, module);
        }

        IModule ms[] = (IModule[]) l.toArray(new IModule[l.size()]);
        for (int i = 0; i < ms.length; i++) {
            ms[i].setGroupAllFiles(true);
        }

        String rname = "s:" + name;
        ISet set = new SetImpl(rname, name, uri, ms);

        filesByURI.put(uri, set);
        setsByName.put(name, set);
        resourcesByName.put(rname, set);

        return set;
    }

    private void addModules(List modules, IModule module) {
        if (modules.contains(module)) {
            return;
        }

        if (module.getSet() != null) {
            return;
        }

        IModule extMods[] = module.listExternalModules();
        if (extMods != null && extMods.length > 0) {
            for (int i = 0; i < extMods.length; i++) {
                addModules(modules, extMods[i]);
            }
        }

        modules.add(module);
    }

    public IContext createContext(Locale locale) {
        return new ContextImpl(locale);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static class ContextImpl implements IContext {
        private static final String REVISION = "$Revision$";

        private final Set files = new HashSet(32);

        private final Locale locale;

        public ContextImpl(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        public boolean contains(IFile file) {
            return files.contains(file);
        }

        public boolean add(IFile file) {
            return files.add(file);
        }

        public IContext copy() {
            ContextImpl ctx = new ContextImpl(locale);
            ctx.files.addAll(files);

            return ctx;
        }

        public void restoreState(IRepository repository, Object state) {
            if (state == null) {
                return;
            }

            Object fs[] = (Object[]) state;
            for (int i = 0; i < fs.length; i++) {
                IFile file = ((AbstractRepository) repository)
                        .getFileById((String) fs[i]);

                if (file == null) {
                    continue;
                }

                add(file);
            }
        }

        public Object saveState() {
            if (files.isEmpty()) {
                return null;
            }

            Iterator it = files.iterator();
            Object ret[] = new Object[files.size()];
            for (int i = 0; it.hasNext();) {
                IFile file = (IFile) it.next();

                ret[i++] = ((File) file).getId();
            }

            return ret;
        }
    }

    private static IModule[] filterModules(IModule[] modules) {
        List l = new ArrayList(modules.length);

        for (int i = 0; i < modules.length; i++) {
            IModule module = modules[i];

            if (module.getSet() != null) {
                continue;
            }

            l.add(module);
        }

        return (IModule[]) l.toArray(new IModule[l.size()]);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected class SetImpl extends File implements ISet {
        private static final String REVISION = "$Revision$";

        private IFile externalDependencies[];

        private IFile dependencies[];

        public SetImpl(String name, String filename, String uri,
                IModule modules[]) {
            super(null, name, filename, uri, null, filterModules(modules), null);

            modules = (IModule[]) super.listDependencies();
            for (int i = 0; i < modules.length; i++) {
                if (modules[i].getSet() != null) {
                    throw new IllegalArgumentException("Module '"
                            + modules[i].getFilename()
                            + "' is already associated to a Set !");
                }

                ((Module) modules[i]).setSet(this);
            }
        }

        public IFile[] listExternalDependencies() {
            if (externalDependencies != null) {
                return externalDependencies;
            }

            Set l = null;
            IFile files[] = listDependencies();

            for (int i = 0; i < files.length; i++) {
                IFile file = files[i];

                IFile dfiles[] = file.listDependencies();

                for (int j = 0; j < dfiles.length; j++) {
                    IFile dfile = dfiles[j];

                    IModule module = dfile.getModule();

                    if (module.getSet() == this) {
                        continue;
                    }

                    if (l == null) {
                        l = new HashSet();
                    }

                    l.add(dfile);
                }
            }

            if (l == null) {
                externalDependencies = FILE_EMPTY_ARRAY;

                return externalDependencies;
            }

            externalDependencies = (IFile[]) l.toArray(new IFile[l.size()]);

            return externalDependencies;
        }

        public IFile[] listDependencies() {
            if (dependencies != null) {
                return dependencies;
            }

            List l = new ArrayList();
            IModule modules[] = (IModule[]) super.listDependencies();
            for (int i = 0; i < modules.length; i++) {

                IFile fs[] = modules[i].listDependencies();

                l.addAll(Arrays.asList(fs));
            }

            dependencies = (IFile[]) l.toArray(new IFile[l.size()]);

            return dependencies;
        }

    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected class Module extends File implements IModule {
        private static final String REVISION = "$Revision$";

        private boolean groupAllFiles;

        private List filesList = new ArrayList();

        private IFile files[];

        private ISet set;

        private IFile externalDependencies[];

        private IModule externalModules[];

        public Module(String name, String filename, String uri,
                boolean groupAllFiles) {
            super(null, name, filename, uri, null, null, null);

            this.groupAllFiles = groupAllFiles;
        }

        public void setGroupAllFiles(boolean enable) {
            this.groupAllFiles = enable;
        }

        public boolean getGroupAllFiles() {
            return groupAllFiles;
        }

        public void addFile(IFile file) {
            filesList.add(file);
        }

        public IFile[] listDependencies() {
            if (filesList != null) {
                files = (IFile[]) filesList
                        .toArray(new IFile[filesList.size()]);
                filesList = null;
            }

            return files;
        }

        public IFile[] listExternalDependencies() {
            if (externalDependencies != null) {
                return externalDependencies;
            }

            Set l = null;
            IFile files[] = listDependencies();

            for (int i = 0; i < files.length; i++) {
                IFile file = files[i];

                IFile dfiles[] = file.listDependencies();

                for (int j = 0; j < dfiles.length; j++) {
                    IFile dfile = dfiles[j];

                    if (dfile.getModule() == this) {
                        continue;
                    }

                    if (l == null) {
                        l = new HashSet();
                    }

                    l.add(dfile);
                }
            }

            if (l == null) {
                externalDependencies = FILE_EMPTY_ARRAY;

                return externalDependencies;
            }

            externalDependencies = (IFile[]) l.toArray(new IFile[l.size()]);

            return externalDependencies;
        }

        public IModule[] listExternalModules() {
            if (externalModules != null) {
                return externalModules;
            }

            Set l = null;
            IFile files[] = listExternalDependencies();

            for (int i = 0; i < files.length; i++) {
                IModule module = files[i].getModule();
                if (module == null) {
                    continue;
                }

                if (l == null) {
                    l = new HashSet();
                }

                l.add(module);
            }

            if (l == null) {
                externalModules = MODULE_EMPTY_ARRAY;

                return externalModules;
            }

            externalModules = (IModule[]) l.toArray(new IModule[l.size()]);

            return externalModules;
        }

        public void setSet(ISet set) {
            this.set = set;
        }

        public ISet getSet() {
            return set;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected class File implements IFile, Serializable {
        private static final String REVISION = "$Revision$";

        private final int h;

        private final IModule module;

        private final String id;

        private final String filename;

        private final URL unlocalizedContentLocation;

        private final String unlocalizedURI;

        private final IContentProvider contentProvider;

        private IFile[] dependencies;

        private LocalizedFile unlocalizedFile;

        private Map localizedFiles;

        public File(IModule module, String name, String filename,
                String unlocalizedURI, URL unlocalizedContentLocation,
                IFile dependencies[], IContentProvider contentProvider) {
            this.module = module;
            this.id = name;
            this.filename = filename;
            this.unlocalizedURI = unlocalizedURI;
            this.dependencies = dependencies;
            this.unlocalizedContentLocation = unlocalizedContentLocation;
            this.contentProvider = contentProvider;

            int h = filename.hashCode();
            if (module != null) {
                ((Module) module).addFile(this);
                h ^= module.getFilename().hashCode();
            }

            this.h = h;
        }

        public IRepository getRepository() {
            return AbstractRepository.this;
        }

        public void addDependencies(IFile dependencies[]) {
            List l = new ArrayList(Arrays.asList(this.dependencies));

            for (int i = 0; i < dependencies.length; i++) {
                IFile f = dependencies[i];
                if (l.contains(f)) {
                    continue;
                }

                l.add(f);
            }

            this.dependencies = (IFile[]) l.toArray(new IFile[l.size()]);
        }

        public final String getId() {
            return id;
        }

        public URL[] getContentLocations(Locale locale) {
            LocalizedFile localizedFile = getLocalizedFile(locale);

            return localizedFile.getContentLocations();
        }

        public String getFilename() {
            return filename;
        }

        public IContentProvider getContentProvider() {
            if (contentProvider != null) {
                return contentProvider;
            }
            return getDefaultContentProvider();
        }

        public String getURI(Locale locale) {
            LocalizedFile localizedFile = getLocalizedFile(locale);

            return localizedFile.getURI();
        }

        private synchronized LocalizedFile getLocalizedFile(Locale locale) {
            if (locale == null) {
                if (unlocalizedFile != null) {
                    return unlocalizedFile;
                }

                unlocalizedFile = new LocalizedFile(fileVersion,
                        getContentProvider(), null, unlocalizedURI,
                        unlocalizedContentLocation);
                return unlocalizedFile;
            }

            if (localizedFiles == null) {
                localizedFiles = new HashMap();
            }

            LocalizedFile localizedFile = (LocalizedFile) localizedFiles
                    .get(locale);
            if (localizedFile != null) {
                return localizedFile;
            }

            localizedFile = new LocalizedFile(fileVersion,
                    getContentProvider(), locale, unlocalizedURI,
                    unlocalizedContentLocation);
            localizedFiles.put(locale, localizedFile);

            return localizedFile;
        }

        public IFile[] listDependencies() {
            return dependencies;
        }

        public IModule getModule() {
            return module;
        }

        public boolean equals(Object obj) {
            if (obj == null || (obj instanceof File) == false) {
                return false;
            }

            File f = (File) obj;

            return f.filename.equals(filename);
        }

        public int hashCode() {
            return h;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static class LocalizedFile {
        private static final String REVISION = "$Revision$";

        private final String uri;

        private final URL[] contentLocations;

        public LocalizedFile(String fileVersion,
                IContentProvider contentProvider, Locale locale, String uri,
                URL unlocalizedContentLocation) {
            URL localizedContentLocation = null;

            if (locale != null || fileVersion != null) {
                URIParameters uriParameters = new URIParameters(uri);

                if (locale != null) {
                    uriParameters.appendLocale(locale);
                }
                if (fileVersion != null) {
                    uriParameters.appendVersion(fileVersion);
                }

                uri = uriParameters.computeParametredURI();
            }

            if (locale != null) {
                if (unlocalizedContentLocation != null) {
                    String localized = unlocalizedContentLocation.toString();
                    int idx = localized.lastIndexOf('.');
                    if (idx > 0) {
                        String variant = locale.getVariant();
                        String country = locale.getCountry();
                        String language = locale.getLanguage();
                        Locale foundLocale = null;

                        if (variant != null && variant.length() > 0) {
                            String l = localized.substring(0, idx) + "_"
                                    + locale.getLanguage()
                                    + localized.substring(idx);

                            localizedContentLocation = testURL(contentProvider,
                                    l);
                            if (localizedContentLocation != null) {
                                foundLocale = locale;
                            }
                        }

                        if (localizedContentLocation == null) {
                            if (country != null && country.length() > 0) {
                                String l = localized.substring(0, idx) + "_"
                                        + language + "_" + country
                                        + localized.substring(idx);

                                localizedContentLocation = testURL(
                                        contentProvider, l);
                                if (localizedContentLocation != null) {
                                    foundLocale = new Locale(language, country);
                                }
                            }
                        }

                        if (localizedContentLocation == null) {
                            if (language != null && language.length() > 0) {
                                String l = localized.substring(0, idx) + "_"
                                        + language + localized.substring(idx);

                                localizedContentLocation = testURL(
                                        contentProvider, l);
                                if (localizedContentLocation != null) {
                                    foundLocale = new Locale(language);
                                }
                            }
                        }

                        if (localizedContentLocation != null) {
                            if (LOG.isInfoEnabled()) {
                                LOG.info("Localized version '" + locale
                                        + "' found locale '" + foundLocale
                                        + "' ('" + uri + "').");
                            }

                        } else {
                            if (LOG.isTraceEnabled()) {
                                LOG.trace("Can not find localized version ("
                                        + locale + ") of '" + uri + "'.");
                            }
                        }
                    }
                }
            }

            if (unlocalizedContentLocation == null) {
                contentLocations = null;

            } else if (localizedContentLocation == null) {
                contentLocations = new URL[] { unlocalizedContentLocation };

            } else {
                contentLocations = new URL[] { unlocalizedContentLocation,
                        localizedContentLocation };
            }

            this.uri = uri;
        }

        private URL testURL(IContentProvider contentProvider, String localized) {

            URL localizedURL = null;
            try {
                localizedURL = new URL(localized);
            } catch (IOException ex) {
                LOG.error("Can not construct url '" + localized + "'.");
                return null;
            }

            if (contentProvider.testURL(localizedURL) == false) {
                return null;
            }

            return localizedURL;
        }

        public URL[] getContentLocations() {
            return contentLocations;
        }

        public String getURI() {
            return uri;
        }
    }
}
