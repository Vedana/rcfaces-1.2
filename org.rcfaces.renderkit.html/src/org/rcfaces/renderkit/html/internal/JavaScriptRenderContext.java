/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.3  2006/09/05 08:57:14  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.14  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.13  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.12  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.11  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.10  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.9  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.8  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
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
 * Revision 1.5  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.4  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.3  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.1  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.service.log.LogService;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository.IClass;
import org.rcfaces.renderkit.html.internal.service.LogHtmlService;
import org.rcfaces.renderkit.html.internal.taglib.InitializeTag;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptRenderContext implements IJavaScriptRenderContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(JavaScriptRenderContext.class);

    private static final String STRING_EMPTY_ARRAY[] = new String[0];

    private static final String VARIABLES_POOL_PROPERTY = "org.rcfaces.renderkit.html.POOL_PROPERTY";

    private static final String ENABLE_LOG_CLIENT_PARAMETER = Constants
            .getPackagePrefix()
            + ".client.ENABLE_LOG";

    private static final String ENABLE_SCRIPT_VERIFY_PARAMETER = Constants
            .getPackagePrefix()
            + ".client.SCRIPT_VERIFY";

    private static final String JAVASCRIPT_INITIALIZED_PROPERTY = "org.rcfaces.renderkit.html.JAVASCRIPT_INITIALIZED";

    private static final boolean USE_VARIABLE_CACHE = true;

    public static final IFile[] FILE_EMPTY_ARRAY = new IFile[0];

    private static final int STRINGS_INITIAL_SIZE = 64;

    private static final int COMPONENTS_INITIAL_SIZE = 16;

    private static final String SCRIPT_VERIFY = "try { f_core; } catch(x) { alert(\"RCFaces Javascript Components are not initialized !\"); }";

    private final JavaScriptRenderContext parent;

    private final IJavaScriptRepository repository;

    private final Set waitingRequiredClasses = new HashSet();

    private VariablePool variablePool;

    private int varCounter = 1;

    private IRepository.IContext declaredFiles;

    private IFile[] filesToRequire;

    private Set uninitializedComponents = new HashSet();

    protected boolean symbolsInitialized = false;

    protected Map symbols;

    private boolean initialized = false;

    private MessagesRepository facesMessagesRepository;

    private Map strings;

    private Map componentIds;

    private Locale userLocale;

    public JavaScriptRenderContext(FacesContext facesContext) {
        parent = null;

        repository = JavaScriptRepositoryServlet.getRepository(facesContext);
        declaredFiles = JavaScriptRepositoryServlet
                .getContextRepository(facesContext);

        synchronized (facesContext.getApplication()) {
            Map map = facesContext.getExternalContext().getApplicationMap();

            variablePool = (VariablePool) map.get(VARIABLES_POOL_PROPERTY);
            if (variablePool == null) {
                variablePool = new VariablePool(facesContext);

                map.put(VARIABLES_POOL_PROPERTY, variablePool);
            }
        }

    }

    private JavaScriptRenderContext(JavaScriptRenderContext parent) {
        this.parent = parent;
        this.repository = parent.repository;
        this.declaredFiles = parent.declaredFiles.copy();
        this.initialized = parent.initialized;

        this.filesToRequire = parent.filesToRequire;
        isRequiresPending(); // Ca calcule !

        this.uninitializedComponents = new HashSet(uninitializedComponents);
    }

    public void computeRequires(IHtmlWriter writer,
            AbstractJavaScriptRenderer renderer) {

        // On recupere les fichiers � inclure ...
        renderer
                .addRequiredJavaScriptClassNames(writer, waitingRequiredClasses);
    }

    public String[] popUnitializedComponentsClientId() {
        if (uninitializedComponents.isEmpty()) {
            return STRING_EMPTY_ARRAY;
        }

        String old[] = (String[]) uninitializedComponents
                .toArray(new String[uninitializedComponents.size()]);
        uninitializedComponents.clear();

        return old;
    }

    public IRepository.IFile[] popRequiredFiles() {
        if (isRequiresPending() == false) {
            return FILE_EMPTY_ARRAY;
        }

        IRepository.IFile old[] = filesToRequire;

        filesToRequire = FILE_EMPTY_ARRAY;

        return old;
    }

    public boolean isRequiresPending() {
        if (waitingRequiredClasses.isEmpty() == false) {

            // Ok on recherche les fichiers � inclure
            filesToRequire = repository.computeFiles(waitingRequiredClasses,
                    IJavaScriptRepository.CLASS_NAME_COLLECTION_TYPE,
                    declaredFiles);

            waitingRequiredClasses.clear();
        }

        return filesToRequire.length > 0;
    }

    public IJavaScriptRenderContext pushInteractive() {
        if (initialized == false) {
            throw new FacesException(
                    "Can not push interactive while javaScript is not initialized !");
        }
        return new JavaScriptRenderContext(this);
    }

    public void pushUnitializedComponent(String clientId) {
        uninitializedComponents.add(clientId);
    }

    public String allocateVarName() {
        if (parent != null) {
            return parent.allocateVarName();
        }

        return variablePool.getVarName(varCounter++);
    }

    public String convertSymbol(String symbol) {
        if (symbolsInitialized == false) {
            symbolsInitialized = true;

            if (parent != null) {
                symbol = parent.convertSymbol(symbol);
                symbols = parent.symbols;
                return symbol;
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            // FacesContext est forcement initialisé ici !
            symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);
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

    public boolean isUnitializedComponentsPending() {
        return uninitializedComponents.size() > 0;
    }

    public boolean isInitialized() {
        return initialized;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class VariablePool implements Serializable {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -3122263677480314082L;

        private static final String JAVASCRIPT_VARIABLE_PREFIX_PARAMETER = Constants
                .getPackagePrefix()
                + ".JAVASCRIPT_VARIABLE_PREFIX";

        private static final String DEFAULT_JAVASCRIPT_VARIABLE_PREFIX = "_";

        private static final int MAXIMUM_COMPUTED_VARIABLE = 128;

        private final String jsVariablePrefix;

        private final String variables[];

        private int variableCount = 0;

        public VariablePool(FacesContext facesContext) {
            String prefix = facesContext.getExternalContext().getInitParameter(
                    JAVASCRIPT_VARIABLE_PREFIX_PARAMETER);

            if (prefix == null || prefix.length() < 1) {
                prefix = DEFAULT_JAVASCRIPT_VARIABLE_PREFIX;
            }

            if (Character.isJavaIdentifierStart(prefix.charAt(0)) == false) {
                throw new FacesException(
                        "Invalid javascript variable prefix ! ('" + prefix
                                + "')");
            }

            this.jsVariablePrefix = prefix;

            if (USE_VARIABLE_CACHE) {
                this.variables = new String[MAXIMUM_COMPUTED_VARIABLE];

                for (int i = 0; i < variables.length; i++) {
                    variables[i] = jsVariablePrefix + i;
                }
            }
        }

        public String getVarName(int idx) {
            if (USE_VARIABLE_CACHE == false) {
                return jsVariablePrefix + idx;
            }

            if (idx >= variables.length) {
                return jsVariablePrefix + idx;
            }

            return variables[idx];
        }
    }

    protected MessagesRepository getMessagesRepository(boolean create) {
        if (facesMessagesRepository != null) {
            return facesMessagesRepository;
        }

        if (parent == null) {
            if (create == false) {
                return null;
            }

            facesMessagesRepository = new MessagesRepository(this);
            return facesMessagesRepository;
        }

        MessagesRepository mp = parent.getMessagesRepository(create);
        if (create) {
            facesMessagesRepository = new MessagesRepository(this);
            return facesMessagesRepository;
        }

        return mp;
    }

    public String allocateFacesMessage(FacesMessage message,
            boolean mustDeclare[]) {
        MessagesRepository mr = getMessagesRepository(true);

        return mr.allocateFacesMessage(message, mustDeclare);
    }

    public String allocateString(String text, boolean mustDeclare[]) {
        return allocateString(text, mustDeclare, true);
    }

    protected String allocateString(String text, boolean mustDeclare[],
            boolean allocate) {
        String key;

        if (parent != null) {
            key = parent.allocateString(text, null, false);
            if (key != null) {
                return key;
            }
        }

        if (strings == null) {
            if (allocate == false) {
                return null;
            }

            strings = new HashMap(STRINGS_INITIAL_SIZE);

        } else {
            key = (String) strings.get(text);
            if (key != null || allocate == false) {
                return key;
            }
        }

        key = allocateVarName();

        strings.put(text, key);

        mustDeclare[0] = true;

        return key;
    }

    public String allocateComponentVarId(String text, boolean mustDeclare[]) {
        return allocateComponentVarId(text, mustDeclare, true);
    }

    protected String allocateComponentVarId(String text, boolean mustDeclare[],
            boolean allocate) {
        String key;

        if (parent != null) {
            key = parent.allocateComponentVarId(text, null, false);
            if (key != null) {
                return key;
            }
        }

        if (componentIds == null) {
            if (allocate == false) {
                return null;
            }

            componentIds = new HashMap(COMPONENTS_INITIAL_SIZE);

        } else {
            key = (String) componentIds.get(text);
            if (key != null || allocate == false) {
                return key;
            }
        }

        key = allocateVarName();

        componentIds.put(text, key);

        mustDeclare[0] = true;

        return key;
    }

    public void initializeJavaScriptDocument(IJavaScriptWriter writer)
            throws WriterException {

        if (initialized) {
            return;
        }
        initialized = true;

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        IHtmlRenderContext renderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        initializeJavaScript(writer, repository, renderContext
                .getHtmlExternalContext());
    }

    public void restoreState(Object state) {
        Object ret[] = (Object[]) state;

        initialized = ((Boolean) ret[0]).booleanValue();
        declaredFiles.restoreState(repository, ret[1]);
    }

    public Object saveState() {
        return new Object[] { Boolean.valueOf(initialized),
                declaredFiles.saveState() };
    }

    public Locale getUserLocale() {
        if (userLocale != null) {
            return userLocale;
        }

        userLocale = ContextTools.getUserLocale(null);

        return userLocale;
    }

    public void appendRequiredClasses(Collection classNames, String className,
            String requiredId) {
        IClass clazz = repository.getClassByName(className);
        if (clazz == null) {
            LOG.error("appendRequiredClasses: Unknown class '" + className
                    + "'.");
            return;
        }

        IClass requiredClasses[] = clazz.listRequiredClasses(requiredId);
        if (requiredClasses == null || requiredClasses.length < 1) {
            return;
        }

        for (int i = 0; i < requiredClasses.length; i++) {
            classNames.add(requiredClasses[i].getName());
        }
    }

    public static void initializeJavaScript(IJavaScriptWriter writer,
            IJavaScriptRepository repository,
            IHtmlProcessContext renderExternalContext) throws WriterException {

        Map requestMap = renderExternalContext.getFacesContext()
                .getExternalContext().getRequestMap();
        if (requestMap.containsKey(JAVASCRIPT_INITIALIZED_PROPERTY)) {
            return;
        }

        LOG.debug("Initializing javascript.");

        requestMap.put(JAVASCRIPT_INITIALIZED_PROPERTY, Boolean.TRUE);

        FacesContext facesContext = writer.getFacesContext();

        Map initParameter = facesContext.getExternalContext()
                .getInitParameterMap();
        if ("false".equalsIgnoreCase((String) initParameter
                .get(ENABLE_SCRIPT_VERIFY_PARAMETER)) == false) {
            writer.writeln(SCRIPT_VERIFY);
        }

        IHtmlProcessContext processContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        boolean debugMode = processContext.getDebugMode();
        if (debugMode) {
            writer.writeCall("f_core", "SetDebugMode").writeln("true);");
        }

        boolean profilerMode = processContext.getProfilerMode();
        if (profilerMode) {
            writer.writeCall("f_core", "SetProfilerMode").writeln("true);");
        }

        String invalidBrowserURL = InitializeTag
                .getInvalidBrowserURL(facesContext);
        if (invalidBrowserURL != null) {
            writer.writeCall("f_core", "VerifyBrowserCompatibility")
                    .writeString(invalidBrowserURL).writeln(");");
        }

        if (InitializeTag.isDisableContextMenu(facesContext)) {
            writer.writeCall("f_core", "DisableContextMenu").writeln(");");
        }

        boolean flatIdentifier = processContext.isFlatIdentifierEnabled();
        if (flatIdentifier) {
            writer.writeCall("fa_namingContainer", "SetSeparator").writeln(
                    "false);");

        } else {
            char separator = processContext.getNamingSeparatorChar();

            if (separator != NamingContainer.SEPARATOR_CHAR) {
                writer.writeCall("fa_namingContainer", "SetSeparator")
                        .writeString(String.valueOf(separator)).writeln(");");
            }
        }

        String baseURI = processContext.getFacesContext().getExternalContext()
                .getRequestContextPath();
        writer.writeCall("f_env", "Initialize").writeString(baseURI);

        int pred = 0;

        String requestURI = AbstractJavaScriptRenderer.getRequestURL(
                facesContext, facesContext.getViewRoot());
        if (requestURI != null) {
            for (; pred > 0; pred--) {
                writer.write(',').writeNull();
            }
            writer.write(',').writeString(requestURI);
        } else {
            pred++;
        }

        Locale locale = ContextTools.getUserLocale(facesContext);
        if (locale != null) {
            for (; pred > 0; pred--) {
                writer.write(',').writeNull();
            }
            writer.write(",\"").write(locale.getLanguage());

            String country = locale.getCountry();
            if (country != null && country.length() > 0) {
                writer.write('_').write(country);

                String variant = locale.getLanguage();
                if (variant != null && variant.length() > 0) {
                    writer.write('_').write(variant);
                }
            }

            writer.write('\"');

        } else {
            pred++;
        }

        String jsBaseURI = repository.getBaseURI(processContext);
        if (jsBaseURI != null) {
            for (; pred > 0; pred--) {
                writer.write(',').writeNull();
            }

            writer.write(',').writeString(jsBaseURI);
        } else {
            pred++;
        }

        String styleSheetURI = renderExternalContext.getStyleSheetURI(null);
        if (styleSheetURI != null && styleSheetURI.equals(jsBaseURI) == false) {
            for (; pred > 0; pred--) {
                writer.write(',').writeNull();
            }

            writer.write(',').writeString(styleSheetURI);
        } else {
            pred++;
        }

        writer.writeln(");");

        String logProperty = (String) initParameter
                .get(ENABLE_LOG_CLIENT_PARAMETER);
        if ("true".equalsIgnoreCase(logProperty)) {
            LogService logService = LogHtmlService.getInstance(facesContext);
            if (logService != null) {
                LogService.IFilter filters[] = logService
                        .listFilters(facesContext);
                if (filters.length > 0) {
                    writer.writeCall("f_log", "AddLevels");

                    for (int i = 0; i < filters.length; i++) {
                        LogService.IFilter filter = filters[i];

                        if (i > 0) {
                            writer.write(',');
                        }

                        writer.writeString(filter.getName());
                        writer.write(',');
                        writer.writeInt(filter.getLevel());
                    }

                    writer.writeln(");");
                }
            }
        }

        LOG.debug("Javascript initialized.");
    }

}
