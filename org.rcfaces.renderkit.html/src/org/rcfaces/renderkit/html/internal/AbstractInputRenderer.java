/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractInputRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    private static final String INPUT_MESSAGES_PROPERTY = "org.rcfaces.html.INPUT_MESSAGES";

    public static final String BUTTON_TYPE = "button";

    public static final String RADIO_TYPE = "radio";

    public static final String CHECKBOX_TYPE = "checkbox";

    public static final String TEXT_TYPE = "text";

    public static final String PASSWORD_TYPE = "password";

    public static final String RESET_TYPE = "reset";

    public static final String SUBMIT_TYPE = "submit";

    public static final String IMAGE_TYPE = "image";

    protected abstract String getInputType(UIComponent component);

    /*
     * protected void encodeEnd(IComponentWriter writer) throws WriterException {
     * 
     * IHtmlWriter htmlWriter = (IHtmlWriter) writer;
     * 
     * if (htmlWriter.isJavaScriptEnabled() == false) { Iterator it =
     * writer.getComponentRenderContext().getFacesContext() .getMessages(
     * writer.getComponentRenderContext() .getComponentClientId()); if
     * (it.hasNext()) { ((IHtmlWriter) writer).enableJavaScript();
     * 
     * writer.getComponentRenderContext().setAttribute( INPUT_MESSAGES_PROPERTY,
     * Boolean.TRUE); } }
     * 
     * super.encodeEnd(writer); }
     * 
     * protected void encodeJavaScript(IJavaScriptWriter js) throws
     * WriterException { super.encodeJavaScript(js);
     * 
     * if (JavaScriptTools.writeMessages(js)) {
     * js.getHtmlComponentRenderContext().setAttribute( INPUT_MESSAGES_PROPERTY,
     * Boolean.TRUE); } }
     */

    protected final void encodeEnd(IComponentWriter writer)
            throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        // On active le javascript pour le traitement des facesMessages !
        htmlWriter.enableJavaScript();
        
        super.encodeEnd(writer);
    }

    protected abstract void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException;

    protected IHtmlWriter writeInputAttributes(IHtmlWriter writer)
            throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        return writeInputAttributes(writer, component.getId());
    }

    protected final IHtmlWriter writeInputAttributes(IHtmlWriter writer,
            String id) throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        UIComponent component = componentRenderContext.getComponent();

        String name = getInputName(componentRenderContext, id);
        if (name != null) {
            writer.writeAttribute("name", name);
        }

        String type = getInputType(component);
        if (type != null) {
            writer.writeAttribute("type", type);
        }

        if (component instanceof IReadOnlyCapability) {
            writeReadOnly(writer, (IReadOnlyCapability) component);
        }

        if (component instanceof IDisabledCapability) {
            writeEnabled(writer, (IDisabledCapability) component);
        }

        return writer;
    }

    /*
     * Il faut ecrire l'ID de toute facon, car il peut y avoir des regles CSS !
     * protected IWriter writeIdAttribute(IWriter writer) throws WriterException {
     * if (isNameEqualsId()) { return writer; }
     * 
     * return super.writeIdAttribute(writer); }
     */

    protected boolean isNameEqualsId() {
        return false;
    }

    protected String getInputName(
            IHtmlComponentRenderContext componentRenderContext, String id) {
        return componentRenderContext.getComponentClientId();
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        if ((component instanceof IMenuCapability) == false) {
            return null;
        }

        IMenuCapability menuCapabilityComponent = (IMenuCapability) component;

        IComponentDecorator decorator = null;

        IMenuIterator menuIterator = menuCapabilityComponent.listMenus();
        for (; menuIterator.hasNext();) {
            MenuComponent menuComponent = menuIterator.next();

            IComponentDecorator menuDecorator = new SubMenuDecorator(
                    menuComponent, menuComponent.getMenuId(), null,
                    menuComponent.isRemoveAllWhenShown(facesContext),
                    getMenuItemImageWidth(menuComponent),
                    getMenuItemImageHeight(menuComponent));

            if (decorator == null) {
                decorator = menuDecorator;
                continue;
            }

            menuDecorator.addChildDecorator(decorator);
            decorator = menuDecorator;
        }

        return decorator;
    }

    protected int getMenuItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getMenuItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        UIComponent component = componentRenderContext.getComponent();

        if (component instanceof IMenuCapability) {
            IMenuCapability menuCapability = (IMenuCapability) component;

            IMenuIterator menuIterator = menuCapability.listMenus();
            if (menuIterator.hasNext()) {

                IJavaScriptRenderContext javaScriptRenderContext = htmlWriter
                        .getHtmlComponentRenderContext().getHtmlRenderContext()
                        .getJavaScriptRenderContext();

                javaScriptRenderContext.appendRequiredClasses(classes,
                        getJavaScriptClassName(), "menu");
            }
        }
    }
}