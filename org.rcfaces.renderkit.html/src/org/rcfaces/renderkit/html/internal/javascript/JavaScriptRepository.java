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
 * Revision 1.12  2006/08/28 16:03:54  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.11  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.10  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.9  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.8  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.7  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.6  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.5  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 */
package org.rcfaces.renderkit.html.internal.javascript;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.IExternalContext;
import org.rcfaces.core.internal.util.AbstractRepository;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class JavaScriptRepository extends AbstractRepository implements
        IJavaScriptRepository {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(JavaScriptRepository.class);

    private static final IClass[] CLASS_EMPTY_ARRAY = new IClass[0];

    private static final String JAVASCRIPT_BASE_URI_PROPERTY = "org.rfaces.core.repository.JAVASCRIPT_BASE_URI";

    private final Map classByName = new HashMap();

    private final Map dependenciesById = new HashMap();

    public JavaScriptRepository(String servletURI, String repositoryVersion) {
        super(servletURI, repositoryVersion, null);
    }

    public void loadRepository(InputStream input,
            String contentLocationDirectory, Object container) {
        super.loadRepository(input, contentLocationDirectory, container);

        String moduleName = AbstractFacesImplementation.get()
                .getJavaScriptModuleName();

        if (moduleName != null) {
            Module coreModule = (Module) getModuleByName("core");
            if (coreModule != null) {
                IModule module = getModuleByName(moduleName);
                if (module != null) {
                    IFile deps[] = module.listDependencies();
                    for (int i = 0; i < deps.length; i++) {
                        coreModule.addFile(deps[i]);
                    }
                }
            }
        }
    }

    protected File createFile(IModule module, String name, String filename,
            String unlocalizedURI, URL unlocalizedContentLocation,
            IFile dependencies[], IContentProvider contentProvider) {

        return new JavaScriptFile(module, name, filename, unlocalizedURI,
                unlocalizedContentLocation, dependencies, contentProvider);
    }

    public IClass getClassByName(String className) {
        return (IClass) classByName.get(className);
    }

    protected IFile convertType(Object next, int typeOfCollection) {
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
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected class JavaScriptFile extends File implements IClassFile {
        private static final String REVISION = "$Revision$";

        private List classes;

        public JavaScriptFile(IModule module, String name, String filename,
                String unlocalizedURI, URL unlocalizedContentLocation,
                IFile[] dependencies, IContentProvider contentProvider) {
            super(module, name, filename, unlocalizedURI,
                    unlocalizedContentLocation, dependencies, contentProvider);
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
    }

    protected void addRules(Digester digester, String contentLocationDirectory,
            Object container) {
        super.addRules(digester, contentLocationDirectory, container);

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
                    IFile file = cls[i].getFile();

                    if (l == null) {
                        l = new ArrayList();
                    }
                    l.add(file);
                }

                IFile resources[] = clazz.listRequiredResources(null);
                if (resources.length > 0) {
                    if (l == null) {
                        l = new ArrayList();
                    }
                    l.addAll(Arrays.asList(resources));
                }

                if (l != null) {
                    ((File) clazz.getFile()).addDependencies((IFile[]) l
                            .toArray(new IFile[l.size()]));
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
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected static class ClassImpl implements IClass {
        private static final String REVISION = "$Revision$";

        private final String name;

        private final IFile file;

        private List requiredClass;

        private IClass requiredClassArray[];

        private Map requiredClassById = new HashMap();

        public ClassImpl(String name, IFile file) {
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

        public IFile[] listRequiredResources(String requiredId) {
            return FILE_EMPTY_ARRAY;
        }

        public IFile getFile() {
            return file;
        }
    }

    public String getBaseURI(IExternalContext externalContext) {
        Map request = externalContext.getExternalContext().getRequestMap();
        String uri = (String) request.get(JAVASCRIPT_BASE_URI_PROPERTY);
        if (uri != null) {
            return uri;
        }

        uri = servletURI;

        if (repositoryVersion != null && repositoryVersion.length() > 0) {
            uri += "/" + repositoryVersion;
        }

        request.put(JAVASCRIPT_BASE_URI_PROPERTY, uri);

        if (LOG.isInfoEnabled()) {
            LOG.info("Set Javascript repository URL to '" + uri + "'.");
        }
        return uri;
    }
}
