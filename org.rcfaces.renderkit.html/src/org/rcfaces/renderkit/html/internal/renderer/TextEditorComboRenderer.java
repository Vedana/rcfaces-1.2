/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextEditorComboComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.ns.INamespaceConfiguration;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEditorComboRenderer extends ComboRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        TextEditorComboComponent component = (TextEditorComboComponent) writer
                .getComponentRenderContext().getComponent();

        component.setDisabled(true);

        super.encodeBegin(writer);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_EDITOR_COMBO;
    }

    protected void writeComboAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        super.writeComboAttributes(htmlWriter);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        TextEditorComboComponent combo = (TextEditorComboComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        String type = combo.getType(facesContext);
        if (type != null) {
            htmlWriter.writeAttributeNS("type", type);
        }

        String forProperty = combo.getFor(facesContext);
        if (forProperty != null) {
            htmlWriter.writeAttributeNS("for", forProperty);

            htmlWriter.getJavaScriptEnableMode().enableOnInit(); // Pour
                                                                 // positionner
                                                                 // la combo
                                                                 // en
                                                                 // fonction
                                                                 // de l'état
                                                                 // de la
                                                                 // target
        }
    }

    public void declare(INamespaceConfiguration nameSpaceProperties) {
        super.declare(nameSpaceProperties);

        nameSpaceProperties.addAttributes(null, new String[] { "type", "for" });
    }
}
