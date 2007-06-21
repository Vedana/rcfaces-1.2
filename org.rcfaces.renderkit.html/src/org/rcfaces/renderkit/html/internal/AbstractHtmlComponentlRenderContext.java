/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.internal.renderkit.AbstractComponentRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlComponentlRenderContext extends
        AbstractComponentRenderContext implements IHtmlComponentRenderContext {

    private static final String REVISION = "$Revision$";

    private static final String CLIENT_DATA_COUNT_PROPERTY_NAME = "org.rcfaces.renderkit.html.CLIENT_DATA_COUNT";

    protected AbstractHtmlComponentlRenderContext(FacesContext facesContext,
            UIComponent component, String componentClientId) {
        super(facesContext, component, componentClientId);
    }

    public boolean hasClientDatas(boolean clear) {
        UIComponent component = getComponent();
        if ((component instanceof IClientDataCapability) == false) {
            return false;
        }

        int clientDataCount = 0;
        if (clientDataCount < 0) {
            Integer cdc = (Integer) getAttribute(CLIENT_DATA_COUNT_PROPERTY_NAME);
            if (cdc != null) {
                clientDataCount = cdc.intValue();
            }
        }

        int count = ((IClientDataCapability) component).getClientDataCount();
        if (count <= clientDataCount) {
            return false;
        }

        if (clear == false) {
            return true;
        }

        clientDataCount = count;

        setAttribute(CLIENT_DATA_COUNT_PROPERTY_NAME, new Integer(
                clientDataCount));
        return true;
    }

    public IHtmlRenderContext getHtmlRenderContext() {
        return (IHtmlRenderContext) getRenderContext();
    }
}
