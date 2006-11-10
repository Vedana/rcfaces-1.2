/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class MenuContext extends SelectItemsJsContext {
    private static final String REVISION = "$Revision$";

    public MenuContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext,
            UIComponent rootComponent, Object value) {
        super(renderer, componentRenderContext, rootComponent, value);
    }
}
