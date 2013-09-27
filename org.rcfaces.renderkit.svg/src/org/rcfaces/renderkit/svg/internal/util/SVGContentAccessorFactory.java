/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.util;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.BasicContentAccessor;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentProxyHandler;
import org.rcfaces.core.internal.contentAccessor.IContentVersionHandler;
import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.renderkit.svg.internal.component.ISVGAccessors;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SVGContentAccessorFactory extends ContentAccessorFactory {

    private static final Log LOG = LogFactory
            .getLog(SVGContentAccessorFactory.class);

    public static ISVGAccessors createSingleSVGWebResource(
            FacesContext facesContext, Object value, IContentFamily image) {
        return new SimpleSVGAccessor(facesContext, value, image,
                RESOURCE_CONTENT_VERSION_HANDLER,
                RESOURCE_CONTENT_PROXY_HANDLER);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class SimpleSVGAccessor extends BasicContentAccessor
            implements ISVGAccessors {

        public SimpleSVGAccessor(FacesContext facesContext, Object url,
                IContentFamily contentType,
                IContentVersionHandler versionHandler,
                IContentProxyHandler contentProxyHandler) {
            super(facesContext, url, contentType, versionHandler,
                    contentProxyHandler);
        }

        public IContentAccessor getSVGAccessor() {
            return this;
        }
    }

}
