/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.service.log.LogService;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.core.lang.IClientStorage;
import org.rcfaces.core.util.ClientStorageManager;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository.IClass;
import org.rcfaces.renderkit.html.internal.renderer.MessagesRepository;
import org.rcfaces.renderkit.html.internal.service.LogHtmlService;
import org.rcfaces.renderkit.html.internal.util.JavaScriptTools;

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

    private static final String LAZY_TAG_USES_BROTHER_PARAMETER = Constants
            .getPackagePrefix()
            + ".LAZY_TAG_USES_BROTHER";

    private static final String ENABLE_LOG_CLIENT_PARAMETER = Constants
            .getPackagePrefix()
            + ".client.ENABLE_LOG";

    private static final String ENABLE_SCRIPT_VERIFY_PARAMETER = Constants
            .getPackagePrefix()
            + ".client.SCRIPT_VERIFY";

    private static final String VARIABLES_POOL_PROPERTY = "org.rcfaces.renderkit.html.POOL_PROPERTY";

    private static final String JAVASCRIPT_INITIALIZED_PROPERTY = "org.rcfaces.renderkit.html.JAVASCRIPT_INITIALIZED";

    private static final boolean USE_VARIABLE_CACHE = true;

    public static final IFile[] FILE_EMPTY_ARRAY = new IFile[0];

    private static final int STRINGS_INITIAL_SIZE = 64;

    private static final int COMPONENTS_INITIAL_SIZE = 16;

    private static final String SCRIPT_VERIFY = "try { f_core; } catch(x) { alert(\"RCFaces Javascript Components are not initialized properly !\"); }";

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

    private boolean javaScriptStubForced;

    private boolean lazyTagUsesBrother = Constants.LAZY_TAG_USES_BROTHER_DEFAULT_VALUE;

    public JavaScriptRenderContext(FacesContext facesContext) {
        parent = null;

        repository = JavaScriptRepositoryServlet.getRepository(facesContext);
        declaredFiles = JavaScriptRepositoryServlet
                .getContextRepository(facesContext);

        Map map = facesContext.getExternalContext().getApplicationMap();
        synchronized (facesContext.getApplication()) {
            variablePool = (VariablePool) map.get(VARIABLES_POOL_PROPERTY);
            if (variablePool == null) {
                variablePool = new VariablePool(facesContext);

                map.put(VARIABLES_POOL_PROPERTY, variablePool);
            }
        }

        String param = facesContext.getExternalContext().getInitParameter(
                LAZY_TAG_USES_BROTHER_PARAMETER);
        if (param != null) {
            if ("false".equalsIgnoreCase(param)) {
                lazyTagUsesBrother = false;

            } else if ("true".equalsIgnoreCase(param)) {
                lazyTagUsesBrother = true;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Set lazyTagUsesBrother=" + lazyTagUsesBrother);
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

        // On recupere les fichiers à inclure ...
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

        if (filesToRequire == null) {
            return false;
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

    public boolean canLazyTagUsesBrother() {
        if (parent != null) {
            return parent.canLazyTagUsesBrother();
        }

        return lazyTagUsesBrother;
    }

    public String convertSymbol(String className, String memberName) {
        if (symbolsInitialized == false) {
            symbolsInitialized = true;

            if (parent != null) {
                String converted = parent.convertSymbol(className, memberName);
                symbols = parent.symbols;
                return converted;
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            // FacesContext est forcement initialisé ici !
            symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);
        }

        if (symbols == null) {
            return memberName;
        }

        String converted;
        if (className != null && className.startsWith("f")) {
            StringAppender sa = new StringAppender(className.length() + 1
                    + memberName.length());
            sa.append(className);
            sa.append(".");
            sa.append(memberName);

            converted = (String) symbols.get(sa.toString());
            if (converted != null) {
                return converted;
            }
        }

        converted = (String) symbols.get(memberName);

        if (converted != null) {
            return converted;
        }

        return memberName;
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

        initializeJavaScript(writer, repository);
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
            IJavaScriptRepository repository) throws WriterException {

        FacesContext facesContext = writer.getFacesContext();
        ExternalContext externalContext = facesContext.getExternalContext();

        Map requestMap = externalContext.getRequestMap();
        if (requestMap.containsKey(JAVASCRIPT_INITIALIZED_PROPERTY)) {
            return;
        }

        LOG.debug("Initializing javascript.");

        requestMap.put(JAVASCRIPT_INITIALIZED_PROPERTY, Boolean.TRUE);

        Map initParameter = externalContext.getInitParameterMap();
        if ("false".equalsIgnoreCase((String) initParameter
                .get(ENABLE_SCRIPT_VERIFY_PARAMETER)) == false) {
            writer.writeln(SCRIPT_VERIFY);
        }

        IHtmlRenderContext htmlRenderContext = writer.getHtmlRenderContext();

        IHtmlProcessContext processContext = htmlRenderContext
                .getHtmlProcessContext();

        boolean debugMode = processContext.getDebugMode();
        if (debugMode) {
            writer.writeCall("f_core", "SetDebugMode").writeln(");");
        }

        boolean profilerMode = processContext.getProfilerMode();
        if (profilerMode) {
            writer.writeCall("f_core", "SetProfilerMode").writeln(");");
        }

        boolean designerMode = processContext.isDesignerMode();
        if (designerMode) {
            writer.writeCall("f_core", "SetDesignerMode").writeln(");");
        }

        String invalidBrowserURL = htmlRenderContext.getInvalidBrowserURL();
        if (invalidBrowserURL != null) {
            writer.writeCall("f_core", "VerifyBrowserCompatibility")
                    .writeString(invalidBrowserURL).writeln(");");
        }

        if (htmlRenderContext.isDisabledContextMenu()) {
            writer.writeCall("f_core", "DisableContextMenu").writeln(");");
        }

        if (htmlRenderContext.isClientValidation() == false) {
            writer.writeCall("f_env", "DisableClientValidation").writeln(");");
        }

        boolean flatIdentifier = processContext.isFlatIdentifierEnabled();
        if (flatIdentifier) {
            writer.writeCall("fa_namingContainer", "SetSeparator").writeln(
                    "false);");

        } else {
            if (Constants.CLIENT_NAMING_SEPARATOR_SUPPORT) {
                String separator = processContext.getNamingSeparator();

                if (separator != null) {
                    writer.writeCall("fa_namingContainer", "SetSeparator")
                            .writeString(String.valueOf(separator)).writeln(
                                    ");");
                }
            }
        }

        String baseURI = externalContext.getRequestContextPath();
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

        String styleSheetURI = htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetURI(null, true);
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

        IClientStorage clientStorage = ClientStorageManager.get(facesContext,
                false);
        if (clientStorage != null) {
            Iterator it = clientStorage.listAttributeNames();

            for (; it.hasNext();) {

            }
        }

        Map messages = null;
        Set clientMessageIdFilters = htmlRenderContext
                .getClientMessageIdFilters();

        if (clientMessageIdFilters
                .contains(IHtmlRenderContext.NO_CLIENT_MESSAGES) == false) {
            StringAppender sa = null;
            Iterator messageClientIds = facesContext.getClientIdsWithMessages();

            for (; messageClientIds.hasNext();) {
                String clientId = (String) messageClientIds.next();

                if (clientMessageIdFilters.isEmpty() == false
                        && clientMessageIdFilters.contains(clientId) == false) {
                    continue;
                }

                if (messages == null) {
                    messages = new HashMap();
                }

                Iterator it = facesContext.getMessages(clientId);
                for (; it.hasNext();) {
                    FacesMessage facesMessage = (FacesMessage) it.next();

                    String varName = (String) messages.get(facesMessage);
                    if (varName == null) {
                        varName = JavaScriptTools.writeMessage(facesContext,
                                writer, facesMessage);

                        messages.put(facesMessage, varName);

                    }
                    if (sa == null) {
                        sa = new StringAppender(32);
                    }

                    if (sa.length() > 0) {
                        sa.append(',');
                    }
                    sa.append(varName);
                }

                if (sa == null || sa.length() < 1) {
                    continue;
                }

                writer.writeCall("f_messageContext", "AppendMessages")
                        .writeString(clientId).write(',').write(sa.toString())
                        .writeln(");");

                sa.setLength(0);
            }
        }

        LOG.debug("Javascript initialized.");
    }

    public void clearJavaScriptStubForced() {
        javaScriptStubForced = false;
    }

    public void forceJavaScriptStub() {
        javaScriptStubForced = true;
    }

    public boolean isJavaScriptStubForced() {
        return javaScriptStubForced;
    }

}
