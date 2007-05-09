package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessageDialogComponent;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.MessageDialogDecorator;

/**
 * 
 * @author Fred (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessageDialogRenderer extends AbstractSelectItemsRenderer {

    public static final String JS_SUBMITVALUE_CALLBACK = JavaScriptClasses.MESSAGE_DIALOG
            + ".SubmitValue";

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return createMessageBoxDecorator(facesContext, component);
    }

    protected IComponentDecorator createMessageBoxDecorator(
            FacesContext facesContext, UIComponent component) {

        if (((MessageDialogComponent) component).isVisible(facesContext)) {
            return new MessageDialogDecorator(component);
        }
        return null;
    }

    protected boolean sendCompleteComponent() {
        return false;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MESSAGE_DIALOG;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();
        MessageDialogComponent component = (MessageDialogComponent) componentRenderContext
                .getComponent();

        // Pas visible : rien à faire !
        if (!component.isVisible(facesContext) && component.getHiddenMode(facesContext)==IHiddenModeCapability.SERVER_HIDDEN_MODE) {
            super.encodeEnd(writer);
            return;
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.enableJavaScript();

        htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        String chaine = component.getTitle(facesContext);
        if (chaine != null) {
            htmlWriter.writeAttribute("v:title", chaine);
        }
        chaine = component.getText(facesContext);
        if (chaine != null) {
            htmlWriter.writeAttribute("v:text", chaine);
        }
        chaine = component.getDefaultValue(facesContext);
        if (chaine != null) {
            htmlWriter.writeAttribute("v:defaultValue", chaine);
        }
        chaine = component.getWidth(facesContext);
        if (chaine != null) {
            htmlWriter.writeAttribute("v:width", chaine);
        }
        chaine = component.getHeight(facesContext);
        if (chaine != null) {
            htmlWriter.writeAttribute("v:height", chaine);
        }
        chaine = component.getWaiRole(facesContext);
        if (chaine != null) {
            htmlWriter.writeAttribute("v:waiRole", chaine);
        }

        htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

        setAlreadyLazyComponent(htmlWriter);

        super.encodeEnd(htmlWriter);

    }

}
