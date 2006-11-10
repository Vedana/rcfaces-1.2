/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ImageRadioButtonComponent;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageRadioButtonRenderer extends ImageCheckButtonRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ImageRadioButtonRenderer.class);

    protected void decodeSelection(IImageButtonFamilly imageButtonCapability,
            boolean selected) {
        super.decodeSelection(imageButtonCapability, selected);

        ImageRadioButtonComponent imageRadioButtonComponent = (ImageRadioButtonComponent) imageButtonCapability;
        Object radioValue = imageRadioButtonComponent.getRadioValue();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Decode selection of componentId='"
                    + ((UIComponent) imageRadioButtonComponent).getId()
                    + "' radioValue=" + radioValue + " selected=" + selected);
        }

        if (radioValue == null) {
            return;
        }

        // La selection pouvait être déjà faite !
        if (imageRadioButtonComponent.isSelected()) {
            imageRadioButtonComponent.setSubmittedValue(radioValue);
        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_RADIO_BUTTON;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ImageRadioButtonWriter((IImageButtonFamilly) component);
    }

    protected boolean isSelected(
            ImageRadioButtonComponent imageRadioButtonComponent,
            FacesContext facesContext) {
        Object radioValue = imageRadioButtonComponent.getRadioValue();
        if (radioValue == null) {
            return imageRadioButtonComponent.isSelected(facesContext);
        }

        Object currentValue = imageRadioButtonComponent.getValue();
        return radioValue.equals(currentValue);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class ImageRadioButtonWriter extends ImageCheckButtonDecorator {
        private static final String REVISION = "$Revision$";

        public ImageRadioButtonWriter(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            super.encodeAttributes(facesContext);

            String groupName = ((IRadioGroupCapability) imageButtonFamilly)
                    .getGroupName();
            if (groupName != null) {

                groupName = HtmlTools.computeGroupName(writer
                        .getHtmlComponentRenderContext().getHtmlRenderContext()
                        .getHtmlProcessContext(),
                        (UIComponent) imageButtonFamilly, groupName);

                writer.writeAttribute("v:groupName", groupName);
            }
        }

        protected boolean isSelected(ISelectedCapability imageButtonFamilly) {
            return ImageRadioButtonRenderer.this.isSelected(
                    (ImageRadioButtonComponent) imageButtonFamilly, null);
        }
    }
}
