/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.component.IImageAccessors;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentAccessorFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ContentAccessorFactory.class);

    private static final IContentVersionHandler RESOURCE_CONTENT_VERSION_HANDLER = new AbstractContentVersionHandler() {
        private static final String REVISION = "$Revision$";

        public String getId() {
            return "ResourceVersionHandler(pre)";
        }

        public IContentAccessor getVersionedContentAccessor(
                RcfacesContext rcfacesContext, FacesContext facesContext,
                IContentAccessor contentAccessor,
                IContentInformation[] contentInformation) {

            if (Constants.RESOURCE_CONTENT_VERSION_SUPPORT == false) {
                return null;
            }

            IContentVersionHandler contentVersionHandler = rcfacesContext
                    .getDefaultContentVersionHandler();
            if (contentVersionHandler == null) {
                return null;
            }

            return contentVersionHandler.getVersionedContentAccessor(
                    rcfacesContext, facesContext, contentAccessor,
                    contentInformation);
        }

        public String getVersionTag(RcfacesContext rcfacesContext,
                FacesContext facesContext, String relativeUrl,
                IContentAccessor contentAccessor,
                IContentInformation contentInformation) {

            if (Constants.RESOURCE_CONTENT_VERSION_SUPPORT == false) {
                return null;
            }

            IContentVersionHandler contentVersionHandler = rcfacesContext
                    .getDefaultContentVersionHandler();
            if (contentVersionHandler == null) {
                return null;
            }

            return contentVersionHandler.getVersionTag(rcfacesContext,
                    facesContext, relativeUrl, contentAccessor,
                    contentInformation);
        }

    };

    public static IContentAccessor createFromWebResource(
            FacesContext facesContext, Object value, IContentType type) {

        return new BasicContentAccessor(facesContext, value, type,
                RESOURCE_CONTENT_VERSION_HANDLER);
    }

    public static IContentAccessor createFromWebResource(
            FacesContext facesContext, Object value, IContentAccessor parent) {

        return new BasicContentAccessor(facesContext, value, parent,
                IContentAccessor.UNDEFINED_PATH_TYPE);
    }

    public static IContentAccessors createSingleImageWebResource(
            FacesContext facesContext, Object value, IContentType image) {
        return new SimpleImageAccessor(facesContext, value, image,
                RESOURCE_CONTENT_VERSION_HANDLER);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class SimpleImageAccessor extends BasicContentAccessor
            implements IImageAccessors {
        private static final String REVISION = "$Revision$";

        public SimpleImageAccessor(FacesContext facesContext, Object url,
                IContentType contentType, IContentVersionHandler versionHandler) {
            super(facesContext, url, contentType, versionHandler);
        }

        public IContentAccessor getImageAccessor() {
            return this;
        }
    }

}
