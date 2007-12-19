/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log LOG = LogFactory
            .getLog(AbstractInputRenderer.class);

    private static final String INPUT_MESSAGES_PROPERTY = "org.rcfaces.html.INPUT_MESSAGES";

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

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        // On active le javascript pour le traitement des facesMessages !
        if (componentRenderContext.getFacesContext().getMessages(
                componentRenderContext.getComponentClientId()).hasNext()) {

            // Il y a une erreur, on active le JavaScript ...
            htmlWriter.getJavaScriptEnableMode().enableOnInit();
        }

        encodeComponent(htmlWriter);

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
            writer.writeName(name);
        }

        String type = getInputType(component);
        if (type != null) {
            writer.writeType(type);
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

    public void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addRequiredJavaScriptClassNames(htmlWriter,
                javaScriptRenderContext);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        UIComponent component = componentRenderContext.getComponent();

        if (component instanceof IMenuCapability) {
            IMenuCapability menuCapability = (IMenuCapability) component;

            IMenuIterator menuIterator = menuCapability.listMenus();
            if (menuIterator.hasNext()) {

                javaScriptRenderContext.appendRequiredClass(
                        getJavaScriptClassName(), "menu");
            }
        }
    }

    protected IHtmlWriter writeStyleClass(IHtmlWriter writer, String classSuffix)
            throws WriterException {

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        boolean disabled = false;

        if (component instanceof IDisabledCapability) {
            if (((IDisabledCapability) component).isDisabled()) {
                disabled = true;

                if (classSuffix == null) {
                    classSuffix = getMainStyleClassName() + "_disabled";
                } else {
                    classSuffix += "_disabled";
                }
            }
        }

        if (disabled == false && (component instanceof IReadOnlyCapability)) {
            if (((IReadOnlyCapability) component).isReadOnly()) {
                if (classSuffix == null) {
                    classSuffix = getMainStyleClassName() + "_readOnly";
                } else {
                    classSuffix += "_readOnly";
                }
            }
        }

        return super.writeStyleClass(writer, classSuffix);
    }
}