package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessageDialogComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.MessageDialogDecorator;

public class MessageDialogRenderer extends AbstractSelectItemsRenderer {
	
	public static final String JS_CLASS = "f_messageDialog";
	public static final String JS_SUBMITVALUE_CALLBACK = JS_CLASS+".SubmitValue";

	protected IComponentDecorator createComponentDecorator(FacesContext facesContext, UIComponent component) {
		return createMessageBoxDecorator(facesContext, component);
	}

    protected IComponentDecorator createMessageBoxDecorator(FacesContext facesContext, UIComponent component) {

		if (((MessageDialogComponent)component).isVisible(facesContext)) {
	        return new MessageDialogDecorator(component);
		}
		return null;
    }
    
	protected boolean sendCompleteComponent() {
		return false;
	}

    protected String getJavaScriptClassName() {
        return JS_CLASS;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer.getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();
        MessageDialogComponent component = (MessageDialogComponent) componentRenderContext.getComponent();
        
		// Pas visible : rien à faire !
		if (!component.isVisible(facesContext)) {
			super.encodeEnd(writer);
			return;
		}
		
		IHtmlWriter htmlWriter = (IHtmlWriter) writer;
		
		htmlWriter.enableJavaScript();

        htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);

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

        htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

        setAlreadyLazyComponent(htmlWriter);

        super.encodeEnd(htmlWriter);

    }

}
