/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import org.rcfaces.core.component.FieldSetComponent;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.renderkit.border.ITitledBorderRenderer;
import org.rcfaces.renderkit.html.internal.border.IHtmlBorderRenderer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FieldSetRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final String BORDER_RENDERER = "camelia.customButton.borderRenderer";

    private static final String CELL_BODY = "_cellBody";

    private static final String DIV_BODY = "_cellBody";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FieldSetComponent component = (FieldSetComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeFieldSetTop(htmlWriter, component);
    }

    protected void encodeFieldSetTop(IHtmlWriter htmlWriter,
            FieldSetComponent fieldSetComponent) throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        htmlWriter.startElement("DIV");

        writeFieldSetAttributes(htmlWriter, fieldSetComponent);

        String textAlignement = fieldSetComponent
                .getTextAlignment(facesContext);
        String verticalAlignement = fieldSetComponent
                .getVerticalAlignment(facesContext);

        IHtmlBorderRenderer borderRenderer = getHtmlBorderRenderer(htmlWriter,
                fieldSetComponent);

        if (borderRenderer != null) {
            componentRenderContext
                    .setAttribute(BORDER_RENDERER, borderRenderer);

            String width = fieldSetComponent.getWidth(facesContext);
            String height = fieldSetComponent.getHeight(facesContext);

            borderRenderer.initialize(htmlWriter, width, height, 1, 1, false,
                    false);

            if (borderRenderer instanceof ITitledBorderRenderer) {
                ((ITitledBorderRenderer) borderRenderer).setText(htmlWriter,
                        fieldSetComponent.getText(facesContext));
            }

            borderRenderer.startComposite(htmlWriter);

            borderRenderer.startRow(htmlWriter);

            borderRenderer.startChild(htmlWriter, CELL_BODY, textAlignement,
                    verticalAlignement, null, null, 1, 1);
        }

        String className = getStyleClassName(componentRenderContext,
                fieldSetComponent);

        htmlWriter.startElement("DIV").writeAttribute("class",
                className + DIV_BODY);

        if (borderRenderer == null) {
            // On aligne par CSS ...

            ICssWriter cssWriter = new CssWriter(htmlWriter, 64);

            if (textAlignement != null) {
                cssWriter.writeTextAlign(textAlignement);
            }

            if (verticalAlignement != null) {
                cssWriter.writeVerticalAlign(verticalAlignement);
            }

            cssWriter.close();
        }
    }

    protected void writeFieldSetAttributes(IHtmlWriter htmlWriter,
            FieldSetComponent fieldSetComponent) throws WriterException {

        writeCoreAttributes(htmlWriter);
        String className = getStyleClassName(htmlWriter
                .getComponentRenderContext(), fieldSetComponent);
        htmlWriter.writeAttribute("class", className);

        writeJavaScriptAttributes(htmlWriter);

        ICssWriter cssWriter = new CssWriter(htmlWriter, 32);
        writeFiledSetCss(cssWriter, fieldSetComponent);
        cssWriter.close();
    }

    protected void writeFiledSetCss(ICssWriter cssWriter,
            FieldSetComponent fieldSetComponent) {
        cssWriter.writePosition(fieldSetComponent);
        cssWriter.writeSize(fieldSetComponent);
        cssWriter.writeMargin(fieldSetComponent);
        cssWriter.writeVisibility(fieldSetComponent);
        cssWriter.writeBackground(fieldSetComponent, null);
    }

    protected IHtmlBorderRenderer getHtmlBorderRenderer(IHtmlWriter writer,
            FieldSetComponent fieldSetComponent) {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        String borderType = fieldSetComponent.getBorderType(facesContext);

        IBorderRenderersRegistry borderRendererRegistry = RcfacesContext
                .getInstance(facesContext).getBorderRenderersRegistry();

        return (IHtmlBorderRenderer) borderRendererRegistry.getBorderRenderer(
                facesContext, RenderKitFactory.HTML_BASIC_RENDER_KIT,
                fieldSetComponent.getFamily(), fieldSetComponent
                        .getRendererType(), borderType);
    }

    protected String getDefaultBorderType(FieldSetComponent fieldSetComponent) {
        return "rounded";
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FieldSetComponent component = (FieldSetComponent) componentContext
                .getComponent();

        encodeFieldSetBottom((IHtmlWriter) writer, component);

        super.encodeEnd(writer);
    }

    protected void encodeFieldSetBottom(IHtmlWriter htmlWriter,
            FieldSetComponent component) throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        htmlWriter.endElement("DIV");

        IHtmlBorderRenderer borderRenderer = (IHtmlBorderRenderer) componentRenderContext
                .getAttribute(BORDER_RENDERER);

        if (borderRenderer != null) {
            borderRenderer.endChild(htmlWriter);

            borderRenderer.endRow(htmlWriter);

            borderRenderer.endComposite(htmlWriter);
        }

        htmlWriter.endElement("DIV");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(org.rcfaces.core.internal.renderkit.IRequestContext,
     *      javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FieldSetComponent fieldSetComponent = (FieldSetComponent) component;

        String text = componentData.getStringProperty("text");
        if (text != null) {
            String old = fieldSetComponent.getText();
            if (text.equals(old) == false) {
                fieldSetComponent.setText(text);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.TEXT, old, text));
            }
        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.FIELD_SET;
    }
}