/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.HiddenValueComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HiddenValueRenderer extends AbstractJavaScriptRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        HiddenValueComponent hiddenValueComponent = (HiddenValueComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("INPUT");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);

        htmlWriter.writeName(componentRenderContext.getComponentClientId());

        htmlWriter.writeType("hidden");

        Object value = hiddenValueComponent.getValue();
        if (value != null) {
            String svalue = convertValue(facesContext, hiddenValueComponent,
                    value);

            if (svalue != null) {
                htmlWriter.writeValue(svalue);
            }
        }

        htmlWriter.endElement("INPUT");

        super.encodeEnd(htmlWriter);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        HiddenValueComponent hiddenValueComponent = (HiddenValueComponent) component;

        String newValue = componentData.getComponentParameter();

        if (newValue != null) {
            hiddenValueComponent.setSubmittedValue(newValue);
        }
    }

    protected void writeInputAttributes(IHtmlWriter htmlWriter) {
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.HIDDEN_VALUE;
    }

    protected boolean sendCompleteComponent() {
        return false;
    }
}
