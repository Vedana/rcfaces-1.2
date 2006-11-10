/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.internal.IReleasable;
import org.rcfaces.core.internal.renderkit.AbstractComponentRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlComponentlRenderContext extends
        AbstractComponentRenderContext implements IHtmlComponentRenderContext,
        IReleasable {

    private static final String REVISION = "$Revision$";

    private int clientDataCount = 0;

    protected AbstractHtmlComponentlRenderContext(FacesContext facesContext,
            UIComponent component, String componentClientId) {
        super(facesContext, component, componentClientId);
    }

    public boolean hasClientDatas(boolean clear) {
        UIComponent component = getComponent();
        if ((component instanceof IClientDataCapability) == false) {
            return false;
        }

        int count = ((IClientDataCapability) component).getClientDataCount();
        if (count <= clientDataCount) {
            return false;
        }

        if (clear) {
            clientDataCount = count;
        }

        return true;
    }

    public IHtmlRenderContext getHtmlRenderContext() {
        return (IHtmlRenderContext) getRenderContext();
    }

    public void release() {
        clientDataCount = 0;
    }

}
