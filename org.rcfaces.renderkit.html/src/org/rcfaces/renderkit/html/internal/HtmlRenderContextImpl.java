/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/05 08:57:14  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.2  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.1  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.24  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.23  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.22  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.21  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.20  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.19  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.18  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.17  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.16  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.15  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.14  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.13  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.12  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.11  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.10  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.9  2004/09/17 15:59:32  oeuillot
 * *** empty log message ***
 *
 * Revision 1.8  2004/09/17 15:18:43  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.internal.renderkit.AbstractRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.service.AbstractAsyncRenderService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlRenderContextImpl extends AbstractRenderContext implements
        IHtmlRenderContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(HtmlRenderContextImpl.class);

    protected static final String RENDER_CONTEXT = "camelia.render.html.context";

    private static final String JAVASCRIPT_CONTEXT = "camelia.html.javascript.context";

    private static final String JAVASCRIPT_WRITER = "camelia.html.javascript.writer";

    private List interactiveRenderComponents;

    private IAsyncRenderComponent lastInteractiveRenderComponent;

    private String lastInteractiveRenderComponentClientId;

    private IJavaScriptRenderContext javascriptRenderContext;

    private boolean noLazyTag = false;

    private boolean asyncRenderer = false;

    private IHtmlProcessContext htmlExternalContext;

    public void initialize(FacesContext facesContext) {
        super.initialize(facesContext);

        AbstractAsyncRenderService service = AbstractAsyncRenderService
                .getInstance(facesContext);
        if (service != null) {
            asyncRenderer = service.isAsyncRenderEnable();
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialize htmlRenderContext asyncRenderer="
                    + asyncRenderer + ".");
        }

        htmlExternalContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);
    }

    public IScriptRenderContext getScriptRenderContext() {
        return getJavaScriptRenderContext();
    }

    public final IJavaScriptRenderContext getJavaScriptRenderContext() {
        if (javascriptRenderContext != null) {
            return javascriptRenderContext;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();

        Map requestMap = facesContext.getExternalContext().getRequestMap();

        javascriptRenderContext = (IJavaScriptRenderContext) requestMap
                .get(JAVASCRIPT_CONTEXT);
        if (javascriptRenderContext != null) {
            return javascriptRenderContext;
        }

        javascriptRenderContext = allocateJavaScriptContext(facesContext);

        requestMap.put(JAVASCRIPT_CONTEXT, javascriptRenderContext);

        return javascriptRenderContext;
    }

    protected IJavaScriptRenderContext allocateJavaScriptContext(
            FacesContext facesContext) {
        return new JavaScriptRenderContext(facesContext);
    }

    static final IRenderContext getRenderContext(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();

        IRenderContext renderContext = (IRenderContext) requestMap
                .get(RENDER_CONTEXT);
        if (renderContext != null) {
            return renderContext;
        }

        renderContext = createRenderContext(context);
        requestMap.put(RENDER_CONTEXT, renderContext);

        return renderContext;
    }

    public static final IRenderContext restoreRenderContext(
            FacesContext facesContext, Object state, boolean noLazyTag) {
        HtmlRenderContextImpl renderContext = (HtmlRenderContextImpl) getRenderContext(facesContext);

        renderContext.restoreState(state);

        if (noLazyTag) {
            renderContext.noLazyTag = true;
        }

        return renderContext;
    }

    protected void restoreState(Object state) {
        Object ret[] = (Object[]) state;
        if (ret == null) {
            return;
        }

        if (ret[0] != null) {
            IJavaScriptRenderContext javaScriptRenderContext = getJavaScriptRenderContext();
            javaScriptRenderContext.restoreState(ret[0]);
        }
    }

    public Object saveRenderContextState() {
        Object ret[] = new Object[2];

        if (javascriptRenderContext != null) {
            ret[0] = javascriptRenderContext.saveState();
        }

        return ret;
    }

    /*
     * public static final void releaseRenderContext(IRenderContext
     * renderContext) { FacesContext facesContext =
     * renderContext.getFacesContext();
     * 
     * Map requestMap = facesContext.getExternalContext().getRequestMap();
     * 
     * IRenderContext rc = (IRenderContext) requestMap.get(RENDER_CONTEXT); if
     * (rc != renderContext) { return; }
     * 
     * requestMap.remove(RENDER_CONTEXT); }
     */

    static final IRenderContext createRenderContext(FacesContext context) {
        HtmlRenderContextImpl hrc = new HtmlRenderContextImpl();
        hrc.initialize(context);

        return hrc;
    }

    public final IJavaScriptWriter removeJavaScriptWriter(IHtmlWriter writer) {
        return (IJavaScriptWriter) writer.getComponentRenderContext()
                .removeAttribute(JAVASCRIPT_WRITER);
    }

    public final IJavaScriptWriter getJavaScriptWriter(IHtmlWriter writer,
            IJavaScriptComponent javaScriptComponent) throws WriterException {
        IJavaScriptWriter js = (IJavaScriptWriter) writer
                .getComponentRenderContext().getAttribute(JAVASCRIPT_WRITER);
        if (js != null) {
            return js;
        }

        JavaScriptWriterImpl jsImpl = new JavaScriptWriterImpl();
        jsImpl.setWriter(writer, javaScriptComponent,
                getJavaScriptRenderContext(), htmlExternalContext
                        .useMetaContentScriptType(), htmlExternalContext
                        .useScriptCData());

        writer.getComponentRenderContext().setAttribute(JAVASCRIPT_WRITER,
                jsImpl);

        return jsImpl;
    }

    public void pushInteractiveRenderComponent(IHtmlWriter writer) {
        if (interactiveRenderComponents == null) {
            interactiveRenderComponents = new LinkedList();
        }

        interactiveRenderComponents.add(lastInteractiveRenderComponent);
        interactiveRenderComponents.add(lastInteractiveRenderComponentClientId);
        interactiveRenderComponents.add(javascriptRenderContext);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        lastInteractiveRenderComponent = (IAsyncRenderComponent) componentRenderContext
                .getComponent();
        lastInteractiveRenderComponentClientId = componentRenderContext
                .getComponentId();
        javascriptRenderContext = javascriptRenderContext.pushInteractive();
    }

    public IAsyncRenderComponent getCurrentInteractiveRenderComponent() {
        return lastInteractiveRenderComponent;
    }

    public String getCurrentInteractiveRenderComponentClientId() {
        return lastInteractiveRenderComponentClientId;
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component) {
        if (lastInteractiveRenderComponent != null) {
            if (lastInteractiveRenderComponent == component) {

                lastInteractiveRenderComponent = (IAsyncRenderComponent) interactiveRenderComponents
                        .remove(0);
                lastInteractiveRenderComponentClientId = (String) interactiveRenderComponents
                        .remove(0);
                javascriptRenderContext = (IJavaScriptRenderContext) interactiveRenderComponents
                        .remove(0);
            }
        }

        super.encodeEnd(facesContext, component);
    }

    public boolean canUseLazyTag() {
        if (noLazyTag) {
            return false;
        }

        return getCurrentInteractiveRenderComponent() == null;
    }

    public String getComponentId(FacesContext facesContext,
            UIComponent component) {
        if (htmlExternalContext.isFlatIdentifierEnabled()) {

            String id = component.getId();

            if (id == null || id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
                return component.getClientId(facesContext);
            }

            return id;
        }

        String id = component.getClientId(facesContext);

        char separatorChar = htmlExternalContext.getNamingSeparatorChar();
        if (separatorChar == NamingContainer.SEPARATOR_CHAR) {
            return id;
        }

        return id.replace(NamingContainer.SEPARATOR_CHAR, separatorChar);

    }

    public String computeBrotherComponentId(FacesContext facesContext,
            UIComponent brotherComponent, String componentId) {
        if (htmlExternalContext.isFlatIdentifierEnabled()) {
            return componentId;
        }

        String id = super.computeBrotherComponentId(facesContext,
                brotherComponent, componentId);

        char separatorChar = htmlExternalContext.getNamingSeparatorChar();
        if (separatorChar == NamingContainer.SEPARATOR_CHAR || id == null) {
            return id;
        }

        return id.replace(NamingContainer.SEPARATOR_CHAR, separatorChar);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.render.Renderer#convertClientId(javax.faces.context.FacesContext,
     *      java.lang.String)
     * 
     * public String convertClientId(FacesContext context, String clientId) {
     * int idx = clientId.lastIndexOf(NamingContainer.SEPARATOR_CHAR); if (idx <
     * 0 || idx == clientId.length()) { return clientId; }
     * 
     * String id = clientId.substring(idx + 1); if
     * (id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) { return clientId; }
     * 
     * return id; }
     */
    protected IComponentWriter createWriter(FacesContext facesContext,
            UIComponent component) {
        return new HtmlWriterImpl(facesContext, this);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class HtmlWriterImpl extends AbstractHtmlWriter {
        private static final String REVISION = "$Revision$";

        private static final String ENABLE_JAVASCRIPT = "camelia.html.javascript.enable";

        private static final String FORCE_JAVASCRIPT_STUB = "camelia.html.javascript.forceStub";

        private boolean javaScriptEnabled = false;

        public HtmlWriterImpl(FacesContext facesContext,
                HtmlRenderContextImpl context) {
            super(facesContext, context);
        }

        public void enableJavaScript() {
            if (javaScriptEnabled) {
                return;
            }

            javaScriptEnabled = true;
            getComponentRenderContext().setAttribute(ENABLE_JAVASCRIPT,
                    Boolean.TRUE);
        }

        public boolean isJavaScriptStubForced() {
            return getComponentRenderContext().containsAttribute(
                    FORCE_JAVASCRIPT_STUB);
        }

        public void forceJavaScriptStub() {
            enableJavaScript();

            getComponentRenderContext().setAttribute(FORCE_JAVASCRIPT_STUB,
                    Boolean.TRUE);
        }

        public boolean isJavaScriptEnabled() {
            if (javaScriptEnabled) {
                return true;
            }

            javaScriptEnabled = getComponentRenderContext().containsAttribute(
                    ENABLE_JAVASCRIPT);

            return javaScriptEnabled;
        }
    }

    public IHtmlProcessContext getHtmlExternalContext() {
        return htmlExternalContext;
    }

    public IProcessContext getExternalContext() {
        return htmlExternalContext;
    }

    public boolean isAsyncRenderEnable() {
        return asyncRenderer;
    }
}