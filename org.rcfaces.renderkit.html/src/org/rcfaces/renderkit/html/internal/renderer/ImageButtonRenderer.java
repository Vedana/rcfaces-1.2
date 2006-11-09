/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.AbstractImageButtonFamillyDecorator;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageButtonRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    public static final String IMAGE_BUTTON_WRITER = "camelia.writer.ImageButton";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_BUTTON;
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        if (hasComponenDecoratorSupport() == false) {
            encodeComponent((IHtmlWriter) writer);
        }

        // Il faut activer le Javascript
        // car l'attribut SELECTED doit être envoyé a chaque requete du client vers le serveur !
        ((IHtmlWriter) writer).enableJavaScript();

        super.encodeEnd(writer);
    }

    protected void encodeComponent(IHtmlWriter writer) throws WriterException {
        throw new WriterException("Render is not implemented !", null, writer
                .getComponentRenderContext().getComponent());
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new ImageButtonDecorator((IImageButtonFamilly) component);
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class ImageButtonDecorator extends
            AbstractImageButtonFamillyDecorator {
        private static final String REVISION = "$Revision$";

        public ImageButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void writeAttributes(String classSuffix)
                throws WriterException {
            writeHtmlAttributes(writer);
            writeJavaScriptAttributes(writer);
            writeCssAttributes(writer, classSuffix, ~CSS_FONT_MASK);

            FacesContext facesContext = writer.getComponentRenderContext()
                    .getFacesContext();
            encodeAttributes(facesContext);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            if (imageButtonFamilly.isDisabled(facesContext)) {
                writer.writeAttribute("v:disabled", "true");
            }

            if (imageButtonFamilly.isReadOnly(facesContext)) {
                writer.writeAttribute("v:readOnly", "true");
            }
        }
    }
}