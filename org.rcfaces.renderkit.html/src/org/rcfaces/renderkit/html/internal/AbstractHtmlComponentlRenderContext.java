/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
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
            UIComponent component, String componentId) {
        super(facesContext, component, componentId);
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

    public void release() {
        clientDataCount = 0;
    }

}
