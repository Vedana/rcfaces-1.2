/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

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
}
