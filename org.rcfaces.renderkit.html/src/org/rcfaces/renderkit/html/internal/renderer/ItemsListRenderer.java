/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ItemsListComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ItemsListDecorator;

/**
 * 
 * @author Fred Lefevere-Laoid (latest modification by $Author$)
 * @version $Revision$ $Date$
 * @see ItemsToolFolderRenderer
 */
public class ItemsListRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

    /**
     * surrounding for the decorator
     * @param writer writer
     * @param componentDecorator decorator
     * @throws WriterException 
     */
    protected void encodeBeforeDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(writer, componentDecorator);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ItemsListComponent itemsListComponent = (ItemsListComponent) componentRenderContext
                .getComponent();

        writer.startElement(IHtmlWriter.DIV);

        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        if (itemsListComponent.isDisabled(facesContext)) {
            writer.writeAttribute("v:disabled", true);
        }
        if (itemsListComponent.isReadOnly(facesContext)) {
            writer.writeAttribute("v:readOnly", true);
        }

        writer.startElement(IHtmlWriter.TABLE);
        writer.startElement(IHtmlWriter.TBODY);
    }

    /**
     * surrounding for the decorator
     * @param writer writer
     * @param componentDecorator decorator
     * @throws WriterException 
     */
    protected void encodeAfterDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {

        writer.endElement(IHtmlWriter.TBODY);
        writer.endElement(IHtmlWriter.TABLE);
        writer.endElement(IHtmlWriter.DIV);

        super.encodeAfterDecorator(writer, componentDecorator);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.ITEMS_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#getDecodesChildren()
     */
    public boolean getDecodesChildren() {
        return true;
    }

    /**
     * back from the client
     * @param context context
     * @param component component
     * @param componentData componentData
     */
    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);
    }

    /**
     * Creates the decorator
     * @param facesContext facesContext
     * @param component component
     */
    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ItemsListDecorator(component);
    }
    
}