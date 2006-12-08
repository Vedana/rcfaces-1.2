/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.HelpButtonComponent;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HelpButtonRenderer extends ImageButtonRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.HELP_BUTTON;
    }

    public void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.enableJavaScript();

        super.encodeEnd(writer);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new HelpButtonDecorator((IImageButtonFamilly) component);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class HelpButtonDecorator extends ImageButtonDecorator {
        private static final String REVISION = "$Revision$";

        public HelpButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            HelpButtonComponent helpButtonComponent = (HelpButtonComponent) imageButtonFamilly;

            String ac = ((IForCapability) imageButtonFamilly).getFor();

            if (ac != null) {
                IComponentRenderContext componentRenderContext = writer
                        .getComponentRenderContext();

                IRenderContext renderContext = componentRenderContext
                        .getRenderContext();

                String forId = renderContext.computeBrotherComponentClientId(
                        helpButtonComponent, ac);

                if (forId != null) {
                    writer.writeAttribute("v:for", forId);
                }
            }
        }

    }
}
