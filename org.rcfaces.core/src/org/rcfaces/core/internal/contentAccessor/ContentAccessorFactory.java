/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentAccessorFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ContentAccessorFactory.class);

    public static final IContentAccessor UNSUPPORTED_CONTENT_ACCESSOR = new IContentAccessor() {
        private static final String REVISION = "$Revision$";

        public Object getAttribute(String attributeName) {
            return null;
        }

        public Map getAttributes() {
            return null;
        }

        public Object getContentRef() {
            return null;
        }

        public IContentVersionHandler getContentVersionHandler() {
            return null;
        }

        public int getPathType() {
            return 0;
        }

        public IContentAccessor getParentAccessor() {
            return null;
        }

        public IContentType getType() {
            return null;
        }

        public String resolveURL(FacesContext facesContext,
                IContentInformation contentInformation,
                IFilterProperties filterProperties) {
            return null;
        }

        public void setContentVersionHandler(
                IContentVersionHandler contentVersionHandler) {
        }

        public void setPathType(int pathType) {
        }

        public boolean equals(Object obj) {
            return obj == this;
        }

        public int hashCode() {
            return 0;
        }

    };

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
