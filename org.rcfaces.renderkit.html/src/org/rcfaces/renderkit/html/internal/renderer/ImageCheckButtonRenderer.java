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
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.ns.INamespaceConfiguration;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageCheckButtonRenderer extends ImageButtonRenderer {

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_CHECK_BUTTON;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        IImageButtonFamilly imageButtonCapability = (IImageButtonFamilly) component;

        Boolean selected = componentData.getBooleanProperty("selected");
        if (selected != null) {
            decodeSelection(context.getFacesContext(), imageButtonCapability,
                    selected.booleanValue());
        }

        // System.out.println("Component:"+component.getId()+"
        // Selected="+selected+" : "+componentData.getProperty("selected"));
    }

    protected void decodeSelection(FacesContext facesContext,
            IImageButtonFamilly imageButtonCapability, boolean selected) {
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

        public ImageCheckButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected String getRole() {
            return IAccessibilityRoles.CHECK_BOX;
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
            writer.writeAttributeNS("selected", true);
        }
    }

    public void declare(INamespaceConfiguration nameSpaceProperties) {
        super.declare(nameSpaceProperties);

        nameSpaceProperties.addAttributes(null, new String[] { "selected" });
    }
}
