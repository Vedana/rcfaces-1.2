/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.FieldSetComponent;
import org.rcfaces.core.component.MessageFieldSetComponent;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.util.JavaScriptTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessageFieldSetRenderer extends FieldSetRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MESSAGE_FIELD_SET;
    }

    protected void writeFieldSetAttributes(IHtmlWriter htmlWriter,
            FieldSetComponent fieldSetComponent) throws WriterException {
        super.writeFieldSetAttributes(htmlWriter, fieldSetComponent);

        MessageFieldSetComponent messageFieldSetComponent = (MessageFieldSetComponent) fieldSetComponent;

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        writeSeverityStyleClasses(htmlWriter, messageFieldSetComponent);

        writeSeverityImages(htmlWriter, messageFieldSetComponent);

        String forValue = messageFieldSetComponent.getFor(facesContext);
        if (forValue != null) {
            htmlWriter.writeAttribute("v:for", forValue);
        }

        htmlWriter.enableJavaScript();
    }

    protected void encodeJavaScript(IJavaScriptWriter js)
            throws WriterException {
        super.encodeJavaScript(js);

        JavaScriptTools.writeFirstMessage(js);
    }

}
