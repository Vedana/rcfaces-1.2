/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ItemsToolFolderComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
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

        ItemsToolFolderComponent itemsToolFolderComponent = (ItemsToolFolderComponent) componentRenderContext
                .getComponent();

        writer.startElement(IHtmlWriter.UL);

        int cellPadding = itemsToolFolderComponent.getToolBar().getItemPadding(
                facesContext);
        if (cellPadding < 0) {
            cellPadding = 0;
        }
        // writer.writeCellPadding(cellPadding);

       // writer.writeCellSpacing(0);

        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        if (itemsToolFolderComponent.isDisabled(facesContext)) {
            writer.writeAttribute("v:disabled", true);
        }
        if (itemsToolFolderComponent.isReadOnly(facesContext)) {
            writer.writeAttribute("v:readOnly", true);
        }

        String verticalAlignment = itemsToolFolderComponent
                .getVerticalAlignment(facesContext);
        if (verticalAlignment != null) {
            writer.writeVAlign(verticalAlignment);
        }

        /*
         * String className = getMainStyleClassName(); // Un dummy pour eviter
         * des sauts de pages writer.startElement(IHtmlWriter.A"); String cls =
         * className + "_itemFolder"; writer.writeAttribute("class", cls);
         * writer.endElement(IHtmlWriter.A");
         */

       // writer.startElement(IHtmlWriter.TBODY);

        // writer.startElement(IHtmlWriter.TR);
    }

    protected void encodeAfterDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {

        writer.endElement(IHtmlWriter.UL);

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

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ItemsToolFolderDecorator(component);
    }
}