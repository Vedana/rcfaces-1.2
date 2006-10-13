/*
 * $Id$
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
public class HtmlRenderContext extends AbstractRenderContext implements
        IHtmlRenderContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(HtmlRenderContext.class);

    protected static final String RENDER_CONTEXT = "camelia.render.html.context";

    private static final String JAVASCRIPT_CONTEXT = "camelia.html.javascript.context";

    private static final String JAVASCRIPT_WRITER = "camelia.html.javascript.writer";

    private List interactiveRenderComponents;

    private IAsyncRenderComponent lastInteractiveRenderComponent;

    private String lastInteractiveRenderComponentClientId;

    private IJavaScriptRenderContext javascriptRenderContext;

    private boolean noLazyTag = false;

    private boolean asyncRenderer = false;

    private boolean javaScriptEnabled = false;

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
        HtmlRenderContext renderContext = (HtmlRenderContext) getRenderContext(facesContext);

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

    public String getComponentClientId(FacesContext facesContext,
            UIComponent component) {
        if (htmlExternalContext.isFlatIdentifierEnabled()) {
            String id = component.getId();

            if (id == null || id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
                return component.getClientId(facesContext);
            }

            return id;
        }

        return component.getClientId(facesContext);
    }

    public String computeBrotherComponentClientId(FacesContext facesContext,
            UIComponent brotherComponent, String componentId) {
        if (htmlExternalContext.isFlatIdentifierEnabled()) {
            return componentId;
        }

        String brotherComponentId = brotherComponent.getClientId(facesContext);

        if (Constants.PARAMETERIZED_SEPARATOR_SUPPORT) {
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

        public HtmlWriterImpl(FacesContext facesContext,
                HtmlRenderContext context) {
            super(facesContext, context);
        }

        public void enableJavaScript() {
            ((HtmlRenderContext) renderContext).enableJavaScript();
        }

        public boolean isJavaScriptEnabled() {
            return ((HtmlRenderContext) renderContext).isJavaScriptEnabled();
        }

        public IHtmlWriter writeMaxLength(int maxLength) throws WriterException {
            writeAttribute("maxlength", maxLength);

            return this;
        }

        public IHtmlWriter writeSize(int size) throws WriterException {
            writeAttribute("size", size);

            return this;
        }

        public IHtmlWriter writeType(String type) throws WriterException {
            writeAttribute("type", type);

            return this;
        }

        public IHtmlWriter writeId(String id) throws WriterException {
            writeAttribute("id", id);

            return this;
        }

        public IHtmlWriter writeName(String name) throws WriterException {
            writeAttribute("name", name);

            return this;
        }

        public IHtmlWriter writeClass(String className) throws WriterException {
            writeAttribute("class", className);

            return this;
        }

        public IHtmlWriter writeDisabled() throws WriterException {
            writeAttribute("DISABLED");

            return this;
        }

        public IHtmlWriter writeReadOnly() throws WriterException {
            writeAttribute("READONLY");

            return this;
        }

        public IHtmlWriter writeValue(String value) throws WriterException {
            writeAttribute("value", value);

            return this;
        }

        public IHtmlWriter writeAccessKey(String accessKey)
                throws WriterException {
            writeAttribute("accessKey", accessKey);

            return this;
        }

        public IHtmlWriter writeTabIndex(int tabIndex) throws WriterException {
            writeAttribute("tabIndex", tabIndex);

            return this;
        }

    }

    public IHtmlProcessContext getHtmlProcessContext() {
        return htmlExternalContext;
    }

    public boolean isJavaScriptEnabled() {
        return javaScriptEnabled;
    }

    public void enableJavaScript() {
        javaScriptEnabled = true;
    }

    public IProcessContext getProcessContext() {
        return htmlExternalContext;
    }

    public boolean isAsyncRenderEnable() {
        return asyncRenderer;
    }
}