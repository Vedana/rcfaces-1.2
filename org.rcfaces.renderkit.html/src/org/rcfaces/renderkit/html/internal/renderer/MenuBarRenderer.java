/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MenuBarComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.MenuBarDecorator;
import org.rcfaces.renderkit.html.internal.ns.INamespaceConfiguration;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MenuBarRenderer extends AbstractSelectItemsRenderer {

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MENU_BAR;
    }

    protected void encodeBeforeDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(writer, componentDecorator);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        writer.startElement(IHtmlWriter.DIV);

        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        MenuBarComponent menuComponent = (MenuBarComponent) componentRenderContext
                .getComponent();
        if (menuComponent.isDisabled(facesContext)) {
            writer.writeAttributeNS("disabled", true);
        }
        if (menuComponent.isReadOnly(facesContext)) {
            writer.writeAttributeNS("readOnly", true);
        }

        // Un dummy pour eviter des sauts de pages
        writer.startElement(IHtmlWriter.A);
        writer.writeClass(getBarItemClassName(writer));
        writer.endElement(IHtmlWriter.A);
    }

    private String getBarItemClassName(IHtmlWriter writer) {
        return getMainStyleClassName() + "_bitem";
    }

    protected void encodeAfterDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {

        writer.endElement(IHtmlWriter.DIV);

        super.encodeAfterDecorator(writer, componentDecorator);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new MenuBarDecorator(component);
    }

    public void declare(INamespaceConfiguration nameSpaceProperties) {
        super.declare(nameSpaceProperties);

        nameSpaceProperties.addAttributes(null, new String[] { "disabled",
                "readOnly" });
    }
}