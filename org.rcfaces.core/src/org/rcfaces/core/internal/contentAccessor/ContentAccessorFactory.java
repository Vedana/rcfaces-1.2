/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentAccessorFactory {
    private static final String REVISION = "$Revision$";

    private static final IContentVersionHandler RESOURCE_CONTENT_VERSION_HANDLER = new IContentVersionHandler() {
        private static final String REVISION = "$Revision$";

        public String getVersionPath(RcfacesContext rcfacesContext,
                FacesContext facesContext, String url,
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

            return contentVersionHandler.getVersionPath(rcfacesContext,
                    facesContext, url, contentAccessor, contentInformation);
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

    private static final IContentVersionHandler FRAMEWORK_CONTENT_VERSION_HANDLER = null;

    public static IContentAccessor createFromWebResource(Object value,
            IContentType type) {

        return createAccessor(value, type, null,
                RESOURCE_CONTENT_VERSION_HANDLER);
    }

    public static IContentAccessor createFromWebResource(Object value,
            IContentAccessor parent) {

        return createAccessor(value, null, parent,
                RESOURCE_CONTENT_VERSION_HANDLER);
    }

    public static IContentAccessor createAccessor(Object value,
            IContentAccessor parent) {
        return createAccessor(value, null, parent, parent
                .getContentVersionHandler());
    }

    public static IContentAccessor createAccessor(Object value,
            IContentAccessor parent,
            IContentVersionHandler contentVersionHandler) {
        return createAccessor(value, null, parent, contentVersionHandler);
    }

    public static IContentAccessor createAccessor(Object value,
            IContentType type, IContentAccessor parent,
            IContentVersionHandler contentVersionHandler) {
        if (value == null) {
            return null;
        }

        if (value instanceof IContentAccessor) {
            return (IContentAccessor) value;
        }

        if (parent != null) {
            return new BasicContentAccessor(value, parent,
                    contentVersionHandler);
        }

        return new BasicContentAccessor(value, type, contentVersionHandler);
    }

    public static IContentAccessor createFromFrameworkStyleSheet(String url,
            IContentType type) {
        return new BasicContentAccessor(url, type,
                FRAMEWORK_CONTENT_VERSION_HANDLER);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class BasicContentAccessor extends AbstractContentAccessor {
        private static final String REVISION = "$Revision$";

        private final Object value;

        public BasicContentAccessor(Object url, IContentType type,
                IContentVersionHandler contentVersionHandler) {
            super(type, contentVersionHandler);
            this.value = url;
        }

        public BasicContentAccessor(Object url, IContentAccessor root,
                IContentVersionHandler contentVersionHandler) {
            super(root.getType(), root, contentVersionHandler);
            this.value = url;
        }

        public BasicContentAccessor(Object url, IContentAccessor root) {
            super(root);
            this.value = url;
        }

        public Object getContentRef() {
            return value;
        }

        public Object getAttribute(String attributeName) {
            if (value instanceof IContentModel) {
                IContentModel contentModel = (IContentModel) value;

                Object value = contentModel.getAttribute(attributeName);
                if (value != null) {
                    return value;
                }
            }

            return super.getAttribute(attributeName);
        }

        public Map getAttributes() {
            Map attributes = super.getAttributes();

            if (value instanceof IContentModel) {
                IContentModel contentModel = (IContentModel) value;

                Map value = contentModel.getAttributes();
                if (value != null && value.size() > 0) {
                    attributes = new HashMap(attributes);
                    
                    attributes.putAll(value);
                }
            }

            return attributes;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class SimpleImageAccessor extends BasicContentAccessor
            implements IImageAccessors {
        private static final String REVISION = "$Revision$";

        public SimpleImageAccessor(Object url, IContentType contentType,
                IContentVersionHandler contentVersionHandler) {
            super(url, contentType, contentVersionHandler);
        }

        public IContentAccessor getImageAccessor() {
            return this;
        }
    }

    public static IContentAccessors createSingleImageWebResource(Object value,
            IContentType image) {
        return new SimpleImageAccessor(value, image,
                RESOURCE_CONTENT_VERSION_HANDLER);
    }

}
