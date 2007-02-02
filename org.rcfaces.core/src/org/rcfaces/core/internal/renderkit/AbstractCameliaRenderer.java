/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IVariableScopeCapability;
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
            throw new WriterException(e.getMessage(), e, component);
        }

        writer.endComponent();
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

        ValueBinding valueBinding = variableScopeCapability.getScopeValue();
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

        writer.endComponent();

        renderContext.encodeEnd(component);
        
        try {
            encodeEnd(writer);

        } catch (RuntimeException e) {
            throw new WriterException(e.getMessage(), e, component);
        }

        super.encodeEnd(context, component);

        writer.endComponent();

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
            UIComponent component, Object value) {
        if (component instanceof ValueHolder) {
            ValueHolder valueHolder = (ValueHolder) component;

            return ValuesTools.valueToString(valueHolder, facesContext);
        }

        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        return ValuesTools.convertStringToValue(context, component,
                submittedValue);
    }

    protected static final Renderer getRenderer(FacesContext facesContext,
            UIComponent component) {
        String rendererType = component.getRendererType();
        if (rendererType == null) {
            LOG.error("Invalid renderType for component id="
                    + component.getId() + " component=" + component);
            return null;
        }

        RenderKitFactory renderKitFactory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);

        RenderKit renderKit = renderKitFactory.getRenderKit(facesContext,
                facesContext.getViewRoot().getRenderKitId());

        return renderKit.getRenderer(component.getFamily(), rendererType);

    }
}