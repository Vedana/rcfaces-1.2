/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractSelectItemsRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected final boolean hasComponenDecoratorSupport() {
        return true;
    }

    public final boolean getRendersChildren() {
        return true;
    }
}