/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.FacesListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IHelpCapability;
import org.rcfaces.core.internal.capability.IRCFacesComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractJavaScriptRenderer extends AbstractHtmlRenderer
        implements IJavaScriptComponent {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractJavaScriptRenderer.class);

    // private static final String JAVASCRIPT_INITIALIZED =
    // "camelia.html.javascript.initialized";

    // private static final boolean FORCE_JAVASCRIPT_WRITER = false;

    // private static final String FIRST_COMPONENT = "camelia.component.first";

    // private static final String LAZY_COMPONENTS = "camelia.component.lazy";

    protected static final String LAZY_INIT_TAG = "v:init";

    // private static final String INIT_BY_NAME = "javascript.InitByName";

    private static final boolean ENCODE_EVENT_ATTRIBUTE = true;

    private static final String ALREADY_LAZY_OBJECT = "camelia.already.lazy";

    private boolean lazyTagUsesBrother = true;

    public static String getRequestURL(FacesContext facesContext,
            UIViewRoot viewRoot) {

        ExternalContext externalContext = facesContext.getExternalContext();

        String url = facesContext.getApplication().getViewHandler()
                .getActionURL(facesContext, viewRoot.getViewId());

        url = externalContext.encodeActionURL(url);

        // Plus en mode f_httpRequest !
        // url += "?" + DATAGRID_PARAMETER + "=update&";

        return url;
    }

    /*
     * protected void setInitializeByName(IComponentRenderContext renderContext) {
     * renderContext.setAttribute(INIT_BY_NAME, Boolean.TRUE); }
     */
    public final void initializeJavaScript(IJavaScriptWriter writer)
            throws WriterException {

        IJavaScriptRenderContext javaScriptRenderContext = writer
                .getJavaScriptRenderContext();

        lazyTagUsesBrother = javaScriptRenderContext.canLazyTagUsesBrother();

        javaScriptRenderContext.initializeJavaScriptDocument(writer);
    }

    protected IJavaScriptWriter writeJsInitComponent(IJavaScriptWriter writer)
            throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        String componentId = componentRenderContext.getComponentClientId();

        IJavaScriptRenderContext javascriptRenderContext = ((IHtmlRenderContext) componentRenderContext
                .getRenderContext()).getJavaScriptRenderContext();

        boolean declare[] = new boolean[1];
        String componentVarName = javascriptRenderContext
                .allocateComponentVarId(componentId, declare);

        writer.setComponentVarName(componentVarName);

        String cameliaClassLoader = writer.getJavaScriptRenderContext()
                .convertSymbol("f_classLoader", "_cameliaClassLoader");

        if (declare[0]) {
            writer.write("var ").write(componentVarName);

            writer.write('=').writeCall(cameliaClassLoader, "f_init");
            writer.writeString(componentId);
            writer.writeln(");");

            return writer;
        }

        writer.writeCall(cameliaClassLoader, "f_init");
        writer.write(componentVarName).writeln(");");

        return writer;
    }

    public void initializePendingComponents(IJavaScriptWriter writer)
            throws WriterException {

        IJavaScriptRenderContext javascriptRenderContext = writer
                .getJavaScriptRenderContext();

        if (javascriptRenderContext.isUnitializedComponentsPending() == false) {
            return;
        }

        javascriptRenderContext.popUnitializedComponentsClientId();

        String cameliaClassLoader = writer.getJavaScriptRenderContext()
                .convertSymbol("f_classLoader", "_cameliaClassLoader");

        writer.writeCall(cameliaClassLoader, "f_initializeObjects").writeln(
                ");");
    }

    public void initializeJavaScriptComponent(IJavaScriptWriter writer)
            throws WriterException {

        initializePendingComponents(writer);

        String javaScriptClassName = getJavaScriptClassName();
        if (javaScriptClassName == null) {
            return;
        }

        writeJsInitComponent(writer);

        // String componentVarName = writer.getComponentVarName();

        // writer.write("with(").write(componentVarName).writeln("){");

        IHtmlComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        if (componentRenderContext.hasClientDatas(true)) {
            encodeClientData(writer);
        }
    }

    public void releaseJavaScript(IJavaScriptWriter writer)
            throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();
        IHtmlRenderContext renderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        IJavaScriptRenderContext javascriptRenderContext = renderContext
                .getJavaScriptRenderContext();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Release Javascript javascriptInitialized="
                    + javascriptRenderContext.isInitialized() + " writer.open="
                    + writer.isOpened() + "  canUseLazyTag="
                    + renderContext.canUseLazyTag() + " sendComplete="
                    + sendCompleteComponent());
        }

        if (writer.isOpened() == false && renderContext.canUseLazyTag()) {
            // Le LAZY seulement en cas de non interactive, car IE ne
            // reconnait
            // pas les <v:init> dans le traitement interactif !

            if (javascriptRenderContext.isInitialized()) {
                if (componentRenderContext.setAttribute(ALREADY_LAZY_OBJECT,
                        Boolean.TRUE) == null) {
                    IHtmlWriter w = writer.getWriter();
                    // On ecrit à la main le tag, car on ne peut pas le
                    // fermer
                    // directement dans le m�me tag !
                    w.startElement(LAZY_INIT_TAG);

                    if (lazyTagUsesBrother == false) {
                        w.writeAttribute("rid", componentRenderContext
                                .getComponentClientId());
                    }

                    IRepository.IFile files[] = renderContext
                            .getJavaScriptRenderContext().popRequiredFiles();
                    if (files != null && files.length > 0) {
                        Locale locale = javascriptRenderContext.getUserLocale();
                        StringAppender sb = new StringAppender(
                                files.length * 32);
                        for (int i = 0; i < files.length; i++) {
                            if (i > 0) {
                                sb.append(',');
                            }
                            sb.append(files[i].getURI(locale));
                        }

                        w.writeAttribute("requiresBundle", sb.toString());
                    }

                    // Il y a des nouveaux clientDatas ?
                    if (writer.getHtmlComponentRenderContext().hasClientDatas(
                            true)) {
                        writeClientData(w,
                                (IClientDataCapability) componentRenderContext
                                        .getComponent());
                    }

                    w.endElement(LAZY_INIT_TAG);
                }

                javascriptRenderContext
                        .pushUnitializedComponent(componentRenderContext
                                .getComponentClientId());

                return;
            }
        }

        if (sendCompleteComponent()) {
            writer.writeMethodCall("f_completeComponent").writeln(");");
        }

        // writer.writeln("}");
    }

    protected abstract boolean sendCompleteComponent();

    protected String getJavaScriptClassName() {
        return null;
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return null;
    }

    protected final IHtmlWriter writeCoreAttributes(IHtmlWriter writer)
            throws WriterException {
        IHtmlWriter w = super.writeCoreAttributes(writer);

        String javascriptClass = getJavaScriptClassName();
        if (javascriptClass != null) {
            w.writeAttribute("v:class", javascriptClass);
        }

        return w;
    }

    public final IHtmlWriter writeJavaScriptAttributes(IHtmlWriter writer)
            throws WriterException {
        // Les evenements ....
        if (ENCODE_EVENT_ATTRIBUTE == false) {
            return writer;
        }
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        if (componentRenderContext.getRenderContext().getProcessContext()
                .isDesignerMode()) {
            return writer;
        }

        UIComponent component = componentRenderContext.getComponent();

        MethodBinding action = null;
        if (component instanceof UICommand) {
            action = ((UICommand) component).getAction();
        }

        boolean initJavascript = false;

        // On recherche les ActionListeners
        Map listenersByType = ListenerTools.getListenersByType(
                ListenerTools.ATTRIBUTE_NAME_SPACE, component);

        if (action != null) {
            String listenerType = getActionEventName(ListenerTools.ATTRIBUTE_NAME_SPACE);
            if (listenerType == null) {
                throw new FacesException("Unknown listenerType for action=\""
                        + action.getExpressionString() + "\".");
            }

            if (listenersByType.containsKey(listenerType) == false) {
                listenersByType = Collections.singletonMap(listenerType,
                        EventsRenderer.getSubmitJavaScriptListeners());
            }
        }

        if (listenersByType.isEmpty() == false) {
            StringAppender sa = new StringAppender(128);

            IRenderContext renderContext = writer.getComponentRenderContext()
                    .getRenderContext();
            for (Iterator it = listenersByType.entrySet().iterator(); it
                    .hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                String listenerType = (String) entry.getKey();
                FacesListener listeners[] = (FacesListener[]) entry.getValue();

                boolean submitSupport = true;
                if (ListenerTools.ATTRIBUTE_NAME_SPACE.getValidationEventName()
                        .equals(listenerType)) {
                    submitSupport = false;
                }

                EventsRenderer.encodeAttributeEventListeners(renderContext, sa,
                        listenerType, listeners, submitSupport);
            }

            if (sa.length() > 0) {
                writer.writeAttribute("v:events", sa.toString());
                initJavascript = true;
            }
        }

        if (initJavascript) {
            writer.enableJavaScript();
        }

        return writer;
    }

    protected void encodeJavaScript(IJavaScriptWriter writer)
            throws WriterException {
    }

    protected void encodeClientData(IJavaScriptWriter writer)
            throws WriterException {
        IClientDataCapability clientDataCapability = (IClientDataCapability) writer
                .getHtmlComponentRenderContext().getComponent();

        String keys[] = clientDataCapability.listClientDataKeys();
        if (keys == null || keys.length < 1) {
            return;
        }

        writer.writeMethodCall("f_setClientData");

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (key == null || key.length() < 1) {
                continue;
            }

            if (i > 0) {
                writer.write(',');
            }

            writer.writeString(key);

            String value = clientDataCapability.getClientData(key);

            writer.write(',');
            writer.writeString(value);
        }

        writer.writeln(");");
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        super.encodeEnd(writer);

        encodeEndJavaScript((IHtmlWriter) writer);
    }

    protected void encodeEndJavaScript(IHtmlWriter writer)
            throws WriterException {
        IHtmlRenderContext htmlRenderContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext();
        UIComponent component = htmlRenderContext.getComponent();

        IJavaScriptWriter js = htmlRenderContext.removeJavaScriptWriter(writer);
        if (js != null) {
            // Le Javascript writer a �t� referm�, on ignore dans ce cas ...

            js = null;
        }

        boolean enableJavascript = false;

        MethodBinding action = null;
        Map listenersByType = null;
        if (ENCODE_EVENT_ATTRIBUTE == false
                && htmlRenderContext.getProcessContext().isDesignerMode() == false) {
            // On recherche l'attribut Action
            if (component instanceof UICommand) {
                action = ((UICommand) component).getAction();
                if (action != null) {
                    enableJavascript = true;
                }
            }

            // On recherche les ActionListeners
            listenersByType = ListenerTools.getListenersByType(
                    ListenerTools.JAVASCRIPT_NAME_SPACE, component);
            if (listenersByType.size() > 0 || action != null) {
                enableJavascript = true;
            }
        }

        IJavaScriptRenderContext javascriptRenderContext = htmlRenderContext
                .getJavaScriptRenderContext();
        javascriptRenderContext.computeRequires(writer, this);
        if (javascriptRenderContext.isRequiresPending()) {
            // Rien on peut utiliser le tag v:init !
            enableJavascript = true;
        }

        boolean javaScriptStubForced = javascriptRenderContext
                .isJavaScriptStubForced();
        if (js == null) {
            if (enableJavascript == false) {
                enableJavascript = writer.isJavaScriptEnabled();
            }

            if (javaScriptStubForced) {
                IRCFacesComponent cameliaParent = null;

                for (UIComponent parent = component.getParent(); parent != null; parent = parent
                        .getParent()) {

                    if (parent instanceof IRCFacesComponent) {
                        cameliaParent = (IRCFacesComponent) parent;
                        break;
                    }
                }

                if (cameliaParent != null) {
                    javaScriptStubForced = false;

                } else {
                    enableJavascript = true;
                }
            }

            if (enableJavascript) {
                js = htmlRenderContext.getJavaScriptWriter(writer, this);
            }
        }

        if (js != null) {
            if (javaScriptStubForced) {
                js.ensureInitialization();

                javascriptRenderContext.clearJavaScriptStubForced();
            }

            try {
                encodeJavaScript(js);

                if ((listenersByType != null && listenersByType.isEmpty() == false)
                        || action != null) {

                    String actionListenerType = null;
                    if (action != null) {
                        actionListenerType = getActionEventName(ListenerTools.JAVASCRIPT_NAME_SPACE);
                        if (actionListenerType == null) {
                            throw new FacesException(
                                    "Unknown listenerType for action=\""
                                            + action.getExpressionString()
                                            + "\".");
                        }
                    }

                    EventsRenderer.encodeEventListeners(js, js
                            .getComponentVarName(), listenersByType,
                            actionListenerType);
                }

                if (hasComponenDecoratorSupport()) {
                    IComponentDecorator componentDecorator = getComponentDecorator(writer
                            .getComponentRenderContext());
                    if (componentDecorator != null) {
                        componentDecorator.encodeJavaScript(js);
                    }
                }

            } finally {
                js.end();
            }
        }
    }

    protected IHtmlWriter writeAccessKey(IHtmlWriter writer,
            IAccessKeyCapability accessKeyCapability) throws WriterException {
        if (useHtmlAccessKeyAttribute()) {
            return super.writeAccessKey(writer, accessKeyCapability);
        }

        String ak = accessKeyCapability.getAccessKey();

        if (ak != null) {
            writer.writeAttribute("v:accessKey", ak);
            writer.enableJavaScript();
        }

        return writer;
    }

    protected boolean useHtmlAccessKeyAttribute() {
        return false;
    }

    protected final IHtmlWriter writeHelp(IHtmlWriter writer,
            IHelpCapability helpComponent) {
        if ((helpComponent.getHelpURL() != null)
                || (helpComponent.getHelpMessage() != null)) {
            writer.enableJavaScript();
        }

        return writer;
    }

    protected final void setAlreadyLazyComponent(IHtmlWriter writer) {
        writer.getComponentRenderContext().setAttribute(ALREADY_LAZY_OBJECT,
                Boolean.TRUE);
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        String className = getJavaScriptClassName();
        if (className != null) {
            classes.add(className);
        }
    }
}
