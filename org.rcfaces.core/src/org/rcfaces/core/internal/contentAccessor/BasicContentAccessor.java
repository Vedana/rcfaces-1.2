package org.rcfaces.core.internal.contentAccessor;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentAccessor extends AbstractContentAccessor {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(BasicContentAccessor.class);

    private final Object value;

    public BasicContentAccessor(FacesContext facesContext, Object url,
            IContentType type, IContentVersionHandler contentVersionHandler) {
        super(type, contentVersionHandler);

        this.value = convertURL(facesContext, url);
    }

    public BasicContentAccessor(FacesContext facesContext, Object url,
            IContentAccessor root, int pathType) {
        super(root);

        if (pathType == UNDEFINED_PATH_TYPE) {
            this.value = convertURL(facesContext, url);
        } else {
            this.value = url;
            setPathType(pathType);
        }
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

    protected Object convertURL(FacesContext facesContext, Object url) {
        if (url == null) {
            setPathType(IContentAccessor.UNDEFINED_PATH_TYPE);

            return null;
        }

        if (url instanceof ValueBinding) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }
            url = ((ValueBinding) url).getValue(facesContext);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Get value of binding => " + url);
            }
        }

        if ((url instanceof String) == false) {
            setPathType(IContentAccessor.UNDEFINED_PATH_TYPE);
            return url;
        }

        return resolvePath(facesContext, (String)url);
    }

    public String toString() {
        return "[AbstractContentAccessor contentType=" + getType()
                + " pathType=" + getPathTypeName(getPathType())
                + " versionHandler=" + getContentVersionHandler()
                + " content='" + value + "' root=" + getParentAccessor() + "]";
    }
}