/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.provider.AbstractProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractRendererTypeFactory extends AbstractProvider
        implements IRenderTypeFactory {
    protected static final String PREFIX_PROVIDER_ID = "org.rcfaces.core.RENDERER_TYPE_FACTORY:";

    private static final String RENDER_TYPE_FACTORY_PROPERTY = "org.rcface.core.RENDER_TYPE_FACTORY";

    public static IRenderTypeFactory get() {
        return get(null);
    }

    public static IRenderTypeFactory get(FacesContext facesContext) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();

            if (facesContext == null) {
                throw new FacesException("No faces context !");
            }
        }

        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, Object> requestMap = externalContext.getRequestMap();

        AbstractRendererTypeFactory rendererTypeFactory = (AbstractRendererTypeFactory) requestMap
                .get(RENDER_TYPE_FACTORY_PROPERTY);
        if (rendererTypeFactory != null) {
            return rendererTypeFactory;
        }

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        if (rcfacesContext == null) {
            throw new IllegalStateException("Can not find RCFaces context");
        }

        String renderKiId = facesContext.getViewRoot().getRenderKitId();
        String providerId = PREFIX_PROVIDER_ID + renderKiId;

        rendererTypeFactory = (AbstractRendererTypeFactory) rcfacesContext
                .getProvidersRegistry().getProvider(providerId);

        if (rendererTypeFactory == null) {
            throw new IllegalStateException(
                    "Can not find rendererTypeFactory for renderKitId '"
                            + renderKiId + "'.");
        }

        requestMap.put(RENDER_TYPE_FACTORY_PROPERTY, rendererTypeFactory);

        return rendererTypeFactory;
    }

    protected AbstractRendererTypeFactory() {
    }
}
