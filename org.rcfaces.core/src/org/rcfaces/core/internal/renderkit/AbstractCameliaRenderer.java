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
import javax.faces.render.Renderer;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.rewriting.AbstractURLRewritingProvider;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.core.provider.IURLRewritingProvider.IURLRewritingInformation;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractCameliaRenderer extends Renderer {
    private static final String REVISION = "$Revision$";

    public final void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        super.encodeBegin(context, component);

        IRenderContext renderContext = getRenderContext(context);

        String id = renderContext.getComponentId(context, component);

        renderContext.pushComponent(context, component, id);

        IComponentWriter writer = renderContext.getComponentWriter(context);
        try {
            encodeBegin(writer);

        } catch (RuntimeException e) {
            throw new WriterException("RuntimeException", e, component);
        }

        writer.flush();
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
    }

    protected abstract IRenderContext getRenderContext(FacesContext context);

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        IRenderContext renderContext = getRenderContext(context);

        IComponentWriter writer = renderContext.getComponentWriter(context);

        renderContext.encodeEnd(context, component);
        try {
            encodeEnd(writer);

        } catch (RuntimeException e) {
            throw new WriterException("RuntimeException", e, component);
        }

        writer.flush();

        super.encodeEnd(context, component);

        renderContext.popComponent(component);

    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
    }

    protected abstract IRequestContext getRequestContext(FacesContext context);

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRendererExtension#decodeChildren(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void decodeChildren(FacesContext context, UIComponent component) {
        Iterator kids = component.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

            decodeChild(context, component, kid);
        }
    }

    public void decodeChild(FacesContext context, UIComponent parent,
            UIComponent child) {
        child.processDecodes(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRendererExtension#decodeEnd(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void decodeEnd(FacesContext context, UIComponent component) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRendererExtension#getDecodesChildren()
     */
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

    public static final String rewriteURL(
            IComponentRenderContext componentRenderContext, int type,
            String attributeName, String url, String rootURL,
            IURLRewritingInformation rewritingInformation) {
        if (Constants.URL_REWRITING_SUPPORT == false) {
            return url;
        }

        FacesContext facesContext = componentRenderContext.getFacesContext();

        IURLRewritingProvider urlRewritingProvider;
        IRenderContext renderContext = componentRenderContext
                .getRenderContext();
        if (renderContext != null) {
            urlRewritingProvider = renderContext.getURLRewritingProvider();

        } else {
            urlRewritingProvider = AbstractURLRewritingProvider
                    .getInstance(facesContext.getExternalContext());
        }

        if (urlRewritingProvider == null) {
            return url;
        }

        return urlRewritingProvider.computeURL(facesContext,
                componentRenderContext.getComponent(), type, attributeName,
                url, rootURL, rewritingInformation);
    }
}