/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.FieldSetComponent;
import org.rcfaces.core.component.MessageFieldSetComponent;
import org.rcfaces.core.internal.component.ISeverityImageAccessors;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.ns.INamespaceConfiguration;

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

        ISeverityImageAccessors accessors = (ISeverityImageAccessors) messageFieldSetComponent
                .getImageAccessors(facesContext);

        writeSeverityImages(htmlWriter, accessors);

        String forValue = messageFieldSetComponent.getFor(facesContext);
        if (forValue != null) {
            htmlWriter.writeAttributeNS("for", forValue);
        }

        if (messageFieldSetComponent.isShowActiveComponentMessage(facesContext)) {
            htmlWriter.writeAttributeNS("showActiveComponentMessage", true);
        }

        htmlWriter.getJavaScriptEnableMode().enableOnMessage(); // Pour les
                                                                // messages
    }

    /*
     * protected void encodeJavaScript(IJavaScriptWriter js) throws
     * WriterException { super.encodeJavaScript(js);
     * 
     * JavaScriptTools.writeFirstMessage(js); }
     */

    public void declare(INamespaceConfiguration nameSpaceProperties) {
        super.declare(nameSpaceProperties);

        nameSpaceProperties.addAttributes(null, new String[] { "for",
                "showActiveComponentMessage" });
    }
}
