/*
 * $Id$
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
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class AbstractRenderKitRegistryImpl {

    private static final Log LOG = LogFactory
            .getLog(AbstractRenderKitRegistryImpl.class);

    private static final Object LOAD_CONFIG_LOCK = new Object();

    private transient Map<String, RenderKit> renderKitsById = null;

    @SuppressWarnings("unchecked")
    synchronized Map<String, RenderKit> initialize(FacesContext facesContext) {
        synchronized (LOAD_CONFIG_LOCK) {
            if (renderKitsById != null) {
                return renderKitsById;
            }

            Map<String, Object> applicationMap = null;
            String applicationPropertyId = getApplicationPropertyId();
            if (applicationPropertyId != null) {

                if (facesContext == null) {
                    facesContext = FacesContext.getCurrentInstance();
                }

                applicationMap = facesContext.getExternalContext()
                        .getApplicationMap();

                renderKitsById = (Map<String, RenderKit>) applicationMap
                        .get(applicationPropertyId);
            }

            if (renderKitsById == null) {
                renderKitsById = new HashMap<String, RenderKit>(16);

                if (applicationMap != null) {
                    applicationMap.put(applicationPropertyId, renderKitsById);
                }
            }

            return renderKitsById;
        }
    }

    protected String getApplicationPropertyId() {
        return null;
    }

    protected final RenderKit getRenderKit(FacesContext facesContext,
            String renderKitId) {

        if (renderKitId == null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            renderKitId = facesContext.getViewRoot().getRenderKitId();

            if (LOG.isDebugEnabled()) {
                LOG.debug("ViewRoot render kit id='" + renderKitId + "'.");
            }
        }

        if (renderKitId == null) {
            renderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
        }

        Map<String, RenderKit> renderKitsById = initialize(facesContext);

        RenderKit renderKit = renderKitsById.get(renderKitId);

        if (LOG.isDebugEnabled()) {
            LOG.debug("getRenderKit: returns '" + renderKit
                    + "' for renderKitId='" + renderKitId + "'.");
        }

        return renderKit;
    }

    protected RenderKit allocate(String renderKitId) {
        if (renderKitId == null) {
            renderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
        }

        if (renderKitsById == null) {
            renderKitsById = new HashMap<String, RenderKit>();
        }

        RenderKit renderKit = renderKitsById.get(renderKitId);
        if (renderKit == null) {
            renderKit = createRenderKit();
            renderKitsById.put(renderKitId, renderKit);
        }

        return renderKit;
    }

    protected abstract RenderKit createRenderKit();

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static abstract class RenderKit {

    }
}
