/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.1  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.1  2004/09/29 20:49:39  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.service;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
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