/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageCheckButtonRenderer extends ImageButtonRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_CHECK_BUTTON;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        IImageButtonFamilly imageButtonCapability = (IImageButtonFamilly) component;

        Boolean selected = componentData.getBooleanProperty("selected");
        if (selected != null) {
            decodeSelection(imageButtonCapability, selected.booleanValue());
        }

        // System.out.println("Component:"+component.getId()+"
        // Selected="+selected+" : "+componentData.getProperty("selected"));
    }

    protected void decodeSelection(IImageButtonFamilly imageButtonCapability,
            boolean selected) {
        ((ISelectedCapability) imageButtonCapability).setSelected(selected);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new ImageCheckButtonDecorator((IImageButtonFamilly) component);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class ImageCheckButtonDecorator extends ImageButtonDecorator {
        private static final String REVISION = "$Revision$";

        public ImageCheckButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            super.encodeAttributes(facesContext);

            encodeSelectedAttribute(facesContext);
        }

        protected void encodeSelectedAttribute(FacesContext facesContext)
                throws WriterException {
            if (isSelected((ISelectedCapability) imageButtonFamilly) == false) {
                return;
            }
            writer.writeAttribute("v:selected", "true");
        }
    }
}
