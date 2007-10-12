package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ViewDialogComponent;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

public class ViewDialogRenderer extends AbstractJavaScriptRenderer {

    public ViewDialogRenderer() {
        super();
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();
        ViewDialogComponent component = (ViewDialogComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.enableJavaScript();

        htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        String width = component.getWidth(facesContext);
        if (width != null) {
            htmlWriter.writeAttribute("v:width", width);
        }
        String height = component.getHeight(facesContext);
        if (height != null) {
            htmlWriter.writeAttribute("v:height", height);
        }
        String text = component.getText(facesContext);
        if (text != null) {
            htmlWriter.writeAttribute("v:title", text);
        }
        String src = component.getViewURL(facesContext);
        if (src != null) {
            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src, IContentType.USER);

            src = contentAccessor.resolveURL(facesContext, null, null);
            if (src != null) {
                htmlWriter.writeAttribute("v:viewURL", src);
            }
        }
        if (!component.isVisible(facesContext)) {
            htmlWriter.writeAttribute("v:visible", false);
        }
        if (component.isDialogPrioritySetted()) {
            htmlWriter.writeAttribute("v:dialogPriority", component
                    .getDialogPriority(facesContext));
        }
        htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

        setAlreadyLazyComponent(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.VIEW_DIALOG;
    }

    protected boolean sendCompleteComponent() {
        return false;
    }

}
