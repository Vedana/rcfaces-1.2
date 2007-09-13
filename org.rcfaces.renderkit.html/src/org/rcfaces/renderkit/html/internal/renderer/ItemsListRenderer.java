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
 * @author Frederic Lefevere (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ItemsListRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeBeforeDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(writer, componentDecorator);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ItemsListComponent ItemsListComponent = (ItemsListComponent) componentRenderContext
                .getComponent();

        writer.startElement(IHtmlWriter.DIV);

        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        if (ItemsListComponent.isDisabled(facesContext)) {
            writer.writeAttribute("v:disabled", true);
        }
        if (ItemsListComponent.isReadOnly(facesContext)) {
            writer.writeAttribute("v:readOnly", true);
        }

        /*
         * String className = getMainStyleClassName(); // Un dummy pour eviter
         * des sauts de pages writer.startElement(IHtmlWriter.A"); String cls =
         * className + "_itemFolder"; writer.writeAttribute("class", cls);
         * writer.endElement(IHtmlWriter.A");
         */

        writer.startElement(IHtmlWriter.TABLE);
        writer.startElement(IHtmlWriter.TBODY);
    }

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

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ItemsListDecorator(component);
    }
    
}