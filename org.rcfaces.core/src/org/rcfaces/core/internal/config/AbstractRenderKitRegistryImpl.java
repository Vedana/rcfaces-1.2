/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.internal.config;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
abstract class AbstractRenderKitRegistryImpl {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractRenderKitRegistryImpl.class);

    private static final Object LOAD_CONFIG_LOCK = new Object();

    private transient Map renderKitsById = null;

    synchronized Map initialize(FacesContext facesContext) {
        synchronized (LOAD_CONFIG_LOCK) {
            if (renderKitsById != null) {
                return renderKitsById;
            }

            renderKitsById = new HashMap(16);

            return renderKitsById;
        }
    }

    protected final RenderKit getRenderKit(FacesContext facesContext,
            String renderKitId) {

        if (renderKitId == null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            renderKitId = facesContext.getViewRoot().getRenderKitId();
        }

        if (renderKitId == null) {
            renderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
        }

        Map renderKitsById = initialize(facesContext);

        RenderKit renderKit = (RenderKit) renderKitsById.get(renderKitId);

        return renderKit;
    }

    protected RenderKit allocate(String renderKitId) {
        if (renderKitId == null) {
            renderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
        }

        if (renderKitsById == null) {
            renderKitsById = new HashMap();
        }

        RenderKit renderKit = (RenderKit) renderKitsById.get(renderKitId);
        if (renderKit == null) {
            renderKit = createRenderKit();
            renderKitsById.put(renderKitId, renderKit);
        }

        return renderKit;
    }

    protected abstract RenderKit createRenderKit();

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected static abstract class RenderKit {
        private static final String REVISION = "$Revision$";

    }
}
