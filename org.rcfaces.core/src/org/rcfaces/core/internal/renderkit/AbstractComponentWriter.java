/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractComponentWriter implements IComponentWriter {

    private static final Log LOG = LogFactory
            .getLog(AbstractComponentWriter.class);

    private final IComponentRenderContext componentRenderContext;

    protected AbstractComponentWriter(
            IComponentRenderContext componentRenderContext) {
        this.componentRenderContext = componentRenderContext;
    }

    public IComponentRenderContext getComponentRenderContext() {
        return componentRenderContext;
    }
}
