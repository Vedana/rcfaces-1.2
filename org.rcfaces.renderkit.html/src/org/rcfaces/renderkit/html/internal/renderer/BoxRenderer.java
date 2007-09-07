/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.BoxComponent;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BoxRenderer extends AbstractCssRenderer implements IAsyncRenderer {
    private static final String REVISION = "$Revision$";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.DIV);

        writeComponentAttributes(htmlWriter);
    }

    protected void writeComponentAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        IHtmlRenderContext htmlRenderContext = componentRenderContext
                .getHtmlRenderContext();

 //       FacesContext facesContext = componentRenderContext.getFacesContext();

        BoxComponent box = (BoxComponent) componentRenderContext.getComponent();

        int asyncRender = IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE;

        boolean hidden = Boolean.FALSE.equals(box.getVisibleState());

        if (hidden) {
            if (htmlRenderContext.isAsyncRenderEnable()) {
                asyncRender=htmlRenderContext.getAsyncRenderMode(box);
                
                if (asyncRender != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    htmlWriter.writeAttribute("v:asyncRender", true);

                    htmlRenderContext
                            .pushInteractiveRenderComponent(htmlWriter);
                }
            }
        }

        setAsyncRenderer(htmlWriter, box, asyncRender);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;
        BoxComponent boxComponent = (BoxComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        htmlWriter.endElement(IHtmlWriter.DIV);

        IMenuIterator menuIterator = boxComponent.listMenus();
        if (menuIterator.hasNext()) {
            htmlWriter.enableJavaScript();
        }
        super.encodeEnd(writer);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.BOX;
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator decorator = null;

        BoxComponent boxComponent = (BoxComponent) component;

        IMenuIterator menuIterator = boxComponent.listMenus();
        for (; menuIterator.hasNext();) {
            MenuComponent menuComponent = menuIterator.next();

            IComponentDecorator menuDecorator = new SubMenuDecorator(
                    menuComponent, menuComponent.getMenuId(), null,
                    menuComponent.isRemoveAllWhenShown(facesContext),
                    getItemImageWidth(menuComponent),
                    getItemImageHeight(menuComponent));

            if (decorator == null) {
                decorator = menuDecorator;
                continue;
            }

            menuDecorator.addChildDecorator(decorator);
            decorator = menuDecorator;
        }

        return decorator;
    }

    protected int getItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        BoxComponent boxComponent = (BoxComponent) writer
                .getComponentRenderContext().getComponent();
        IMenuIterator menuIterator = boxComponent.listMenus();
        if (menuIterator.hasNext()) {
            IHtmlRenderContext htmlRenderContext = writer
                    .getHtmlComponentRenderContext().getHtmlRenderContext();

            IJavaScriptRenderContext javaScriptRenderContext = htmlRenderContext
                    .getJavaScriptRenderContext();

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.BOX, "menu");
        }
    }
}