/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.model.SelectItem;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class ToolBarContext extends SelectItemsJsContext {
    private static final String REVISION = "$Revision$";

    public ToolBarContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext, Object value) {
        super(renderer, componentRenderContext, componentRenderContext
                .getComponent(), value);
    }

    public ItemsMenuDecorator pushMenuDecorator(SelectItem selectItem) {
        return null;
    }

    public void popupMenuDecorator() {

    }

    public ItemsMenuDecorator peekMenuDecorator() {
        // TODO Auto-generated method stub
        return null;
    }
}
