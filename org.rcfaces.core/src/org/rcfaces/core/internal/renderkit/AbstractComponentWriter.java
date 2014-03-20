/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractComponentWriter implements IComponentWriter {

    private final IComponentRenderContext componentRenderContext;

    protected AbstractComponentWriter(
            IComponentRenderContext componentRenderContext) {
        this.componentRenderContext = componentRenderContext;
    }

    public IComponentRenderContext getComponentRenderContext() {
        return componentRenderContext;
    }
}
