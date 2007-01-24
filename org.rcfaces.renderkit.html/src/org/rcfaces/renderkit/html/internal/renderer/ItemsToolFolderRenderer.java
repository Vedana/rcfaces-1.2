/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.rcfaces.core.component.ItemsToolFolderComponent;
import org.rcfaces.core.event.ItemActionEvent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IEventData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ItemsToolFolderDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ItemsToolFolderRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeBeforeDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(writer, componentDecorator);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ItemsToolFolderComponent toolBarComponent = (ItemsToolFolderComponent) componentRenderContext
                .getComponent();

        writer.startElement("TABLE");

        int cellPadding = toolBarComponent.getItemPadding(facesContext);
        if (cellPadding < 0) {
            cellPadding = 0;
        }
        writer.writeCellPadding(cellPadding);

        writer.writeCellSpacing(0);

        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        if (toolBarComponent.isDisabled(facesContext) == false) {
            writer.writeAttribute("v:disabled", "true");
        }
        if (toolBarComponent.isReadOnly(facesContext)) {
            writer.writeAttribute("v:readOnly", "true");
        }

        /*
         * String className = getMainStyleClassName(); // Un dummy pour eviter
         * des sauts de pages writer.startElement("A"); String cls = className +
         * "_itemFolder"; writer.writeAttribute("class", cls);
         * writer.endElement("A");
         */

        writer.startElement("TR");
    }

    protected void encodeAfterDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {

        writer.endElement("TR");

        writer.endElement("TABLE");

        super.encodeAfterDecorator(writer, componentDecorator);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.ITEMS_TOOL_FOLDER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#getDecodesChildren()
     */
    public boolean getDecodesChildren() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decodeEvent(org.rcfaces.core.internal.renderkit.IRequestContext,
     *      javax.faces.component.UIComponent, java.lang.String,
     *      java.lang.String)
     */
    protected void decodeEvent(IRequestContext context, UIComponent component,
            IEventData eventData) {

        if (eventData != null
                && JavaScriptClasses.EVENT_SELECTION.equals(eventData
                        .getEventName())) {

            Object value = eventData.getEventValue();

            // TODO: Il faut converir la value de l'item ...

            ActionEvent actionEvent = new ItemActionEvent(component, value);
            component.queueEvent(actionEvent);

            return;
        }

        super.decodeEvent(context, component, eventData);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ItemsToolFolderDecorator(component);
    }
}