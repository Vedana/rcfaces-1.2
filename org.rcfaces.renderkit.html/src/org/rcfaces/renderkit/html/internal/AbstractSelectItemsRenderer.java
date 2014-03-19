/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractSelectItemsRenderer extends AbstractCssRenderer {

    protected final boolean hasComponenDecoratorSupport() {
        return true;
    }

    public final boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {

        // Les enfants ne doivent pas être rendus
    }
}