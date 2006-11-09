/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractInputRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    public static final String BUTTON_TYPE = "button";

    public static final String RADIO_TYPE = "radio";

    public static final String CHECKBOX_TYPE = "checkbox";

    public static final String TEXT_TYPE = "text";

    public static final String PASSWORD_TYPE = "password";

    public static final String RESET_TYPE = "reset";

    public static final String SUBMIT_TYPE = "submit";

    public static final String IMAGE_TYPE = "image";

    protected abstract String getInputType(UIComponent component);

    protected IHtmlWriter writeInputAttributes(IHtmlWriter writer)
            throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        return writeInputAttributes(writer, component.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#writeAttributes(org.rcfaces.core.internal.renderkit.IWriter)
     */
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
}