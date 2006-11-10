/*
 * $Id$
 */
package org.rcfaces.core.internal.service;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractService implements IService {
    private static final String REVISION = "$Revision$";

    // private static final Log LOG = LogFactory.getLog(AbstractService.class);

    protected static Renderer getRenderer(FacesContext facesContext,
            UIComponent component) {
        String rendererType = component.getRendererType();
        if (rendererType == null) {
            return null;
        }

        RenderKitFactory rkFactory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = rkFactory.getRenderKit(facesContext, facesContext
                .getViewRoot().getRenderKitId());

        return renderKit.getRenderer(component.getFamily(), rendererType);
    }
}