/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ServiceComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ServiceRenderer extends AbstractJavaScriptRenderer {
    private static final String REVISION = "$Revision$";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ServiceComponent focusManagerComponent = (ServiceComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        String serviceId = focusManagerComponent.getServiceId(facesContext);
        if (serviceId != null) {
            htmlWriter.writeAttribute("v:serviceId", serviceId);
        }

        htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

        declareLazyJavaScriptRenderer(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SERVICE;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        // FacesContext facesContext = context.getFacesContext();

        ServiceComponent serviceComponent = (ServiceComponent) component;

        String filterExpression = componentData
                .getStringProperty("filterExpression");
        if (filterExpression != null) {
            if (filterExpression.length() < 1) {
                filterExpression = null;
            }

            serviceComponent.setFilterProperties(HtmlTools
                    .decodeFilterExpression(context.getProcessContext(),
                            component, filterExpression));
        }

    }

    protected boolean sendCompleteComponent(
            IHtmlComponentRenderContext htmlComponentContext) {
        return false;
    }
}
