/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/11/09 19:09:09  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 */
package org.rcfaces.core.internal.service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.servlet.jsp.tagext.BodyContent;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractAsyncRenderService implements IService {
    private static final String REVISION = "$Revision$";

    private static final String SERVICE_ID = Constants.getPackagePrefix()
            + ".AsyncRender";

    public static AbstractAsyncRenderService getInstance(
            FacesContext facesContext) {

        IServicesRegistry serviceRegistry = RcfacesContext.getInstance(
                facesContext).getServicesRegistry();
        if (serviceRegistry == null) {
            // Designer mode
            return null;
        }

        return (AbstractAsyncRenderService) serviceRegistry.getService(
                facesContext, RenderKitFactory.HTML_BASIC_RENDER_KIT,
                SERVICE_ID);
    }

    public abstract boolean isAsyncRenderEnable();

    public abstract boolean isAsyncRendererEnabled(FacesContext facesContext,
            UIComponent component);

    public abstract void setContent(FacesContext facesContext,
            UIComponent componentInstance, BodyContent bodyContent);
}
