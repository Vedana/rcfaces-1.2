/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ImagePagerButtonComponent;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImagePagerButtonRenderer extends ImageButtonRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_PAGER_BUTTON;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();
        ImagePagerButtonComponent imagePagerButtonComponent = (ImagePagerButtonComponent) componentRenderContext
                .getComponent();

        imagePagerButtonComponent.setDisabled(true);
        if (imagePagerButtonComponent.isHideIfDisabled(facesContext)) {
            imagePagerButtonComponent.setVisible(false);
        }

        super.encodeEnd(writer);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new PagerImageButtonDecorator((IImageButtonFamilly) component);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class PagerImageButtonDecorator extends ImageButtonDecorator {
        private static final String REVISION = "$Revision$";

        public PagerImageButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            super.encodeAttributes(facesContext);

            ImagePagerButtonComponent button = (ImagePagerButtonComponent) imageButtonFamilly;

            String type = button.getType(facesContext);
            if (type != null) {
                writer.writeAttribute("v:type", type);
            }

            String forProperty = button.getFor(facesContext);
            if (forProperty != null) {
                writer.writeAttribute("v:for", forProperty);
            }

            boolean hideIfDisabled = button.isHideIfDisabled(facesContext);
            if (hideIfDisabled) {
                writer.writeAttribute("v:hideIfDisabled", true);
            }
        }
    }
}