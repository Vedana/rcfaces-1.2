/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.3  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.24  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.23  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.22  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.21  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.20  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.19  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.18  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.17  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.16  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.15  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.14  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.13  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.12  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.11  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.10  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.9  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.8  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.7  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.6  2005/03/18 18:03:41  oeuillot
 * Ameliration du look du TabbedPane !
 *
 * Revision 1.5  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.4  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.3  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.2  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.1  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
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
import org.rcfaces.core.internal.component.IRCFacesComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractJavaScriptRenderer extends AbstractHtmlRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractJavaScriptRenderer.class);

    // private static final String JAVASCRIPT_INITIALIZED =
    // "camelia.html.javascript.initialized";

    // private static final boolean FORCE_JAVASCRIPT_WRITER = false;

    // private static final String FIRST_COMPONENT = "camelia.component.first";

    // private static final String LAZY_COMPONENTS = "camelia.component.lazy";

    static final String LAZY_INIT_TAG = "v:init";

    // private static final String INIT_BY_NAME = "javascript.InitByName";

    private static final boolean ENCODE_EVENT_ATTRIBUTE = true;

    private static final String ALREADY_LAZY_OBJECT = "camelia.already.lazy";

    private static final boolean LAZY_TAG_USE_BROTHER = true;

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

        writer.getJavaScriptRenderContext()
                .initializeJavaScriptDocument(writer);
    }

    protected IJavaScriptWriter writeJsInitComponent(IJavaScriptWriter writer)
            throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        String componentId = componentRenderContext.getComponentId();

        IJavaScriptRenderContext javascriptRenderContext = ((IHtmlRenderContext) componentRenderContext
                .getRenderContext()).getJavaScriptRenderContext();

        boolean declare[] = new boolean[1];
        String componentVarName = javascriptRenderContext
                .allocateComponentVarId(componentId, declare);

        writer.setComponentVarName(componentVarName);

        if (declare[0]) {
            writer.write("var ").write(componentVarName);

            writer.write('=').writeCall("_classLoader", "_init");
            writer.writeString(componentId);
            writer.writeln(");");

            return writer;
        }

        writer.writeCall("_classLoader", "_init");
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

        writer.writeCall("_classLoader", "_initializeObjects").writeln(");");
    }

    public void initializeJavaScriptComponent(IJavaScriptWriter writer)
            throws WriterException {

        initializePendingComponents(writer);

        String javaScriptClassName = getJavaScriptClassName();
        if (javaScriptClassName == null) {
            return;
        }

        writeJsInitComponent(writer);

        String componentVarName = writer.getComponentVarName();

        // writer.write("with(").write(componentVarName).writeln("){");

        IHtmlComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        if (componentRenderContext.hasClientDatas(true)) {
            encodeClientData(writer);
        }
    }

    public void releaseJavaScript(IJavaScriptWriter writer)
            throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
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

                    if (LAZY_TAG_USE_BROTHER == false) {
                        w.writeAttribute("rid", componentRenderContext
                                .getComponentId());
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
                    if (writer.getComponentRenderContext().hasClientDatas(true)) {
                        writeClientData(w,
                                (IClientDataCapability) componentRenderContext
                                        .getComponent());
                    }

                    w.endElement(LAZY_INIT_TAG);
                }

                javascriptRenderContext
                        .pushUnitializedComponent(componentRenderContext
                                .getComponentId());

                return;
            }
        }

        if (sendCompleteComponent()) {
            writer.writeMethodCall("_completeComponent").writeln(");");
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

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

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
            StringAppender sb = new StringAppender(128);

            for (Iterator it = listenersByType.entrySet().iterator(); it
                    .hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                String listenerType = (String) entry.getKey();
                FacesListener listeners[] = (FacesListener[]) entry.getValue();

                EventsRenderer.encodeAttributeEventListeners(sb, listenerType,
                        listeners);
            }

            if (sb.length() > 0) {
                writer.writeAttribute("v:events", sb.toString());
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
                .getComponentRenderContext().getComponent();

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

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.CameliaRenderer#encodeEnd(org.rcfaces.core.internal.renderkit.IRenderContext)
     */
    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        super.encodeEnd(writer);

        encodeEndJavaScript((IHtmlWriter) writer);
    }

    protected void encodeEndJavaScript(IHtmlWriter writer)
            throws WriterException {
        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(writer);
        UIComponent component = htmlRenderContext.getComponent();

        IJavaScriptWriter js = htmlRenderContext.removeJavaScriptWriter(writer);
        if (js != null) {
            // Le Javascript writer a �t� referm�, on ignore dans ce cas ...

            js = null;
        }

        boolean enableJavascript = false;

        MethodBinding action = null;
        Map listenersByType = null;
        if (ENCODE_EVENT_ATTRIBUTE == false) {
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
