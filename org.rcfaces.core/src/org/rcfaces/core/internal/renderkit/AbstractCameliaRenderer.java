/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.internal.tools.AsyncModeTools;
import org.rcfaces.core.internal.tools.ValuesTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractCameliaRenderer extends Renderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractCameliaRenderer.class);

    private static final String HIDE_CHILDREN_PROPERTY = "camelia.ASYNC_TREE_MODE";

    private static final String VARIABLE_SCOPE_PROPERTY = "camelia.VARIABLE_SCOPE";

    public final void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        super.encodeBegin(context, component);

        IRenderContext renderContext = getRenderContext(context);

        String clientId = renderContext.getComponentClientId(component);

        renderContext.pushComponent(component, clientId);

        IComponentWriter writer = renderContext.getComponentWriter();
        try {
            encodeBegin(writer);

        } catch (RuntimeException e) {
            LOG.error("Encode begin of component '" + clientId
                    + "' throws an exception.", e);

            throw new WriterException(null, e, component);
        }

        if (writer instanceof ISgmlWriter) {
            ((ISgmlWriter) writer).endComponent();
        }
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();
        if ((component instanceof IVariableScopeCapability) == false) {
            return;
        }

        IVariableScopeCapability variableScopeCapability = (IVariableScopeCapability) component;

        String var = variableScopeCapability.getScopeVar();
        if (var == null || var.length() < 1) {
            return;
        }

        Object valueBinding = variableScopeCapability.getScopeValue();
        if (valueBinding == null) {
            return;
        }

        writer.getComponentRenderContext().getRenderContext().pushScopeVar(var,
                valueBinding);

        writer.getComponentRenderContext().setAttribute(
                VARIABLE_SCOPE_PROPERTY, var);
    }

    protected abstract IRenderContext getRenderContext(FacesContext context);

    protected void hideChildren(IComponentRenderContext componentRenderContext) {
        componentRenderContext.setAttribute(HIDE_CHILDREN_PROPERTY,
                Boolean.TRUE);
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {
        if ((this instanceof IAsyncRenderer) == false) {
            super.encodeChildren(facesContext, component);
            return;
        }

        IRenderContext renderContext = getRenderContext(facesContext);

        IComponentWriter componentWriter = renderContext.getComponentWriter();

        IComponentRenderContext componentRenderContext = componentWriter
                .getComponentRenderContext();

        if (componentRenderContext.containsAttribute(HIDE_CHILDREN_PROPERTY)) {
            return;
        }

        super.encodeChildren(facesContext, component);
    }

    public boolean getRendersChildren() {
        if ((this instanceof IAsyncRenderer) == false) {
            return false;
        }

        if (RcfacesContext.isJSF1_2()) {
            // En jsf 1.2 nous sommes forcement en parcours d'arbre
            return true;
        }
        
        // Jsf 1.1 : on doit distinguer d'un parcours par TAG ou par programmation

        if (AsyncModeTools.isTagProcessor(null)) {
            // Nous sommes en mode TAG, c'est le tag qui détourne le flux.
            // NON => pas forcement, pas en mode tree !
            return false;
        }

        // Nous sommes en mode de rendu par parcours d'arbre ...
        return true;
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        IRenderContext renderContext = getRenderContext(context);

        IComponentWriter writer = renderContext.getComponentWriter();

        if (writer instanceof ISgmlWriter) {
            ((ISgmlWriter) writer).endComponent();
        }

        renderContext.encodeEnd(component);

        try {
            encodeEnd(writer);

        } catch (RuntimeException e) {

            String clientId = renderContext.getComponentClientId(component);

            LOG.error("Encode end of component '" + clientId
                    + "' throws an exception.", e);

            throw new WriterException(null, e, component);
        }

        super.encodeEnd(context, component);

        if (writer instanceof ISgmlWriter) {
            ((ISgmlWriter) writer).endComponent();
        }

        renderContext.popComponent(component);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        String scopeVar = (String) writer.getComponentRenderContext()
                .getAttribute(VARIABLE_SCOPE_PROPERTY);
        if (scopeVar == null) {
            return;
        }

        writer.getComponentRenderContext().getRenderContext().popScopeVar(
                scopeVar);
    }

    protected abstract IRequestContext getRequestContext(FacesContext context);

    public final void decode(FacesContext context, UIComponent component) {

        IRequestContext requestContext = getRequestContext(context);

        String requestComponentId = getRequestComponentId(requestContext,
                component);

        IComponentData componentData = requestContext.getComponentData(
                component, requestComponentId);

        decode(requestContext, component, componentData);
    }

    protected String getRequestComponentId(IRequestContext requestContext,
            UIComponent component) {
        return requestContext.getComponentId(component);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
    }

    protected void decodeEvent(IRequestContext context, UIComponent component,
            IEventData eventData) {
    }

    public void decodeChildren(FacesContext context, UIComponent component) {
        for (Iterator children = component.getFacetsAndChildren(); children
                .hasNext();) {
            UIComponent child = (UIComponent) children.next();

            decodeChild(context, component, child);
        }
    }

    public void decodeChild(FacesContext context, UIComponent parent,
            UIComponent child) {
        child.processDecodes(context);
    }

    public void decodeEnd(FacesContext context, UIComponent component) {
    }

    public boolean getDecodesChildren() {
        return false;
    }

    protected String convertValue(FacesContext facesContext,
            ValueHolder valueHolder) {
        return ValuesTools.valueToString(valueHolder, facesContext);
    }

    protected String convertValue(FacesContext facesContext,
            UIComponent component, Object value) {
        return ValuesTools.valueToString(value, component, facesContext);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        return ValuesTools.convertStringToValue(context, component,
                submittedValue, true);
    }

    protected static final Renderer getRenderer(FacesContext facesContext,
            UIComponent component) {
        String rendererType = component.getRendererType();
        if (rendererType == null) {
            LOG.error("Invalid renderType for component id="
                    + component.getId() + " component=" + component);
            return null;
        }

        RenderKit renderKit = facesContext.getRenderKit();

        if (renderKit == null) {
            LOG.error("No renderKit associated to renderKitId='"
                    + facesContext.getViewRoot().getRenderKitId() + "'.");

            return null;
        }

        Renderer renderer = renderKit.getRenderer(component.getFamily(),
                rendererType);

        if (LOG.isDebugEnabled()) {
            LOG.debug("getRenderer(id='" + component.getId() + " family='"
                    + component.getFamily() + "' rendererType='" + rendererType
                    + "' class='" + component.getClass().getName()
                    + "') for renderKitId='"
                    + facesContext.getViewRoot().getRenderKitId() + "' => "
                    + renderer);
        }

        return renderer;
    }

    protected Object getValue(UIComponent component) {
        return ValuesTools.getValue(component);
    }
}