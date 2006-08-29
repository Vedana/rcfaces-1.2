/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.14  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.13  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.12  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.11  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.10  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.9  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.8  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.7  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2004/08/27 09:57:21  oeuillot
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.BoxComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BoxRenderer extends AbstractCssRenderer implements IAsyncRenderer {
    private static final String REVISION = "$Revision$";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(htmlWriter);

        FacesContext facesContext = componentRenderContext.getFacesContext();

        BoxComponent box = (BoxComponent) componentRenderContext.getComponent();

        boolean asyncRender = false;

        boolean hidden = Boolean.FALSE.equals(box.getVisible(facesContext));

        if (hidden) {
            if (htmlRenderContext.isAsyncRenderEnable()) {
                if (box.getAsyncRenderMode(facesContext) != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    htmlWriter.writeAttribute("v:asyncRender", "true");

                    htmlRenderContext
                            .pushInteractiveRenderComponent(htmlWriter);

                    asyncRender = true;
                }
            }
        }

        setAsyncRenderer(htmlWriter, box, asyncRender);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeEnd(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");

        super.encodeEnd(writer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
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
                    menuComponent, menuComponent.getMenuId(), true,
                    menuComponent.isRemoveAllWhenShown(facesContext));

            if (decorator == null) {
                decorator = menuDecorator;
                continue;
            }

            menuDecorator.addChildDecorator(decorator);
            decorator = menuDecorator;
        }

        return decorator;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        BoxComponent boxComponent = (BoxComponent) writer
                .getComponentRenderContext().getComponent();
        IMenuIterator menuIterator = boxComponent.listMenus();
        if (menuIterator.hasNext()) {
            IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(writer);

            IJavaScriptRenderContext javaScriptRenderContext = htmlRenderContext
                    .getJavaScriptRenderContext();

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.BOX, "menu");
        }
    }
}