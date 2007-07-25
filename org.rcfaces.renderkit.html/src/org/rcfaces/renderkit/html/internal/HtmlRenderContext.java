/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;
import org.rcfaces.core.internal.renderkit.AbstractRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.service.AbstractAsyncRenderService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlRenderContext extends AbstractRenderContext implements
        IHtmlRenderContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(HtmlRenderContext.class);

    private static final String META_DATA_INITIALIZED_PROPERTY = "org.rcfaces.renderkit.html.META_DATA_INITIALIZED";

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

    private String invalidBrowserURL;

    private boolean disabledContentMenu;

    private Set clientMessageIdFilter;

    private boolean clientMessageIdFilterReadOnly;

    private String waiRolesNS = null;

    private boolean clientValidation = true;

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

    static final IHtmlRenderContext getRenderContext(FacesContext context) {
        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        Map requestMap = context.getExternalContext().getRequestMap();

        IHtmlRenderContext renderContext = (IHtmlRenderContext) requestMap
                .get(RENDER_CONTEXT);
        if (renderContext != null) {
            return renderContext;
        }

        renderContext = createRenderContext(context);
        requestMap.put(RENDER_CONTEXT, renderContext);

        return renderContext;
    }

    public static final IHtmlRenderContext restoreRenderContext(
            FacesContext facesContext, Object state, boolean noLazyTag) {
        HtmlRenderContext renderContext = (HtmlRenderContext) getRenderContext(facesContext);

        renderContext.restoreState(state);

        if (noLazyTag) {
            renderContext.noLazyTag = true;
        }

        return renderContext;
    }

    public void restoreState(Object state) {
        Object ret[] = (Object[]) state;
        if (ret == null) {
            return;
        }

        super.restoreState(ret[0]);

        if (ret[1] != null) {
            IJavaScriptRenderContext javaScriptRenderContext = getJavaScriptRenderContext();
            javaScriptRenderContext.restoreState(ret[1]);
        }

        waiRolesNS = (String) ret[2];
    }

    public Object saveRenderContextState() {
        Object ret[] = new Object[3];

        ret[0] = super.saveRenderContextState();

        if (javascriptRenderContext != null) {
            ret[1] = javascriptRenderContext.saveState();
        }

        ret[2] = waiRolesNS;

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

    static final IHtmlRenderContext createRenderContext(FacesContext context) {
        HtmlRenderContext hrc = new HtmlRenderContext();
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
                .getComponentClientId();
        javascriptRenderContext = javascriptRenderContext.pushInteractive();
    }

    public IAsyncRenderComponent getCurrentInteractiveRenderComponent() {
        return lastInteractiveRenderComponent;
    }

    public String getCurrentInteractiveRenderComponentClientId() {
        return lastInteractiveRenderComponentClientId;
    }

    public void popInteractiveRenderComponent() {
        if (interactiveRenderComponents.isEmpty()) {
            throw new IllegalStateException(
                    "No more elements into interactive render components");
        }

        lastInteractiveRenderComponent = (IAsyncRenderComponent) interactiveRenderComponents
                .remove(0);
        lastInteractiveRenderComponentClientId = (String) interactiveRenderComponents
                .remove(0);
        javascriptRenderContext = (IJavaScriptRenderContext) interactiveRenderComponents
                .remove(0);
    }

    public void encodeEnd(UIComponent component) {
        if (lastInteractiveRenderComponent == component) {
            popInteractiveRenderComponent();
        }

        super.encodeEnd(component);
    }

    public boolean canUseLazyTag() {
        if (noLazyTag) {
            return false;
        }

        return getCurrentInteractiveRenderComponent() == null;
    }

    public String getComponentClientId(UIComponent component) {
        if (htmlExternalContext.isFlatIdentifierEnabled()) {
            String id = component.getId();

            if (id == null || id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
                return component.getClientId(getFacesContext());
            }

            return id;
        }

        return component.getClientId(getFacesContext());
    }

    public String computeBrotherComponentClientId(UIComponent brotherComponent,
            String componentId) {
        if (htmlExternalContext.isFlatIdentifierEnabled()) {
            return componentId;
        }

        String brotherComponentId = brotherComponent
                .getClientId(getFacesContext());

        if (Constants.CLIENT_NAMING_SEPARATOR_SUPPORT) {
            String separatorChar = htmlExternalContext.getNamingSeparator();

            if (separatorChar != null) {
                int idx = brotherComponentId.lastIndexOf(separatorChar);
                for (; idx > 0; idx--) {
                    if (brotherComponentId.indexOf(separatorChar, idx - 1) != idx - 1) {
                        break;
                    }
                }

                return brotherComponentId.substring(0, idx
                        + separatorChar.length())
                        + componentId;
            }
        }

        int idx = brotherComponentId
                .lastIndexOf(NamingContainer.SEPARATOR_CHAR);
        if (idx < 0) {
            return componentId;
        }

        return brotherComponentId.substring(0, idx + 1) + componentId;
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
    protected IComponentWriter createWriter(UIComponent component) {
        return new HtmlWriterImpl(this);
    }

    public IComponentWriter createWriter(UIComponent component,
            ResponseWriter responseWriter) {
        return new HtmlWriterImpl(this, responseWriter);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class HtmlWriterImpl extends AbstractHtmlWriter {
        private static final String REVISION = "$Revision$";

        private static final String ENABLE_JAVASCRIPT = "camelia.html.javascript.enable";

        private boolean javaScriptEnabled = false;

        public HtmlWriterImpl(HtmlRenderContext context) {
            this(context, context.getFacesContext().getResponseWriter());
        }

        protected HtmlWriterImpl(HtmlRenderContext context,
                ResponseWriter responseWriter) {
            super(context, responseWriter);

            javaScriptEnabled = ((ITransientAttributesManager) getComponent())
                    .getTransientAttribute(ENABLE_JAVASCRIPT) != null;
        }

        public boolean isJavaScriptEnabled() {
            return javaScriptEnabled;
        }

        public void enableJavaScript() {
            if (javaScriptEnabled) {
                return;
            }

            javaScriptEnabled = true;

            ((ITransientAttributesManager) getComponent())
                    .setTransientAttribute(ENABLE_JAVASCRIPT, Boolean.TRUE);
        }
    }

    public IHtmlProcessContext getHtmlProcessContext() {
        return htmlExternalContext;
    }

    public IProcessContext getProcessContext() {
        return htmlExternalContext;
    }

    public boolean isAsyncRenderEnable() {
        return asyncRenderer;
    }

    public static void setMetaDataInitialized(FacesContext facesContext) {
        facesContext.getExternalContext().getRequestMap().put(
                META_DATA_INITIALIZED_PROPERTY, Boolean.TRUE);
    }

    public String getInvalidBrowserURL() {
        return invalidBrowserURL;
    }

    public boolean isDisabledContextMenu() {
        return disabledContentMenu;
    }

    public void setDisabledContextMenu(boolean state) {
        this.disabledContentMenu = state;
    }

    public void setInvalidBrowserURL(String invalidBrowserURL) {
        this.invalidBrowserURL = invalidBrowserURL;
    }

    public Set getClientMessageIdFilters() {
        if (clientMessageIdFilter == null) {
            return Collections.EMPTY_SET;
        }

        return clientMessageIdFilter;
    }

    public void setClientMessageId(Set ids) {
        clientMessageIdFilter = ids;
        clientMessageIdFilterReadOnly = true;
    }

    public void addClientMessageIds(Set ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        if (clientMessageIdFilter == null) {
            clientMessageIdFilter = new HashSet(ids.size());

        } else if (clientMessageIdFilterReadOnly) {
            clientMessageIdFilter = new HashSet(clientMessageIdFilter);
            clientMessageIdFilterReadOnly = false;
        }

        for (Iterator it = ids.iterator(); it.hasNext();) {
            String id = (String) it.next();

            if (ALL_CLIENT_MESSAGES.equals(id)) {
                clientMessageIdFilter.remove(NO_CLIENT_MESSAGES);
                return;
            }

            clientMessageIdFilter.add(id);
        }
    }

    public String getWaiRolesNS() {
        return waiRolesNS;
    }

    public void setWaiRolesNS(String waiRolesNS) {
        this.waiRolesNS = waiRolesNS;
    }

    public void setClientValidation(boolean clientValidation) {
        this.clientValidation = clientValidation;
    }

    public boolean isClientValidation() {
        return clientValidation;
    }

}