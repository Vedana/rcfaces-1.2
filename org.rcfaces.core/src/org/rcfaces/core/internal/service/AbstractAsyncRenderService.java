/*
 * $Id$
 * 
 * $Log$
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
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractAsyncRenderService implements IService {
    private static final String REVISION = "$Revision$";

    private static final String SERVICE_ID = Constants.getPackagePrefix()
            + ".AsyncRender";

    public static AbstractAsyncRenderService getInstance(
            FacesContext facesContext) {

        IServicesRegistry serviceRegistry = RcfacesContext.getInstance(
                facesContext.getExternalContext()).getServicesRegistry();

        return (AbstractAsyncRenderService) serviceRegistry.getService(
                facesContext, RenderKitFactory.HTML_BASIC_RENDER_KIT,
                SERVICE_ID);
    }

    public abstract boolean isAsyncRenderEnable();

    public abstract boolean isAsyncRendererEnabled(FacesContext facesContext,
            UIComponent component);

    public abstract void setContent(UIComponent componentInstance,
            BodyContent bodyContent);
}
