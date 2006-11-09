/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractButtonRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    protected String getButtonType(UIComponent component) {
        return "button";
    }

    protected final IHtmlWriter writeButtonAttributes(IHtmlWriter writer)
            throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        return writeButtonAttributes(writer, component.getId());
    }

    protected final IHtmlWriter writeButtonAttributes(IHtmlWriter writer,
            String id) throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        if (id != null) {
            writer.writeName(id);
        }

        String type = getButtonType(component);
        if (type != null) {
            writer.writeType(type);
        }

        if (component instanceof IDisabledCapability) {
            writeEnabled(writer, (IDisabledCapability) component);
        }

        return writer;
    }
}