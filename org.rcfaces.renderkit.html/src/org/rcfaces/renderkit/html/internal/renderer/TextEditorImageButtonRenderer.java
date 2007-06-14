/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextEditorImageButtonComponent;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEditorImageButtonRenderer extends ImageButtonRenderer {
    private static final String REVISION = "$Revision$";

    private static final int TEXT_EDITOR_IMAGE_BUTTON_WIDTH = 16;

    private static final int TEXT_EDITOR_IMAGE_BUTTON_HEIGHT = 16;

    private static final Map IMAGE_URL_BY_TYPE = new HashMap(24);
    static {
        IMAGE_URL_BY_TYPE.put("bold", "textEditor/bold.gif");
        IMAGE_URL_BY_TYPE.put("italic", "textEditor/italic.gif");
        IMAGE_URL_BY_TYPE.put("underline", "textEditor/underline.gif");
        IMAGE_URL_BY_TYPE.put("strike", "textEditor/strike.gif");

        IMAGE_URL_BY_TYPE.put("subscript", "textEditor/subScript.gif");
        IMAGE_URL_BY_TYPE.put("superscript", "textEditor/superScript.gif");

        IMAGE_URL_BY_TYPE.put("left", "textEditor/left.gif");
        IMAGE_URL_BY_TYPE.put("right", "textEditor/right.gif");
        IMAGE_URL_BY_TYPE.put("center", "textEditor/center.gif");

        IMAGE_URL_BY_TYPE.put("undo", "textEditor/undo.gif");
        IMAGE_URL_BY_TYPE.put("redo", "textEditor/redo.gif");

        IMAGE_URL_BY_TYPE.put("indent", "textEditor/indent.gif");
        IMAGE_URL_BY_TYPE.put("outdent", "textEditor/outdent.gif");

        IMAGE_URL_BY_TYPE.put("orderedlist", "textEditor/orderedList.gif");
        IMAGE_URL_BY_TYPE.put("unorderedlist", "textEditor/unorderedList.gif");
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_EDITOR_IMAGE_BUTTON;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        FacesContext facesContext = componentRenderContext.getFacesContext();
        TextEditorImageButtonComponent textEditorImageButtonComponent = (TextEditorImageButtonComponent) componentRenderContext
                .getComponent();

        textEditorImageButtonComponent.setDisabled(true);

        String type = textEditorImageButtonComponent.getType(facesContext);
        if (type == null) {
            throw new FacesException(
                    "Type attribute is required for an ImageTextEditorButton.");
        }

        type = type.toLowerCase().trim();

        if (IMAGE_URL_BY_TYPE.containsKey(type) == false) {
            throw new FacesException("Unsupported type '" + type
                    + "' for an ImageTextEditorButton.");
        }

        super.encodeEnd(writer);
    }

    protected int getTextEditorImageWidth(IHtmlWriter htmlWriter) {
        return TEXT_EDITOR_IMAGE_BUTTON_WIDTH;
    }

    protected int getTextEditorImageHeight(IHtmlWriter htmlWriter) {
        return TEXT_EDITOR_IMAGE_BUTTON_HEIGHT;
    }

    protected IContentAccessor getTextEditorImageAccessor(IHtmlWriter htmlWriter) {

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        String type = ((TextEditorImageButtonComponent) htmlWriter
                .getComponentRenderContext().getComponent())
                .getType(htmlRenderContext.getFacesContext());

        String imageURL = (String) IMAGE_URL_BY_TYPE.get(type.toLowerCase());
        if (imageURL == null) {
            return null;
        }

        return htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetContentAccessor(imageURL, IContentType.IMAGE);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new TextEditorImageButtonDecorator(
                (IImageButtonFamilly) component);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class TextEditorImageButtonDecorator extends ImageButtonDecorator {
        private static final String REVISION = "$Revision$";

        private IContentAccessor imageAccessor;

        public TextEditorImageButtonDecorator(
                IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            super.encodeAttributes(facesContext);

            TextEditorImageButtonComponent button = (TextEditorImageButtonComponent) imageButtonFamilly;

            String type = button.getType(facesContext);
            if (type != null) {
                writer.writeAttribute("v:type", type);
            }

            String forProperty = button.getFor(facesContext);
            if (forProperty != null) {
                writer.writeAttribute("v:for", forProperty);
            }
        }

        protected IContentAccessor getImageAccessor(IHtmlWriter htmlWriter) {
            if (imageAccessor != null) {
                return imageAccessor;
            }

            imageAccessor = super.getImageAccessor(htmlWriter);
            if (imageAccessor != null) {
                return imageAccessor;
            }

            imageAccessor = getTextEditorImageAccessor(htmlWriter);

            imageButtonFamilly
                    .setImageWidth(getTextEditorImageWidth(htmlWriter));
            imageButtonFamilly
                    .setImageHeight(getTextEditorImageHeight(htmlWriter));

            return imageAccessor;
        }

        protected IContentAccessor getTextEditorImageAccessor(
                IHtmlWriter htmlWriter) {
            return TextEditorImageButtonRenderer.this
                    .getTextEditorImageAccessor(htmlWriter);
        }

        protected int getTextEditorImageHeight(IHtmlWriter htmlWriter) {
            return TextEditorImageButtonRenderer.this
                    .getTextEditorImageHeight(htmlWriter);
        }

        protected int getTextEditorImageWidth(IHtmlWriter htmlWriter) {
            return TextEditorImageButtonRenderer.this
                    .getTextEditorImageWidth(htmlWriter);
        }
    }
}
