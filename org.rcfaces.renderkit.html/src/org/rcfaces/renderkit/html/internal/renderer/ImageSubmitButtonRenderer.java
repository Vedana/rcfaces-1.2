/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageSubmitButtonRenderer extends ImageButtonRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_SUBMIT_BUTTON;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new ImageSubmitButtonDecorator((IImageButtonFamilly) component);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class ImageSubmitButtonDecorator extends ImageButtonDecorator {
        private static final String REVISION = "$Revision$";

        public ImageSubmitButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected String getInputElement() {
            return "INPUT";
        }
    }
}
